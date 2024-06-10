import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Joystick {

    private boolean isConnected = false;

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader reader;
    private DataOutputStream dataOutputStream;
    private double joystick1Input = 0.0;
    private double joystick2Input = 0.0;
    private Thread readThread;
    private double tolerance = 0.1;
    private boolean reading1 = true;
    private int negative = 1;

    public Joystick(){
        host();
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                connect();
                read();
            }
        });
        readThread.start();
    }

    public boolean isConnected(){
        return isConnected;
    }

    public void setTolerance(double tolerance){
        this.tolerance = tolerance;
    }


    public void stopJoystick(){
        readThread.interrupt();
        try{
            socket.close();
            serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void send(){
        try{

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("yes");
            dataOutputStream.flush();
            dataOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public double[] getJoystickInput(){
        return new double[]{joystick1Input, joystick2Input};
    }

    public void read(){
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder builder1 = new StringBuilder();
            StringBuilder builder2 = new StringBuilder();
            while(true){
                char currentCharacter = (char) reader.read();
                if(reading1){
                    if(currentCharacter != '!'){
                        if(currentCharacter == '-'){
                            negative = -1;
                        }else{
                            builder1.append(currentCharacter);
                        }
                    }else{
                        joystick1Input = (Double.valueOf(builder1.toString()) > tolerance) ? negative * Double.valueOf(builder1.toString()) : 0.0;
                        builder1.delete(0, builder1.length());
                        reading1 = false;
                        negative = 1;
                    }
                }else{
                    if(currentCharacter != '?'){
                        if(currentCharacter == '-'){
                            negative = -1;
                        }else{
                            builder2.append(currentCharacter);
                        }
                    }else{
                        joystick2Input = (Double.valueOf(builder2.toString()) > tolerance) ? negative * Double.valueOf(builder2.toString()) : 0.0;
                        builder2.delete(0, builder2.length());
                        reading1 = true;
                        negative = 1;
                    }
                }

            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void host(){
        try{
            serverSocket = new ServerSocket(3131);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void connect(){
        try{
            socket = serverSocket.accept();
            isConnected = true;
            System.out.println("Client Connected!");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
