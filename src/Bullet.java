import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    public Bullet(int damage, int speed){
        this.damage = damage;
        this.speed = speed;
        isShoot = false;
        isOnTheWay = false;
    }

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

    public void update() {

        x = x0 + XSpeed*time;
        y = y0 + YSpeed*time;

        int xMargin = GameLoop.getState().getTopLeftPoint().x;
        int yMargin = GameLoop.getState().getTopLeftPoint().y;

        int xx = (int) Math.ceil(x / GameConstants.getCellWidth());
        int yy = (int) Math.ceil(y / GameConstants.getCellHeight());

        ArrayList<Point> corners = new ArrayList<>();
        corners.add(new Point((int)x, (int) y));
        corners.add(new Point((int) x + image.getWidth(), (int) y));
        corners.add(new Point((int) x + image.getWidth(), (int) y + image.getHeight()));
        corners.add(new Point((int) x, (int) y + image.getHeight()));

        boolean flag = false;
        for (int i = Math.max(0, xx - 2); i < Math.min(xx + 2, GameLoop.getState().getMap().getWidth()); i++) {
            for (int j = Math.max(0, yy - 2); j < Math.min(yy + 2, GameLoop.getState().getMap().getHeight()); j++) {
                for (Point p: corners) {
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
            for (Point p: corners) {
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
        }
        else {
            for (CombatVehicle enemy: GameState.getEnemies()) {
                for (Point p: corners) {
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
            GameLoop.getCanvas().render(GameLoop.getState(), true);
        }

        if (x - xMargin >= GameConstants.getScreenWidth() || x - xMargin<= 0) {
            isOnTheWay = false;
        }

        if (y - yMargin>= GameConstants.getScreenHeight() || y - yMargin <= 0) {
            isOnTheWay = false;
        }

        time++;
    }

    public String getShootSound() {
        return shootSound;
    }

    public void setShootSound(String shootSound) {
        this.shootSound = shootSound;
    }

    public int getReloadPeriod() {
        return reloadPeriod;
    }

    public void setReloadPeriod(int reloadPeriod) {
        this.reloadPeriod = reloadPeriod;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public void setY0(double y0) {
        this.y0 = y0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getX0() {
        return x0;
    }

    public double getY0() {
        return y0;
    }

    public double getAngle() {
        return angle;
    }

    public boolean isOnTheWay() {
        return isOnTheWay;
    }

    public boolean isShoot() {
        return isShoot;
    }

    public BufferedImage getImage() {
        return image;
    }

    public double getSpeed() {
        return speed;
    }

    public double getTime() {
        return time;
    }

    public double getXSpeed() {
        return XSpeed;
    }

    public double getYSpeed() {
        return YSpeed;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isEnemyBullet() {
        return isEnemyBullet;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setOnTheWay(boolean onTheWay) {
        isOnTheWay = onTheWay;
    }

    public void setShoot(boolean shoot) {
        isShoot = shoot;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public void setXSpeed(double XSpeed) {
        this.XSpeed = XSpeed;
    }

    public void setYSpeed(double YSpeed) {
        this.YSpeed = YSpeed;
    }

    public void setEnemyBullet(boolean enemyBullet) {
        isEnemyBullet = enemyBullet;
    }

    public abstract Bullet getBullet();
}
