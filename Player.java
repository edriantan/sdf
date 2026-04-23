
import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;

public class Player extends Entity{

    GameCanvas gc;
    public int playerID;
    private Socket socket;
    private Color c;
    private double x, y, p2x, p2y;

    private ReadFromServer RFS;
    private WriteToServer WTS;

    Player p2;

    public Player(GameCanvas gc) {
        this.gc = gc;
        this.x = 0;
        this.y = 0;
        this.s = 3; 
    }

    public void update(){
        if(gc.down){
            y += s;
        }else if(gc.up){
            y -= s;
        }else if(gc.left){
            x -= s;
        }else if(gc.right){
            x += s;
        }
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }
    
    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setColor(Color color){
        c = color;
    }

    public void setP2(Player p){
        this.p2 = p;
    }

    public void draw(Graphics2D g2d){
        if(c == null){
            return;
        }
        Rectangle2D.Double p = new Rectangle2D.Double(x, y, gc.scaledSize, gc.scaledSize);
        g2d.setColor(c);
        g2d.fill(p);
    }
    
    public void connectToServer(){
        try {
            socket = new Socket("localhost", 45371);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("You are player"+ playerID);
            if(playerID == 1){
                setX(100);
                setY(100);
                c = Color.BLUE;
                p2.setColor(Color.RED);
                System.out.println("Waiting for players");
            }else if (playerID == 2) {
                setX(100);
                setY(300);
                c = Color.RED;
                p2.setColor(Color.BLUE);
            }
            RFS = new ReadFromServer(in);
            WTS = new WriteToServer(out);
            RFS.waitmsg();
        } catch (Exception ex) {
            System.out.println("IO Exception from connectToServer()");
        }

    }

    private class ReadFromServer implements Runnable{
        private DataInputStream din;

        public ReadFromServer(DataInputStream in){
            din = in;
            System.out.println("RFS runnable for "+ playerID +"created");
        }

        public void run(){

            try {
                while (true) { 
                    p2x = din.readDouble();
                    p2y = din.readDouble();
                    if(p2 != null){
                        p2.setX(p2x);
                        p2.setY(p2y);
                    }

                }

            }catch(IOException ex){
                System.out.println("IOException from RFS()");
            }
        }

        public void waitmsg(){
            try {
                String start = din.readUTF();
                System.out.println("Message from the server "+ start);
                Thread rThread = new Thread(RFS);
                Thread wThread = new Thread(WTS);
                rThread.start();
                wThread.start();
            } catch (IOException ex) {
                System.out.println("IOException from wait()RFS");
            }
        }
    }

    private class WriteToServer implements Runnable{
        private DataOutputStream dout;

        public WriteToServer(DataOutputStream out){
            dout = out;
            System.out.println("WTS runnable for "+ playerID +"created");
        }

        public void run(){
            try {
                while (true) { 
                        dout.writeDouble(x);
                        dout.writeDouble(y);
                        dout.flush();
                    try {
                        Thread.sleep(25);
                    }catch(InterruptedException ex){
                        System.out.println("InterruptedException from WTS()");
                    }
                }

            }catch(IOException ex){
                System.out.println("IOException from WTS()");
            }
        }
    }
}