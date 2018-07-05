import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Tank {

    private int health;

    private BufferedImage body;
    private ArrayList<Gun> guns;
    private Gun activeGun;
    private double gunAngle;
    private int XPosition;
    private int YPosition;

    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public Tank() {
        try {
            body = ImageIO.read(new File("res/images/tanks/bodies/body1.png"));
        }
        catch (IOException e) {}

        guns = new ArrayList<Gun>();
        guns.add(GameConstants.getCannon());
        guns.add(GameConstants.getMachineGun());
        activeGun = guns.get(0);

        gunAngle = 0;
        XPosition = (GameConstants.getScreenWidth() - body.getWidth()) / 2;
        YPosition = (GameConstants.getScreenHeight() - body.getHeight()) / 2;
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
    }

    public int getHealth() {
        return health;
    }

    public ArrayList<Gun> getGuns() {
        return guns;
    }

    public BufferedImage getBody() {
        return body;
    }

    public double getGunAngle() {
        return gunAngle;
    }

    public Gun getActiveGun() {
        return activeGun;
    }

    public int getXPosition() {
        return XPosition;
    }

    public int getYPosition() {
        return YPosition;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    public void update() {

        if (keyUP)
            YPosition -= 8;
        if (keyDOWN)
            YPosition += 8;
        if (keyLEFT)
            XPosition -= 8;
        if (keyRIGHT)
            XPosition += 8;

        XPosition = Math.max(XPosition, 0);
        XPosition = Math.min(XPosition, GameConstants.getScreenWidth() - body.getWidth());
        YPosition = Math.max(YPosition, 0);
        YPosition = Math.min(YPosition, GameConstants.getScreenHeight() - body.getHeight());
    }

    class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    keyUP = true;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = true;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    keyUP = false;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = false;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = false;
                    break;
            }
        }
    }

    class MouseHandler extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            int dx = Math.abs(x - (getXPosition() + getBody().getWidth()/2));
            int dy = Math.abs(y - (getYPosition() + getBody().getHeight()/2));

            double tan = ((double) dy) / ((double) dx);
            gunAngle = Math.atan(tan);
        }
    }
}



