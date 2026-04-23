import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class GameFrame {

    private JFrame frame;
    private GameCanvas gc;
    private JPanel cp;
    
    public GameFrame(){
        frame = new JFrame();
        gc = new GameCanvas();
        gc.setFocusable(true);
    }

    public void setUp(){
        frame.setTitle("Final Project - Dy - Tan");
        frame.add(gc);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.pack();
        frame.setVisible(true);
        setKeyBinds();
    }

    public void setKeyBinds(){
        ActionMap am = gc.getActionMap();
        InputMap im = gc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        AbstractAction upPress = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gc.up = true;
            }
        };

        AbstractAction downPress = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gc.down = true;
            }
        };

        AbstractAction leftPress = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gc.left = true;
            }
        };

        AbstractAction rightPress = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gc.right = true;
            }
        };        
        
        AbstractAction upRelease = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gc.up = false;
            }
        };        
        
        AbstractAction downRelease = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gc.down = false;
            }
        };        
        
        AbstractAction leftRelease = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gc.left = false;
            }
        };        
        
        AbstractAction rightRelease = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gc.right = false;
            }
        };

        am.put("uP", upPress);
        am.put("dP", downPress);
        am.put("lP", leftPress);
        am.put("rP", rightPress);
        am.put("uR", upRelease);
        am.put("dR", downRelease);
        am.put("lR", leftRelease);
        am.put("rR", rightRelease);

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "uP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "dP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "rP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "lP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "uR");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "dR");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "rR");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "lR");
    }
}


//aaaa