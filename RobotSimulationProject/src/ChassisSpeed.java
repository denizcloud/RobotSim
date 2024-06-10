public class ChassisSpeed {
    double xSpeed;
    double ySpeed;
    double rot = 0;
    DifferentialDriveRobot robot;

    public ChassisSpeed (double xSpeed, double ySpeed, DifferentialDriveRobot robot){
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.robot = robot;
    }

    public double[] toWheelSpeeds(){
        double pow = Math.sqrt(Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2));
        if(ySpeed == 0){
            rot = 0;
        }
        else{
            rot = Math.atan(xSpeed/ySpeed);
        }
        System.out.println("ROT " + rot);


        return new double[]{
                pow - robot.setYaw(rot),
                pow + robot.setYaw(rot)
        };
    }
}
