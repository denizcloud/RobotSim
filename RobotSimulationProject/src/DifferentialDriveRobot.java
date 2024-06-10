import java.util.Arrays;

public class DifferentialDriveRobot implements Robot{
    private double yaw;
    private double pitch;
    private double roll;
    private double rightEncoderDistance;
    private double leftEncoderDistance;
    private double robotX;
    private double robotY;
    private boolean isRunning;


    private double currentRightAcceleration = 0.0;
    private double currentLeftAcceleration = 0.0;
    private double directionalSpeed;

    private long currentMilis;
    private long prevMilis;

    //

    private PIDController rightSpeedController = new PIDController(0.8, 0, 0);
    private PIDController leftSpeedController = new PIDController(0.8, 0, 0);

    private PIDController turningSpeedController = new PIDController(0.2, 0, 0);

    private double CountsPerRevolution = 1024.0;
    private double maxSpeed = 5.0; //m/s
    private double maxAcceleration = 12.0;
    private double wheelDistance = 0.6;
    private double wheelRadius = 0.075;



    private double rightSpeed = 0.0;
    private double leftSpeed = 0.0;

    private double realRightSpeed = 0.0;
    private double realLeftSpeed = 0.0;

    //

    private Joystick joystick;
    private double lastJoystickInput;
    private double lastJoystickInput2;


    public DifferentialDriveRobot(){

        joystick = new Joystick();
        isRunning = true;
    }

    public double getYaw(){
        return yaw;
    }

    public double getDirectionalSpeed(){
        return directionalSpeed;
    }

    @Override
    public double getPitch() {
        return pitch;
    }

    @Override
    public double getRightEncoderDistance() {
        return rightEncoderDistance;
    }

    @Override
    public double getLeftEncoderDistance() {
        return leftEncoderDistance;
    }

    @Override
    public double getX() {
        return robotX;
    }

    @Override
    public double getY() {
        return robotY;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    public double setYaw(double yaw){
        return turningSpeedController.calculate(yaw, this.yaw);

    }


    public double getRoll(){
        return  roll;
    }

    public void stop(){
        setRightMotors(0.0);
        setLeftMotors(0.0);
    }


    @Override
    public String getRobotType() {
        return "Differential Drive Robot";
    }



    public void setRightMotors(double speed){
        if(speed > 0) rightSpeed = (speed > maxSpeed) ? maxSpeed : speed;
        else rightSpeed = (speed < -maxSpeed) ? -maxSpeed : speed;
    }

    public void setLeftMotors(double speed){
        if (speed > 0) leftSpeed = (speed > maxSpeed) ? maxSpeed : speed;
        else leftSpeed = (speed < -maxSpeed) ? -maxSpeed : speed;
    }


    public void tankDrive(double leftWheelSpeed, double rightWheelSpeed){
        leftWheelSpeed = (Math.abs(leftWheelSpeed) < 0.1) ? 0.0 : leftWheelSpeed;
        rightWheelSpeed = (Math.abs(rightWheelSpeed) < 0.1) ? 0.0 : rightWheelSpeed;
        double leftPID = leftSpeedController.calculate(leftWheelSpeed, realLeftSpeed);
        double rightPID = rightSpeedController.calculate(rightWheelSpeed, realRightSpeed);
        setLeftMotors(leftWheelSpeed + ((Math.abs(leftPID) > 3.0) ? leftPID : 0.0));
        setRightMotors(rightWheelSpeed + ((Math.abs(rightPID) > 3.0) ? rightPID : 0.0));
    }





    @Override
    public void runPeriodically() {
        //System.out.println("YAW GAIN: " + setYaw(0.78));
        //tankDrive(-setYaw(0.78) , setYaw(0.78));

        currentMilis = System.currentTimeMillis();
        if (Math.abs(rightSpeed - realRightSpeed) > 0.02)
            currentRightAcceleration = (rightSpeed > realRightSpeed) ? maxAcceleration : -maxAcceleration;
        else{
            currentRightAcceleration = 0;
        }
        if (Math.abs(leftSpeed - realLeftSpeed) > 0.02)
            currentLeftAcceleration = (leftSpeed > realLeftSpeed) ? maxAcceleration : -maxAcceleration;
        else{
            currentLeftAcceleration = 0;
        }

        realRightSpeed += currentRightAcceleration * (currentMilis - prevMilis) / 1000.0;
        realLeftSpeed += currentLeftAcceleration * (currentMilis - prevMilis) / 1000.0;

        if (Math.abs(realRightSpeed) < 0.03) realRightSpeed = 0;
        if (Math.abs(realLeftSpeed) < 0.03) realLeftSpeed = 0;

        directionalSpeed = Math.abs(realRightSpeed) > Math.abs(realLeftSpeed) ? realLeftSpeed : realRightSpeed;


        rightEncoderDistance += realRightSpeed * (2.0 * Math.PI * wheelRadius) * (currentMilis - prevMilis) / 1000.0;
        leftEncoderDistance += realLeftSpeed * (2.0 * Math.PI * wheelRadius) * (currentMilis - prevMilis) / 1000.0;

        robotX += (directionalSpeed) * (currentMilis - prevMilis) / 1000.0 * Math.cos(yaw);
        robotY += (directionalSpeed) * (currentMilis - prevMilis) / 1000.0 * Math.sin(yaw);

        yaw += (realRightSpeed - realLeftSpeed) * (currentMilis - prevMilis) / 1000.0 / (Math.PI * wheelDistance * 2);

        if(joystick.isConnected()){
            if((joystick.getJoystickInput()[0] != lastJoystickInput || joystick.getJoystickInput()[1] != lastJoystickInput2)) {
                System.out.println("Joystick x1: " + joystick.getJoystickInput()[0] + ", Joystick x2: " + joystick.getJoystickInput()[1]);
                lastJoystickInput = joystick.getJoystickInput()[0];
                lastJoystickInput2 = joystick.getJoystickInput()[1];
                tankDrive(-joystick.getJoystickInput()[0] * maxSpeed, -joystick.getJoystickInput()[1] * maxSpeed);
            }
        }
        //System.out.println("Right Speed:" + realRightSpeed);
        //System.out.println("Left Speed: " + realLeftSpeed);

        Dashboard.update(getX(), robotY, getYaw(), realRightSpeed, realLeftSpeed);

        /*System.out.println("rSpeed" + rightSpeed);
        System.out.println("lSpeed" + leftSpeed);
        System.out.println("Robot X: " + getX());
        System.out.println("Robot Y: " + getY());
        System.out.println("Robot Yaw: " + getYaw());*/


        prevMilis = currentMilis;
    }



}