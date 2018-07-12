
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 */

public abstract class Bullet {

    protected BufferedImage image;
    private double x0;
    private double y0;
    private double x;
    private double y;
    private double XSpeed;
    private double YSpeed;
    private boolean isShoot;
    private boolean isOnTheWay;
    private int damage;
    private double time = 0;
    private double angle;
    private double speed;
    private int reloadPeriod;
    private String shootSound;
    private boolean isEnemyBullet;

    /**
     * //the constructor of the bullet
     * @param damage the damage of the bullet
     * @param speed the speed of the bullet
     */
    public Bullet(int damage, int speed) {
        this.damage = damage;
        this.speed = speed;
        isShoot = false;
        isOnTheWay = false;
    }

    /**
     * to make a copy of a bullet
     * @param bullet the bullet to be copied
     */
    public Bullet(Bullet bullet) {
        image = bullet.image;
        x0 = bullet.x0;
        y0 = bullet.y0;
        x = bullet.x;
        y = bullet.y;
        XSpeed = bullet.XSpeed;
        YSpeed = bullet.YSpeed;
        isShoot = bullet.isShoot;
        isOnTheWay = bullet.isOnTheWay;
        damage = bullet.damage;
        time = bullet.time;
        angle = bullet.angle;
        speed = bullet.speed;
        reloadPeriod = bullet.reloadPeriod;
        shootSound = bullet.shootSound;
    }

