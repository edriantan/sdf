import java.io.*;
import java.net.*;

public class GameServer{

    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;

    private Socket socket1;
    private Socket socket2;
    private ReadFromClient player1RFC;
    private ReadFromClient player2RFC;
    private WriteToClient player1WTC;
    private WriteToClient player2WTC;

    private double p1x, p1y, p2x, p2y;
    
    public GameServer(){
        System.out.println("Game Server");
        numPlayers = 0;
        maxPlayers = 2;        
        p1x = 100;
        p1y = 100;
        p2x = 100;
        p2y = 300;

        try{
            ss = new ServerSocket(45371);
        }catch(IOException ex){
            System.out.println("IO Exception from Game Server Constructor");
        }
            
    }

    public void acceptConnections(){
        try {
            System.out.println("Waiting for connections...");
            while(numPlayers < maxPlayers){
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                
                numPlayers++;
                out.writeInt(numPlayers);
                System.out.println("Player #"+ numPlayers +" has connected");
                
                ReadFromClient RFC = new ReadFromClient(numPlayers, in);
                WriteToClient WTC = new WriteToClient(numPlayers, out);

                if(numPlayers == 1){
                    socket1 = s;
                    player1RFC = RFC;
                    player1WTC = WTC;
                }else{
                    socket2 = s;
                    player2RFC = RFC;
                    player2WTC = WTC;

                    player1WTC.startMsg();
                    player2WTC.startMsg();

                    Thread p1ReadThread = new Thread(player1RFC);
                    Thread p2ReadThread = new Thread(player2RFC);
                    p1ReadThread.start();
                    p2ReadThread.start();
                    
                    Thread p1WriteThread = new Thread(player1WTC);
                    Thread p2WriteThread = new Thread(player2WTC);
                    p1WriteThread.start();
                    p2WriteThread.start();
                }
            }
            System.out.println("No longer accepting players.");
        } catch (IOException ex){
            System.out.println("IO Exception from acceptConnections()");
        }
    }

    private class ReadFromClient implements Runnable {

        private int pID;
        private DataInputStream DIS;
        
        public ReadFromClient(int playerID, DataInputStream in){
            pID = playerID;
            DIS = in;
            System.out.println("RFC to " + playerID +" created.");
            
        }

        public void run(){
            try {
                while (true) { 

                    if(pID == 1){
                        p1x = DIS.readDouble();
                        p1y = DIS.readDouble();
                    }else{
                        p2x = DIS.readDouble();
                        p2y = DIS.readDouble();
                    }
                }
            }catch(IOException ex){
                System.out.println("IOException from RFC()");
            }
        }
    }

    private class WriteToClient implements Runnable {

        private int pID;
        private DataOutputStream DOS;
        
        public WriteToClient(int playerID, DataOutputStream out){
            pID = playerID;
            DOS = out;
            System.out.println("WFC to "+ playerID +" created.");
            
        }

        public void run(){

            try {
                while (true) { 

                    if(pID == 1){
                        DOS.writeDouble(p2x);
                        DOS.writeDouble(p2y);
                        DOS.flush();
                    }else{
                        DOS.writeDouble(p1x);
                        DOS.writeDouble(p1y);
                        DOS.flush();
                    }
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException WTC()");
                    }
                }
            }catch(IOException ex){
                System.out.println("IOException from RFC()");
            }
            }

            public void startMsg(){
            try {
                DOS.writeUTF("Players filled!, GO!");
            } catch (IOException ex) {
                System.out.println("IOException from start() WTS");
            }
        }
        }   

        public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
        }
}



    