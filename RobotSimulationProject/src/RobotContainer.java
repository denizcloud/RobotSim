public class RobotContainer {
    DifferentialDriveRobot robot;
    DriveCommand driveCommand;

    public RobotContainer(DifferentialDriveRobot robot){
        this.robot = robot;
        Dashboard.setFrame();
        AutoCommand autoCommand = new AutoCommand(robot);
        //CommandScheduler.schedule(autoCommand);
    }



    public Command getAutonomousCommand(){
        return null;
    }
}