    /**
     * //todo
     */
    public void update() {

        x = x0 + XSpeed * time;
        y = y0 + YSpeed * time;

        int xMargin = GameLoop.getState().getTopLeftPoint().x;
        int yMargin = GameLoop.getState().getTopLeftPoint().y;

        int xx = (int) Math.ceil(x / GameConstants.getCellWidth());
        int yy = (int) Math.ceil(y / GameConstants.getCellHeight());

        ArrayList<Point> corners = new ArrayList<>();
        corners.add(new Point((int) x, (int) y));
        corners.add(new Point((int) x + image.getWidth(), (int) y));
        corners.add(new Point((int) x + image.getWidth(), (int) y + image.getHeight()));
        corners.add(new Point((int) x, (int) y + image.getHeight()));

        boolean flag = false;
        for (int i = Math.max(0, xx - 2); i < Math.min(xx + 2, GameLoop.getState().getMap().getWidth()); i++) {
            for (int j = Math.max(0, yy - 2); j < Math.min(yy + 2, GameLoop.getState().getMap().getHeight()); j++) {
                for (Point p : corners) {
                    if (GameLoop.getState().getMap().getMap()[i][j].isBarrierForBullet()) {
                        if (p.x >= i * GameConstants.getCellWidth() && p.x <= (i + 1) * GameConstants.getCellWidth()) {
                            if (p.y >= j * GameConstants.getCellHeight() && p.y <= (j + 1) * GameConstants.getCellHeight()) {
                                flag = true;

                                if (GameLoop.getState().getMap().getMap()[i][j].isDestroyable()) {
                                    GameLoop.getState().getMap().getMap()[i][j].destroy(this);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (isEnemyBullet) {
            for (Point p : corners) {
                if (p.x >= GameLoop.getState().getTank().getXPosition() &&
                        p.x <= GameLoop.getState().getTank().getXPosition() + GameLoop.getState().getTank().getBody().getWidth() &&
                        p.y >= GameLoop.getState().getTank().getYPosition() &&
                        p.y <= GameLoop.getState().getTank().getYPosition() + GameLoop.getState().getTank().getBody().getHeight()) {
                    flag = true;
                    AudioPlayer.playSound("enemyBulletToMyTank.wav");
                    GameLoop.getState().getTank().setHealth(GameLoop.getState().getTank().getHealth() - damage);
                    break;
                }
            }
        } else {
            for (CombatVehicle enemy : GameState.getEnemies()) {
                for (Point p : corners) {
                    if (p.x >= enemy.getXPosition() &&
                            p.x <= enemy.getXPosition() + enemy.getBody().getWidth() &&
                            p.y >= enemy.getYPosition() &&
                            p.y <= enemy.getYPosition() + enemy.getBody().getHeight()) {
                        flag = true;
                        enemy.setHealth(enemy.getHealth() - damage);

                        break;
                    }
                }
            }
        }


        if (flag) {
            isOnTheWay = false;
            /*int x = (int)getX() / GameConstants.getCellWidth();
            int y = (int)getY() / GameConstants.getCellHeight();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    GameLoop.getCanvas().renderCell(x + i,y + j);
                }
            }
            */
            GameLoop.getCanvas().render(GameLoop.getState(), true);
        }

        if (x - xMargin >= GameConstants.getScreenWidth() || x - xMargin <= 0) {
            isOnTheWay = false;
        }

        if (y - yMargin >= GameConstants.getScreenHeight() || y - yMargin <= 0) {
            isOnTheWay = false;
        }

        time++;
    }

    /**
     * to get the sound of shooting
     *
     * @return the shoot sound
     */
    public String getShootSound() {
        return shootSound;
    }

    /**
     * to set the sound of shooing
     *
     * @param shootSound the sound of shooting
     */
    public void setShootSound(String shootSound) {
        this.shootSound = shootSound;
    }

    /**
     * to get the reload period
     *
     * @return reload period
     */
    public int getReloadPeriod() {
        return reloadPeriod;
    }

    /**
     * to set the reload period
     *
     * @param reloadPeriod the reload period
     */
    public void setReloadPeriod(int reloadPeriod) {
        this.reloadPeriod = reloadPeriod;
    }

    /** to set the x position of the bullet
     * @param x the x position
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * to set the y position of the bullet
     * @param y the y position
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * to set the initial x position of the bullet
     * @param x0 the initial x position
     */
    public void setX0(double x0) {
        this.x0 = x0;
    }

    /**
     * to set the initial y position of the bullet
     * @param y0 the initial y position
     */
    public void setY0(double y0) {
        this.y0 = y0;
    }

    /**
     * to get the x position of the bullet
     * @return the x position
     */
    public double getX() {
        return x;
    }

    /**
     * to get the y position of the bullet
     * @return the y position
     */
    public double getY() {
        return y;
    }

    /**
     * to get the initial x position of the bullet
     * @return the initial x position
     */
    public double getX0() {
        return x0;
    }

    /**
     * to get the initial y position of the bullet
     * @return the initial y position
     */
    public double getY0() {
        return y0;
    }

    /**
     * to get the angle of the bullet
     * @return the angle
     */
    public double getAngle() {
        return angle;
    }

    /**
     * check if the bullet is shoot and on the way
     * @return true or false
     */
    public boolean isOnTheWay() {
        return isOnTheWay;
    }

    /**
     * check if the bullet is shoot
     * @return true or false
     */
    public boolean isShoot() {
        return isShoot;
    }

    /**
     * to get the image of the bullet
     * @return the image of the bullet
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * to get the speed of the bullet
     * @return the speed of the bullet
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * to get the time of the bullet
     * @return time
     */
    public double getTime() {
        return time;
    }

    /**
     * to get the x component of bullet's speed
     * @return the x component of speed
     */
    public double getXSpeed() {
        return XSpeed;
    }

    /**
     * to get the y component of bullet's speed
     * @return the y component of speed
     */
    public double getYSpeed() {
        return YSpeed;
    }

    /**
     * to get the damage of the bullet
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * to check if the bullet belongs to enemy or not
     * @return true or false
     */
    public boolean isEnemyBullet() {
        return isEnemyBullet;
    }

    /**
     * to set the angel of the bullet
     * @param angle the angle of the bullet
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * to set the image of the bullet
     * @param image the image of the bullet
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * to set the speed of the bullet
     * @param speed the speed of the bullet
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * to set the damage of the bullet
     * @param damage the damage of the bullet
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * to set that the bullet is shoot and on the way or not
     * @param onTheWay true or false
     */
    public void setOnTheWay(boolean onTheWay) {
        isOnTheWay = onTheWay;
    }

    /**
     * to set that the bullet is shoot or not
     * @param shoot true or false
     */
    public void setShoot(boolean shoot) {
        isShoot = shoot;
    }

    /**
     * to set the time of the bullet
     * @param time the time of the bullet
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * to set the x component of bullet's speed
     * @param XSpeed the x component of bullet's speed
     */
    public void setXSpeed(double XSpeed) {
        this.XSpeed = XSpeed;
    }

    /**
     * to set the y component of bullet's speed
     * @param YSpeed the y component of bullet's speed
     */
    public void setYSpeed(double YSpeed) {
        this.YSpeed = YSpeed;
    }

    /**
     * to set if the bullet belongs to enemy or not
     * @param enemyBullet true or false
     */
    public void setEnemyBullet(boolean enemyBullet) {
        isEnemyBullet = enemyBullet;
    }

    /**
     * to get the bullet
     * @return the bullet
     */
    public abstract Bullet getBullet();
}
