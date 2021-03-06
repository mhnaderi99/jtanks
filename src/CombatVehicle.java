import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 *
 */

public abstract class CombatVehicle implements Serializable{

    private int health;
    private boolean isMobile;
    private transient BufferedImage body;
    private ArrayList<Gun> guns;
    private Gun activeGun;
    private double gunAngle;
    private int XPosition;
    private int YPosition;
    private int speed;
    private boolean isEnemy;
    private int diagonalSpeed;

    /**
     * to set the health of the combat vehicle
     * @param health the health of the combat vehicle
     */
    public void setHealth(int health) {
        if (health > this.health && ! isEnemy) {
            AudioPlayer.playSound("repair.wav");
        }
        this.health = health;
    }

    /**
     * to set whether the vehicle is mobile or not
     * @param mobile true or false
     */
    public void setMobile(boolean mobile) {
        isMobile = mobile;
    }

    /**
     * to set the body of the vehicle
     * @param body body of the vehicle
     */
    public void setBody(BufferedImage body) {
        this.body = body;
    }

    /**
     * to set the guns of the vehicle
     * @param guns the guns
     */
    public void setGuns(ArrayList<Gun> guns) {
        this.guns = guns;
    }

    /**
     * to set the active gun
     * @param activeGun the active gun
     */
    public void setActiveGun(Gun activeGun) {
        this.activeGun = activeGun;
    }

    /**
     * to set the gun angle
     * @param gunAngle the gun angle
     */
    public void setGunAngle(double gunAngle) {
        this.gunAngle = gunAngle;
    }

    /**
     * to set x position of the vehicle
     * @param XPosition the x position
     */
    public void setXPosition(int XPosition) {
        this.XPosition = XPosition;
    }

    /**
     * to set y position of the vehicle
     * @param YPosition the y position
     */
    public void setYPosition(int YPosition) {
        this.YPosition = YPosition;
    }

    /**
     * to set the speed of the vehicle
     * @param speed the speed of the vehicle
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * to set the diagonal speed of the vehicle
     * @param diagonalSpeed the diagonal speed of the vehicle
     */
    public void setDiagonalSpeed(int diagonalSpeed) {
        this.diagonalSpeed = diagonalSpeed;
    }

    /**
     * to set the vehicle as enemy's vehicle or not
     * @param enemy true or false
     */
    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    /**
     * to check if the vehicle belongs to enemies or not
     * @return true or false
     */
    public boolean isEnemy() {
        return isEnemy;
    }

    /**
     * to get the health of the vehicle
     * @return the health of the vehicle
     */
    public int getHealth() {
        return health;
    }

    public BufferedImage getBody() {
        return body;
    }

    /**
     * to get the guns of the vehicle
     * @return the guns of the vehicle
     */
    public ArrayList<Gun> getGuns() {
        return guns;
    }

    /**
     * to get the active gun of the vehicle
     * @return the active gun of the vehicle
     */
    public Gun getActiveGun() {
        return activeGun;
    }

    /**
     * to get the gun angle of the vehicle
     * @return the gun angle
     */
    public double getGunAngle() {
        return gunAngle;
    }

    /**
     * to get the x position of the vehicle
     * @return the x position of the vehicle
     */
    public int getXPosition() {
        return XPosition;
    }

    /**
     * to get the y position of the vehicle
     * @return the y position of the vehicle
     */
    public int getYPosition() {
        return YPosition;
    }

    /**
     * to get the speed of the vehicle
     * @return the speed of the vehicle
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * to check whether the vehicle in alive or not
     * @return true or false
     */
    public boolean isAlive() {
        return health > 0;
    }

    public abstract void update();

    /**
     * to check if the vehicle is in the screen bounds or not
     * @return true or false
     */
    public boolean isInScreenBounds() {
        return true;
    }

    /**
     * to shoot
     * @param theta the angle of the gun
     */
    public void shoot(double theta) {
        if (! isEnemy) {
            Tank tank = (Tank) this;
            if (tank == GameLoop.getState().getTank() && GameLoop.isMultiplayer()) {
                double angle = (double)(((int)(theta * Math.pow(10,4))) / Math.pow(10,4));
                String message = "" + angle;
                sendShoot(message);
            }
        }
        getActiveGun().shoot(theta);
    }

