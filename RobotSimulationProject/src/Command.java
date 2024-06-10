public interface Command {
    void run();
    void stop(boolean interrupted);
    boolean isFinished();
}
