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
import java.util.Iterator;

public class Tank {

    private int health;

    private BufferedImage body;
    private ArrayList<Gun> guns;
    private Gun activeGun;
    private double gunAngle;
    private int XPosition;
    private int YPosition;
    private final int SPEED = 3;
    private final int DIAGONAL_SPEED = 2;

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


    public void switchGun(){
        int i = guns.indexOf(activeGun);
        if(i < guns.size() - 1){
            i++;
        }
        else {
            i=0;
        }
        activeGun = guns.get(i);
        update();
    }

    public void shoot(double theta) {
        activeGun.shoot(theta);
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

        for (Gun gun: guns) {
            Iterator<Bullet> iter = gun.getBullets().iterator();
            while (iter.hasNext()) {
                Bullet bullet = null;
                if (iter.hasNext()) {
                    bullet = iter.next();
                }

                if (bullet != null) {
                    if (!bullet.isShoot) {
                        bullet.setX0((XPosition + body.getWidth() / 2 + (activeGun.getImage().getWidth() - 17) * Math.cos(gunAngle)));
                        bullet.setY0((YPosition + body.getHeight() / 2 - (activeGun.getImage().getHeight()) * Math.sin(gunAngle)));
                        bullet.setAngle(2 * Math.PI - gunAngle);
                    }
                }
            }
            gun.update();
        }

        int dx, dy;
        int u = keyUP ? -1 : 0;
        int d = keyDOWN ? 1 : 0;
        int r = keyRIGHT ? 1 : 0;
        int l = keyLEFT ? -1 : 0;

        int verticalMove = u + d;
        int horizontalMove = r + l;

        if (horizontalMove == 0 || verticalMove == 0) {
            dx = horizontalMove*SPEED;
            dy = verticalMove*SPEED;
        }
        else {
            dx = DIAGONAL_SPEED*horizontalMove;
            dy = DIAGONAL_SPEED*verticalMove;
        }

        XPosition += dx;
        YPosition += dy;

        XPosition = Math.max(XPosition, 0);
        XPosition = Math.min(XPosition, GameConstants.getScreenWidth() - body.getWidth());
        YPosition = Math.max(YPosition, 0);
        YPosition = Math.min(YPosition, GameConstants.getScreenHeight() - body.getHeight());
    }

    private double findAngle(int x, int y) {
        int x2 = XPosition + body.getWidth()/2;
        int y2 = YPosition + body.getHeight()/2;

        double dx = x - x2;
        double dy = y - y2;

        int quarter = 0;

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
        double angle = Math.atan(tan);
        if ((angle < 0 && quarter == 4)) {
            angle += 2*Math.PI;
        }
        if (quarter == 3 || quarter == 2) {
            angle += Math.PI;
        }
        return angle;
    }

    class KeyHandler extends KeyAdapter {

        @Override
        public void keyTyped(KeyEvent e) {
            gunAngle = findAngle(MouseInfo.getPointerInfo().getLocation().x - GameLoop.getXOfCanvas(), MouseInfo.getPointerInfo().getLocation().y - GameLoop.getYOfCanvas());
        }

        @Override
        public void keyPressed(KeyEvent e) {

            gunAngle = findAngle(MouseInfo.getPointerInfo().getLocation().x - GameLoop.getXOfCanvas(), MouseInfo.getPointerInfo().getLocation().y - GameLoop.getYOfCanvas());

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
        public void mouseClicked(MouseEvent e){
            gunAngle = findAngle(e.getX(), e.getY());
            if(e.getButton() == 3){
                switchGun();
                update();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            gunAngle = findAngle(e.getX(), e.getY());
            if (e.getButton() == 1) {
                double theta = findAngle(e.getX(), e.getY());
                shoot(theta);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            gunAngle = findAngle(e.getX(), e.getY());
        }
    }
}