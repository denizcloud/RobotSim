public class AutoCommand implements Command {

    double start = 0;
    DifferentialDriveRobot robot;
    TrajectoryGenerator.CubicHermiteSpline xSpline;
    TrajectoryGenerator.CubicHermiteSpline ySpline;

    public AutoCommand(DifferentialDriveRobot robot){
        this.robot = robot;
        xSpline = new TrajectoryGenerator.CubicHermiteSpline(0,30, 0, 0, 5, 3);
        ySpline = new TrajectoryGenerator.CubicHermiteSpline(0,30, 0, 0, 5, 3);
        System.out.println("Starting Auto Path");
    }

    @Override
    public void run() {
        if(start == 0) start = System.currentTimeMillis();
        double time = (System.currentTimeMillis() - start) / 1000.0;
        ChassisSpeed speeds = new ChassisSpeed(xSpline.getVelocity(time), ySpline.getVelocity(time), robot);
        robot.setLeftMotors(speeds.toWheelSpeeds()[0]);
        robot.setRightMotors(speeds.toWheelSpeeds()[1]);
        //System.out.println("xPos: " + xSpline.getPosition(time) + " || yPos: " + ySpline.getPosition(time));

    }


    @Override
    public void stop(boolean interrupted) {
        robot.stop();
    }

    @Override
    public boolean isFinished() {
        if(xSpline.getMaxTime() > ySpline.getMaxTime()){
            return (System.currentTimeMillis() - start)/1000.0 > xSpline.getMaxTime();
        }else{
            return (System.currentTimeMillis() - start) /1000.0 > ySpline.getMaxTime();
        }

    }
}
