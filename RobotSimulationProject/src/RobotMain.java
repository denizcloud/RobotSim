public class RobotMain {
    RobotContainer robotContainer;
    DifferentialDriveRobot robot = new DifferentialDriveRobot();
    CommandScheduler scheduler = new CommandScheduler();

    public void robotInit(){
        scheduler.periodicStart(this);
        robotContainer = new RobotContainer(robot);
    }

    public void robotPeriodic(){
        robot.runPeriodically();
    }

    public void teleopInit(){

    }

    public void teleopPeriodic(){

    }

    public void testInit(){

    }

    public void testPeriodic(){

    }


    public void autonomousInit(){
        scheduler.schedule(robotContainer.getAutonomousCommand());
    }

    public void autonomousPeriodic(){

    }
}
