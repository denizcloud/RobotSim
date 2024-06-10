public class DriveCommand implements Command {
    DifferentialDriveRobot driveRobot;

    public DriveCommand(DifferentialDriveRobot robot){
        driveRobot = robot;
    }

    @Override
    public void run() {
        driveRobot.setRightMotors(5.0);
        driveRobot.setLeftMotors(5.0);
        System.out.println("RUNNING! ");
    }

    @Override
    public void stop(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
