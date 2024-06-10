import java.util.Timer;
import java.util.TimerTask;

public class CommandScheduler {
    private static Command runningCommand;
    private static boolean isRunning = false;
    private static Thread periodicThread;
    private static Thread mainCommandThread;

    private static Timer timer = new Timer();

    private static Timer commandTimer = new Timer();
    private static boolean pauseTimer = false;

    private static Thread[] threads = new Thread[4]; //multithreadding gerekirse

    public CommandScheduler(){
    }



    public static void schedule(Command command){
        if(isRunning){
            runningCommand.stop(true);
            mainCommandThread.interrupt();
            isRunning = false;
        }

        runningCommand = command;
        isRunning = true;
        mainCommandThread = new Thread(new Runnable() {
            @Override
            public void run() {
                commandTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        command.run();
                        if(command.isFinished()){
                            System.out.println("Finished Command");
                            commandTimer.cancel();
                            command.stop(false);
                            isRunning = false;
                            mainCommandThread.interrupt();
                        }
                    }
                }, 0L, 20L);
            }
        });

        mainCommandThread.start();
    }

    public void stopPeriodic(){
        pauseTimer = true;
        periodicThread.interrupt();
    }

    public void periodicStart(RobotMain robotMain){
        periodicThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(!pauseTimer){
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            robotMain.robotPeriodic();
                        }
                    }, 0L, 20L);
                }
                else{
                    timer.cancel();
                }
            }
        });
        periodicThread.start();
    }

}
