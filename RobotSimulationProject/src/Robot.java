public interface Robot {
    String getRobotType();
    void runPeriodically();
    double getRoll();
    double getYaw();
    double getPitch();
    double getRightEncoderDistance();
    double getLeftEncoderDistance();
    double getX();
    double getY();
    boolean isRunning();
}
