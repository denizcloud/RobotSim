import javax.swing.*;

public class Dashboard {
    public static final JFrame frame = new JFrame();
    private static double xPos = 0;
    private static double yPos = 0;
    private static double Yaw = 0;
    private static double RrightSpeed = 0;
    private static double RleftSpeed = 0;

    private static JLabel xLabel = new JLabel();
    private static JLabel yLabel = new JLabel();
    private static JLabel yawLabel = new JLabel();
    private static JLabel rSpeedLabel = new JLabel();
    private static JLabel lSpeedLabel = new JLabel();
    private static JButton robot = new JButton();


    public static void setFrame(){
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("");
        xLabel.setBounds(50,50,100,50);
        yLabel.setBounds(150,50,100,50);
        yawLabel.setBounds(50,100,100,50);
        rSpeedLabel.setBounds(50,150,100,50);
        lSpeedLabel.setBounds(150,150,100,50);
        frame.add(xLabel);
        frame.add(yLabel);
        frame.add(yawLabel);
        frame.add(rSpeedLabel);
        frame.add(robot);
        frame.add(lSpeedLabel);
    }


    public static void setLabels(){
        xLabel.setText("x: " + String.valueOf(xPos));
        yLabel.setText("y: " + String.valueOf(yPos));
        yawLabel.setText("yaw: " + String.valueOf(Yaw));
        rSpeedLabel.setText("rSpeed" + String.valueOf(RrightSpeed));
        lSpeedLabel.setText("lSpeed" + String.valueOf(RleftSpeed));
    }

    public static void update(double x, double y, double yaw, double rightSpeed, double leftSpeed){
        setX(x);
        setY(y);
        setYaw(yaw);
        setRightSpeed(rightSpeed);
        setLeftSpeed(leftSpeed);
        robot.setBounds(Math.toIntExact((long) x) * 10,Math.toIntExact((long) y) * 10,30,30);
        setLabels();
        frame.repaint();
    }

    public static void setX(double x){
        xPos = x;
    }

    public static void setY(double y){
        yPos = y;
    }

    public static void setYaw(double yaw){
        Yaw = yaw;
    }

    public static void setRightSpeed(double rightSpeed){
        RrightSpeed = rightSpeed;
    }

    public static void setLeftSpeed(double leftSpeed){
        RleftSpeed = leftSpeed;
    }


}
