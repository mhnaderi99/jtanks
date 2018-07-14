import javax.imageio.ImageIO;
import java.io.Serializable;
import java.util.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * this class inherits the combat vehicle
 */
public class Tank extends CombatVehicle implements Serializable{

    private static final int HEALTH = 100;
    private static final int SPEED = 2;
    private static final int DIAGONAL_SPEED = 2;
    private int activeGunIndex = 0;
    private boolean isPlayer;
    private String cheatCode = "";

    private transient boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private transient boolean keyW, keyS, keyD, keyA;
    private transient KeyHandler keyHandler;
    private transient MouseHandler mouseHandler;

    /**
     * the constructor of the tank
     */
    public Tank(boolean isPlayer) {
        setMobile(true);
        setPlayer(isPlayer);
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

    /**
     * to set if it's player
     * @param player true or false
     */
    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    /**
     * to set if it's up key
     * @param keyUP true or false
     */
    public void setKeyUP(boolean keyUP) {
        this.keyUP = keyUP;
    }

    /**
     * to set if it's down key
     * @param keyDOWN true or false
     */
    public void setKeyDOWN(boolean keyDOWN) {
        this.keyDOWN = keyDOWN;
    }

    /**
     * to set if it's left key
     * @param keyLEFT true or false
     */
    public void setKeyLEFT(boolean keyLEFT) {
        this.keyLEFT = keyLEFT;
    }

    /**
     * to set if it's right key
     * @param keyRIGHT true or false
     */
    public void setKeyRIGHT(boolean keyRIGHT) {
        this.keyRIGHT = keyRIGHT;
    }

    /**
     * to get the health
     * @return number of health
     */
    public static int getHEALTH() {
        return HEALTH;
    }

    /**
     * to get the default health
     * @return the default health
     */
    public static int getDefaultHealth() {
        return HEALTH;
    }

    /**
     * to switch gun
     */
    public void switchGun(boolean shouldBeSent){
        int i = getGuns().indexOf(getActiveGun());
        if(i < getGuns().size() - 1){
            i++;
        }
        else {
            i=0;
        }
        setActiveGun(getGuns().get(i));
        activeGunIndex = i;
        int x = GameLoop.getState().getTopLeftPoint().x / GameConstants.getCellWidth();
        int y = GameLoop.getState().getTopLeftPoint().y / GameConstants.getCellHeight();
        //update();
        if (shouldBeSent) {
            sendSwitchGun();
        }
        //send switch gun

    }

    /**
     * to send switch gun
     */
    private void sendSwitchGun() {
        String message = "SW";
        String sender = "";
        if (GameLoop.getMode() == 1) {
            sender = "SERVER-";
            ClientHandler.writeOnStream(sender + message);
        }
        if (GameLoop.getMode() == 2) {
            sender = "CLIENT-";
            Client.writeOnStream(sender + message);
        }
    }

    /**
     * to send keyboard
     * @param message the message
     */
    private void sendKeyboard(String message) {
        String pre = "KB-";
        String sender = "";
        if (GameLoop.getMode() == 1) {
            sender = "SERVER-";
            ClientHandler.writeOnStream(sender + pre + message);
        }
        if (GameLoop.getMode() == 2) {
            sender = "CLIENT-";
            Client.writeOnStream(sender + pre + message);
        }
    }

    /**
     * to send the angle
     * @param message the message
     */
    private void sendAngle(String message) {
        String pre = "M-";
        String sender = "";
        if (GameLoop.getMode() == 1) {
            sender = "SERVER-";
            ClientHandler.writeOnStream(sender + pre + message);
        }
        if (GameLoop.getMode() == 2) {
            sender = "CLIENT-";
            Client.writeOnStream(sender + pre + message);
        }
    }

    /**
     * to get the key handler
     * @return the key handler
     */
    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    /**
     * to get the mouse handler
     * @return the mouse handler
     */
    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    /**
     * updates the tanks
     */
    @Override
    public void update() {
        if (this == GameLoop.getState().getTank()) {
            setGunAngle(findAngle(MouseInfo.getPointerInfo().getLocation().x - GameLoop.getXOfCanvas(),
                    MouseInfo.getPointerInfo().getLocation().y - GameLoop.getYOfCanvas(),
                    getXPosition() - GameLoop.getState().getTopLeftPoint().x + getBody().getWidth() / 2,
                    getYPosition() - GameLoop.getState().getTopLeftPoint().y + getBody().getHeight() / 2, 0));
        }
        else if (GameLoop.isMultiplayer()){
        }
        //send gunAngle


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

        Iterator<Prize> iterator = GameState.getPrizes().iterator();
        while (iterator.hasNext()) {
            Prize prize = iterator.next();
            if (checkPrize(prize)) {
                prize.work(this);
                iterator.remove();
            }
        }

        if (! checkColision(dx, dy)) {
            setXPosition(getXPosition() + dx);
            setYPosition(getYPosition() + dy);
        }
        //GameLoop.getState().getMap().changeView(horizontalMove,verticalMove);

        if (this == GameLoop.getState().getTank()) {
            if (getXPosition() - GameLoop.getState().getTopLeftPoint().x <= GameConstants.getScreenWidth() / GameConstants.getNum()) {
                GameLoop.getState().getMap().changeView(-1, 0);
            }

            if (getXPosition() + getBody().getWidth() - GameLoop.getState().getTopLeftPoint().x > GameConstants.getScreenWidth() * (GameConstants.getNum() - 1) / GameConstants.getNum()) {
                GameLoop.getState().getMap().changeView(1, 0);
            }
            if (getYPosition() - GameLoop.getState().getTopLeftPoint().y <= GameConstants.getScreenHeight() / GameConstants.getNum()) {
                GameLoop.getState().getMap().changeView(0, -1);
            }

            if (getYPosition() + getBody().getHeight() - GameLoop.getState().getTopLeftPoint().y > GameConstants.getScreenHeight() * (GameConstants.getNum() - 1) / GameConstants.getNum()) {
                GameLoop.getState().getMap().changeView(0, 1);
            }
        }

        //send position

        /*XPosition = Math.max(XPosition, 0);
        XPosition = Math.min(XPosition, GameConstants.getScreenWidth() - body.getWidth());
        YPosition = Math.max(YPosition, 0);
        YPosition = Math.min(YPosition, GameConstants.getScreenHeight() - body.getHeight());
        */
    }

    private void processCheat(String cheatCode) {
        GameConstants.processCheatCode(this, cheatCode);
    }

    /**
     * to find the angle between two points
     * @param x x of first point
     * @param y y of first point
     * @param x2 x of second point
     * @param y2 y of second point
     * @param error error of shoot
     * @return the angle
     */
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

    /**
     * the key handler
     */
    class KeyHandler extends KeyAdapter {

        @Override
        public void keyTyped(KeyEvent e) {
            if(e.getKeyChar() == '\n') {
                processCheat(cheatCode);
                cheatCode = "";
            }
            else {
                cheatCode = cheatCode + e.getKeyChar();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (GameLoop.isGameOver()) {
                GameLoop.setExit(true);
                return;
            }
            String u, d, l, r;
            u = keyUP ? "1" : "0";
            d = keyDOWN ? "1" : "0";
            l = keyLEFT ? "1" : "0";
            r = keyRIGHT ? "1" : "0";
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    keyUP = true;
                    u = "1";
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = true;
                    d = "1";
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = true;
                    l = "1";
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = true;
                    r = "1";
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
            String message = u + d + l + r;
            sendKeyboard(message);

        }

        @Override
        public void keyReleased(KeyEvent e) {
            String u = "", d = "", l = "", r = "";
            u = keyUP ? "1" : "0";
            d = keyDOWN ? "1" : "0";
            l = keyLEFT ? "1" : "0";
            r = keyRIGHT ? "1" : "0";
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    keyUP = false;
                    u = "0";
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = false;
                    d = "0";
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = false;
                    l = "0";
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = false;
                    r = "0";
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
            String message = u + d + l + r;
            sendKeyboard(message);
        }
    }


    /**
     * to handle continues shooting
     */
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

    /**
     * the mouse handler
     */
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
                switchGun(true);
                update();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (task != null) {
                task.cancel();
            }
            else {
                if (timer != null) {
                    timer.purge();
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            double theta = findAngle(x,y,
                    getXPosition() - GameLoop.getState().getTopLeftPoint().x + getBody().getWidth()/2,
                    getYPosition() - GameLoop.getState().getTopLeftPoint().y + getBody().getHeight()/2, 0);
            setGunAngle(theta);
            theta = (double)(((int)(theta * Math.pow(10,4))) / Math.pow(10,4));
            String message = "" + theta;
            sendAngle(message);
        }
    }

    @Override
    public String toString() {
        return getXPosition() + "," + getYPosition() + ": " + getGunAngle();
    }
}