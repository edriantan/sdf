
import java.awt.*;
import java.awt.geom.*;

public class Player extends Entity{

    GameCanvas gc;

    public Player(GameCanvas gc) {
        this.gc = gc;
        this.x = 100;
        this.y = 100;
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
    
    public void draw(Graphics2D g2d){
        Rectangle2D.Double p = new Rectangle2D.Double(x, y, gc.scaledSize, gc.scaledSize);
        g2d.setColor(Color.CYAN);
        g2d.fill(p);
    }
    
}