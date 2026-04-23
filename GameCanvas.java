import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameCanvas extends JComponent implements Runnable {

    public BufferedImage grass;
    public boolean up, down, left, right;
    private final int tileSize = 16;
    private final int scale = 3;
    public final int scaledSize = tileSize * scale;
    public final int maxCol = 21;
    public final int maxRow = 16;
    Thread gameThread;

    Player p;

    public GameCanvas(){

        try {
            grass = ImageIO.read(new File("GRASS.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.p = new Player(this);
        this.setPreferredSize(new Dimension(1008,768));
        this.setDoubleBuffered(true);
        startThread();
    }

    public void startThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

        double FPS = 60;
        double delay = 1000000000/FPS;
        double delta = 0;
        long pastTime = System.nanoTime();
        long currentTime;

        int actualFPS = 0;
        long timer = 0;

        while(gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - pastTime) / delay;
            timer += currentTime - pastTime;
            pastTime = currentTime;
            
            if (delta >= 1){

                update();
                repaint();

                delta --;
                actualFPS ++;
            }
            if (timer >= 1000000000){
                System.out.println("FPS: " + actualFPS);
                actualFPS = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        p.update();
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(grass, 0, 0, scaledSize, scaledSize, null);
        p.draw(g2d);
    }    
}