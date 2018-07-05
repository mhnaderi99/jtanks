import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tank {

    private int health;

    private BufferedImage body;
    private ArrayList<Gun> guns;
    private Gun activeGun;
    private double gunAngle;
    private int XPosition;
    private int YPosition;

    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private int quarter;
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

    public int getQuarter() {
        return quarter;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    public void update() {

        if (keyUP)
            YPosition -= 2;
        if (keyDOWN)
            YPosition += 2;
        if (keyLEFT)
            XPosition -= 2;
        if (keyRIGHT)
            XPosition += 2;

        XPosition = Math.max(XPosition, 0);
        XPosition = Math.min(XPosition, GameConstants.getScreenWidth() - body.getWidth());
        YPosition = Math.max(YPosition, 0);
        YPosition = Math.min(YPosition, GameConstants.getScreenHeight() - body.getHeight());
    }

    class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            //calculateAngle();
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
            //calculateAngle();
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
            //calculateAngle();
            int x = e.getX();
            int y = e.getY();

            int x2 = XPosition + body.getWidth()/2;
            int y2 = YPosition + body.getHeight()/2;

            double dx = x - x2;
            double dy = y - y2;

            if (dx >= 0 && dy >= 0) {
                quarter = 4;
            }
            if (dx < 0 && dy > 0) {
                quarter = 3;
            }
            if (dx < 0 && dy < 0) {
                quarter = 2;
            }
            if (dx > 0 && dy < 0) {
                quarter = 1;
            }
            double tan = (-dy) / (dx);
            gunAngle = Math.atan(tan);
            if ((gunAngle < 0 && quarter == 4)) {
                gunAngle += 2*Math.PI;
            }
            if (quarter == 3 || quarter == 2) {
                gunAngle += Math.PI;
            }
        }
    }

    private void calculateAngle() {

        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
        System.out.println(x + "    " + y);
        int x2 = XPosition + body.getWidth()/2;
        int y2 = YPosition + body.getHeight()/2;

        double dx = x - x2;
        double dy = y - y2;

        if (dx >= 0 && dy >= 0) {
            quarter = 4;
        }
        if (dx < 0 && dy > 0) {
            quarter = 3;
        }
        if (dx < 0 && dy < 0) {
            quarter = 2;
        }
        if (dx > 0 && dy < 0) {
            quarter = 1;
        }

        //System.out.println(Math.toDegrees(gunAngle) + "     " + quarter);
        double tan = (-dy) / (dx);
        gunAngle = Math.atan(tan);
    }
}



