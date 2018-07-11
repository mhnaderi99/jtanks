import javax.imageio.ImageIO;
import java.util.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tank extends CombatVehicle{

    private static final int HEALTH = 100;
    private static final int SPEED = 2;
    private static final int DIAGONAL_SPEED = 2;

    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean keyW, keyS, keyD, keyA;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public Tank() {
        setMobile(true);
        setHealth(HEALTH);
        setEnemy(false);
        try {
            setBody(ImageIO.read(new File("res/images/tanks/bodies/body1.png")));
        }
        catch (IOException e) {}

        setGuns(new ArrayList<Gun>());
        getGuns().add(GameConstants.getCannon());
        getGuns().add(GameConstants.getMachineGun());
        setActiveGun(getGuns().get(0));
        setGunAngle(0);
        setXPosition(Map.getStartPoint().x * GameConstants.getCellWidth() + (GameConstants.getCellWidth() - getBody().getWidth()) / 2);
        setYPosition(Map.getStartPoint().y * GameConstants.getCellHeight() + (GameConstants.getCellHeight() - getBody().getHeight()) / 2);
        setSpeed(SPEED);
        setDiagonalSpeed(DIAGONAL_SPEED);

        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
    }


    public void switchGun(){
        int i = getGuns().indexOf(getActiveGun());
        if(i < getGuns().size() - 1){
            i++;
        }
        else {
            i=0;
        }
        setActiveGun(getGuns().get(i));
        update();
    }




    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    @Override
    public void update() {
        setGunAngle(findAngle(MouseInfo.getPointerInfo().getLocation().x - GameLoop.getXOfCanvas(),
                MouseInfo.getPointerInfo().getLocation().y - GameLoop.getYOfCanvas(),
                getXPosition() - GameLoop.getState().getTopLeftPoint().x + getBody().getWidth()/2,
                getYPosition() - GameLoop.getState().getTopLeftPoint().y + getBody().getHeight()/2, 0));

        for (Gun gun: getGuns()) {
            Iterator<Bullet> iter = gun.getBullets().iterator();
            while (iter.hasNext()) {

                Bullet bullet = null;
                try {
                    bullet = iter.next();
                } catch (Exception e){
                    break;
                }

                if (bullet != null) {
                    if (!bullet.isShoot()) {
                        bullet.setX0((getXPosition() + getBody().getWidth() / 2 + (getActiveGun().getImage().getWidth() - 17) * Math.cos(getGunAngle())));
                        bullet.setY0((getYPosition() + getBody().getHeight() / 2 - (getActiveGun().getImage().getHeight()) * Math.sin(getGunAngle())));
                        bullet.setAngle(2 * Math.PI - getGunAngle());
                    }
                }
            }
            gun.update();
        }

        if (keyA) {
            GameLoop.getState().getMap().changeView(-1,0);
        }
        if (keyS) {
            GameLoop.getState().getMap().changeView(0,1);
        }
        if (keyD) {
            GameLoop.getState().getMap().changeView(1,0);
        }
        if (keyW) {
            GameLoop.getState().getMap().changeView(0,-1);
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

        int w = (getXPosition() + dx) / GameConstants.getCellWidth();
        int h = (getYPosition() + dy) / GameConstants.getCellHeight();

        if (w == Map.getEndPoint().x && h == Map.getEndPoint().y) {
            GameLoop.setGameOver(true);
        }

        if (! checkColision(dx, dy)) {
            setXPosition(getXPosition() + dx);
            setYPosition(getYPosition() + dy);
        }
        if (getXPosition() - GameLoop.getState().getTopLeftPoint().x <= GameConstants.getScreenWidth() / GameConstants.getNum()) {
            GameLoop.getState().getMap().changeView(-1,0);
        }

        if (getXPosition() + getBody().getWidth() - GameLoop.getState().getTopLeftPoint().x > GameConstants.getScreenWidth() * (GameConstants.getNum() -1) / GameConstants.getNum()) {
            GameLoop.getState().getMap().changeView(1,0);
        }
        if (getYPosition() - GameLoop.getState().getTopLeftPoint().y <= GameConstants.getScreenHeight() / GameConstants.getNum()) {
            GameLoop.getState().getMap().changeView(0,-1);
        }

        if (getYPosition() + getBody().getHeight() - GameLoop.getState().getTopLeftPoint().y > GameConstants.getScreenHeight() * (GameConstants.getNum() - 1) / GameConstants.getNum()) {
            GameLoop.getState().getMap().changeView(0,1);
        }

        /*XPosition = Math.max(XPosition, 0);
        XPosition = Math.min(XPosition, GameConstants.getScreenWidth() - body.getWidth());
        YPosition = Math.max(YPosition, 0);
        YPosition = Math.min(YPosition, GameConstants.getScreenHeight() - body.getHeight());
        */
    }

    public static double findAngle(int x, int y, int x2, int y2, int error) {

        //int x2 = getXPosition() - GameLoop.getState().getTopLeftPoint().x + getBody().getWidth()/2;
        //int y2 = getYPosition() - GameLoop.getState().getTopLeftPoint().y + getBody().getHeight()/2;

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
        Random r = new Random(error);
        double errorAngle = -Math.toRadians(error) + Math.toRadians(error)*2 *r.nextDouble();
        return angle + errorAngle;
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
                case KeyEvent.VK_D:
                    keyD = true;
                    break;
                case KeyEvent.VK_A:
                    keyA = true;
                    break;
                case KeyEvent.VK_S:
                    keyS = true;
                    break;
                case KeyEvent.VK_W:
                    keyW = true;
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
                case KeyEvent.VK_A:
                    keyA = false;
                    break;
                case KeyEvent.VK_S:
                    keyS = false;
                    break;
                case KeyEvent.VK_D:
                    keyD = false;
                    break;
                case KeyEvent.VK_W:
                    keyW = false;
                    break;
            }
        }
    }


    private class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            double theta = findAngle(MouseInfo.getPointerInfo().getLocation().x - GameLoop.getXOfCanvas(),
                    MouseInfo.getPointerInfo().getLocation().y - GameLoop.getYOfCanvas(),
                    getXPosition() - GameLoop.getState().getTopLeftPoint().x + getBody().getWidth()/2,
                    getYPosition() - GameLoop.getState().getTopLeftPoint().y + getBody().getHeight()/2, 0);
            shoot(theta);
        }
    }

    class MouseHandler extends MouseAdapter {

        Timer timer;
        TimerTask task;



        @Override
        public void mousePressed(MouseEvent e) {
            setGunAngle(findAngle(e.getX(), e.getY(),
                    getXPosition() - GameLoop.getState().getTopLeftPoint().x + getBody().getWidth()/2,
                    getYPosition() - GameLoop.getState().getTopLeftPoint().y + getBody().getHeight()/2, 0));
            if (e.getButton() == 1) {
                timer = new Timer();
                task = new MyTimerTask();
                timer.scheduleAtFixedRate(task, 0, getActiveGun().getType().getReloadPeriod());
            }
            if (e.getButton() == 3) {
                switchGun();
                update();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (task != null) {
                task.cancel();
            }
            else {
                timer.purge();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            setGunAngle(findAngle(x,y,
                    getXPosition() - GameLoop.getState().getTopLeftPoint().x + getBody().getWidth()/2,
                    getYPosition() - GameLoop.getState().getTopLeftPoint().y + getBody().getHeight()/2, 0));
        }
    }
}