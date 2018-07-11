import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public abstract class CombatVehicle {

    private int health;
    private boolean isMobile;
    private BufferedImage body;
    private ArrayList<Gun> guns;
    private Gun activeGun;
    private double gunAngle;
    private int XPosition;
    private int YPosition;
    private int speed;
    private boolean isEnemy;
    private int diagonalSpeed;


    public void setHealth(int health) {
        this.health = health;
    }

    public void setMobile(boolean mobile) {
        isMobile = mobile;
    }

    public void setBody(BufferedImage body) {
        this.body = body;
    }

    public void setGuns(ArrayList<Gun> guns) {
        this.guns = guns;
    }

    public void setActiveGun(Gun activeGun) {
        this.activeGun = activeGun;
    }

    public void setGunAngle(double gunAngle) {
        this.gunAngle = gunAngle;
    }

    public void setXPosition(int XPosition) {
        this.XPosition = XPosition;
    }

    public void setYPosition(int YPosition) {
        this.YPosition = YPosition;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDiagonalSpeed(int diagonalSpeed) {
        this.diagonalSpeed = diagonalSpeed;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public int getHealth() {
        return health;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public BufferedImage getBody() {
        return body;
    }

    public ArrayList<Gun> getGuns() {
        return guns;
    }

    public Gun getActiveGun() {
        return activeGun;
    }

    public double getGunAngle() {
        return gunAngle;
    }

    public int getXPosition() {
        return XPosition;
    }

    public int getYPosition() {
        return YPosition;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getDiagonalSpeed() {
        return diagonalSpeed;
    }

    public abstract void update();

    public boolean isInScreenBounds() {
        if (XPosition + body.getWidth() - GameLoop.getState().getTopLeftPoint().x >= 0 &&
                XPosition - GameLoop.getState().getTopLeftPoint().y <= GameConstants.getScreenWidth() &&
                YPosition + body.getHeight() - GameLoop.getState().getTopLeftPoint().y >= 0 &&
                YPosition - GameLoop.getState().getTopLeftPoint().y <= GameConstants.getScreenHeight()) {
            return true;
        }

        return false;
    }
    public void shoot(double theta) {
        getActiveGun().shoot(theta);
    }

    public boolean ifCanSee(CombatVehicle vehicle) {
        if (vehicle.isInScreenBounds() && isInScreenBounds()) {
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

    public int distanceToVehicle(CombatVehicle vehicle) {
        int dx = getXPosition() + getBody().getWidth() / 2 - vehicle.getXPosition() - vehicle.getBody().getWidth() / 2;
        int dy = getYPosition() + getBody().getHeight() / 2 - vehicle.getYPosition() - vehicle.getBody().getHeight() / 2;
        return (int) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

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

    public int getPeriod() {
        return (int) (GameConstants.getEnemyFiringPeroid() / getActiveGun().getType().getSpeed());
    }

    public void update(int SPEED) {
        if (ifCanSee(GameLoop.getState().getTank())){
            if (distanceToVehicle(GameLoop.getState().getTank()) < GameConstants.getAmount()) {
                setSpeed(0);
            }
            else {
                setSpeed(SPEED);
            }
            setGunAngle(Tank.findAngle(GameLoop.getState().getTank().getXPosition() - GameLoop.getState().getTopLeftPoint().x + GameLoop.getState().getTank().getBody().getWidth() / 2,
                    GameLoop.getState().getTank().getYPosition() - GameLoop.getState().getTopLeftPoint().y + GameLoop.getState().getTank().getBody().getHeight() / 2,
                    getXPosition() - GameLoop.getState().getTopLeftPoint().x + getBody().getWidth()/2,
                    getYPosition() - GameLoop.getState().getTopLeftPoint().y + getBody().getHeight()/2, GameConstants.getEnemyFiringError()));

        }
        else {
            setSpeed(0);
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
        if (isInScreenBounds() && GameLoop.getState().getTank().isInScreenBounds() && ifCanSee(GameLoop.getState().getTank())) {
            int r = new Random().nextInt(Integer.MAX_VALUE);
            if (r % getPeriod() == 0) {
                getActiveGun().shoot(getGunAngle());
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