    /**
     * to send the shoot
     * @param message the message
     */
    private void sendShoot(String message) {
        String pre = "S-";
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
     * check if the vehicle can see the other one
     * @param vehicle the vehicle to be seen
     * @return true or false
     */
    public boolean ifCanSee(CombatVehicle vehicle) {
        if (true) {
            Point a = new Point(getXPosition() - GameLoop.getState().getTopLeftPoint().x + getBody().getWidth() / 2,
                    getYPosition() - GameLoop.getState().getTopLeftPoint().y + getBody().getHeight() / 2);
            Point b = new Point(vehicle.getXPosition() - GameLoop.getState().getTopLeftPoint().x + vehicle.getBody().getWidth() / 2,
                    vehicle.getYPosition() - GameLoop.getState().getTopLeftPoint().y + vehicle.getBody().getHeight() / 2);
            double m = (double) (a.y - b.y) / (double) (a.x - b.x);
            double h = (double) a.y - m*a.x;

            double step = .01;
            int mn = Math.min(a.x, b.x);
            int mx = Math.max(a.x, b.x);
            for (double x = mn; x  <= mx; x += step) {
                double y = m*x + h;
                int xx = GameLoop.getState().getTopLeftPoint().x + (int) x;
                int yy = GameLoop.getState().getTopLeftPoint().y + (int) y;
                int ww = xx / GameConstants.getCellWidth();
                int hh = yy / GameConstants.getCellHeight();
                if (GameLoop.getState().getMap().getMap()[ww][hh].isBarrierForBullet()) {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * to get the distance to vehicle
     * @param vehicle th vehicle
     * @return the distance
     */
    public int distanceToVehicle(CombatVehicle vehicle) {
        int dx = getXPosition() + getBody().getWidth() / 2 - vehicle.getXPosition() - vehicle.getBody().getWidth() / 2;
        int dy = getYPosition() + getBody().getHeight() / 2 - vehicle.getYPosition() - vehicle.getBody().getHeight() / 2;
        return (int) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    /**
     * to check wheter the vehicle collided with the barriers or not
     * @param dx x speed
     * @param dy y speed
     * @return true or false
     */
    public boolean checkColision(int dx, int dy) {
        int w = (getXPosition() + dx) / GameConstants.getCellWidth();
        int h = (getYPosition() + dy) / GameConstants.getCellHeight();

        int x1 = getXPosition() + dx, y1 = getYPosition() + dy;
        int x2 = getXPosition() + getBody().getWidth() + dx, y2 = getYPosition() + dy;
        int x3 = getXPosition() + getBody().getWidth() + dx, y3 = getYPosition() + getBody().getHeight() + dy;
        int x4 = getXPosition() + dx, y4 = getYPosition() + getBody().getHeight() + dy;
        ArrayList<Point> corners = new ArrayList<>();
        corners.add(new Point(x1,y1));
        corners.add(new Point(x2,y2));
        corners.add(new Point(x3,y3));
        corners.add(new Point(x4,y4));
        for (int i = Math.max(0, w - 1); i < Math.min(w + 2, GameLoop.getState().getMap().getWidth()); i++) {
            for (int j = Math.max(0, h - 1); j < Math.min(h + 2, GameLoop.getState().getMap().getHeight()); j++) {
                if (GameLoop.getState().getMap().getMap()[i][j].isBarrier()) {
                    for (Point p: corners) {
                        if (p.x > i * GameConstants.getCellWidth() && p.x < (i + 1) * GameConstants.getCellWidth()) {
                            if (p.y > j * GameConstants.getCellHeight() && p.y < (j + 1) * GameConstants.getCellHeight()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * to check the prize
     * @param prize the prize
     * @return true or false
     */
    public boolean checkPrize(Prize prize) {
        ArrayList<Point> corners = new ArrayList<>();
        corners.add(new Point(prize.getXPosition(), prize.getYPosition()));
        corners.add(new Point(prize.getXPosition() + prize.getImage().getWidth(), prize.getYPosition()));
        corners.add(new Point(prize.getXPosition() + prize.getImage().getWidth(), prize.getYPosition() + prize.getImage().getHeight()));
        corners.add(new Point(prize.getXPosition(), prize.getYPosition() + prize.getImage().getHeight()));

        for (Point p: corners) {
            if (p.x >= getXPosition() && p.x <= getXPosition() + getBody().getWidth() && p.y >= getYPosition() && p.y <= getYPosition() + getBody().getHeight()) {
                return true;
            }
        }
        return false;
    }

    /**
     * to get the period of shoots
     * @return the period
     */
    public int getPeriod() {
        return (int) (GameConstants.getEnemyFiringPeroid() / getActiveGun().getType().getSpeed());
    }

    /**
     * updates the vehicles
     * @param SPEED
     */
    public void update(int SPEED) {
        ArrayList<Tank> tanks = new ArrayList<>();

        if (GameLoop.getMode() == 1) {
            tanks.add(GameLoop.getState().getTank());
            tanks.add(GameLoop.getState().getTank2());
        }
        else if(GameLoop.getMode() == 2) {
            tanks.add(GameLoop.getState().getTank2());
            tanks.add(GameLoop.getState().getTank());
        }
        else {
            tanks.add(GameLoop.getState().getTank());
        }

        for (Tank tank: tanks) {
            if (ifCanSee(tank)) {
                if (distanceToVehicle(tank) < GameConstants.getvAmount()) {
                    setSpeed(0);
                }
                else {
                    setSpeed(SPEED);
                }
                setGunAngle(Tank.findAngle(tank.getXPosition() - GameLoop.getState().getTopLeftPoint().x + tank.getBody().getWidth() / 2,
                        tank.getYPosition() - GameLoop.getState().getTopLeftPoint().y + tank.getBody().getHeight() / 2,
                        getXPosition() - GameLoop.getState().getTopLeftPoint().x + getBody().getWidth() / 2,
                        getYPosition() - GameLoop.getState().getTopLeftPoint().y + getBody().getHeight() / 2, GameConstants.getEnemyFiringError()));
                break;
            } else {
                setSpeed(0);
            }
        }

        if (! checkColision(getSpeed(),getSpeed())) {
            setXPosition(getXPosition() + (int) ((double) getSpeed() * Math.cos(getGunAngle())));
            setYPosition(getYPosition() + (int) ((double) getSpeed() * -Math.sin(getGunAngle())));
        }
        else {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (checkColision(i * getSpeed(), j * getSpeed())) {
                        setXPosition(getXPosition() + -2*getSpeed()*i);
                        setYPosition(getYPosition() + -2*getSpeed()*j);
                        break;
                    }
                }
            }
        }
        for (Tank tank : tanks) {
            if (ifCanSee(tank)) {
                int r = GameConstants.getRandom().nextInt(Integer.MAX_VALUE);
                if (r % getPeriod() == 0) {
                    getActiveGun().shoot(getGunAngle());
                    break;
                }
            }
        }
        for (Gun gun : getGuns()) {
            Iterator<Bullet> iter = gun.getBullets().iterator();
            while (iter.hasNext()) {

                Bullet bullet;
                try {
                    bullet = iter.next();
                } catch (Exception e) {
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
    }

}
