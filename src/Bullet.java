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
    protected double XSpeed;
    protected double YSpeed;
    protected boolean isShoot;
    protected boolean isOnTheWay;
    protected int damage;
    private double time = 0;
    private double angle;
    protected double speed;

    public Bullet(int damage, int speed){
        this.damage = damage;
        this.speed = speed;
        isShoot = false;
        isOnTheWay = false;
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
                    if (GameLoop.getState().getMap().getMap()[i][j].isBarrier()) {
                        if (p.x >= i * GameConstants.getCellWidth() && p.x <= (i + 1) * GameConstants.getCellWidth()) {
                            if (p.y >= j * GameConstants.getCellHeight() && p.y <= (j + 1) * GameConstants.getCellHeight()) {
                                flag = true;
                            }
                        }
                    }
                }
            }
        }


        if (flag) {
            isOnTheWay = false;
        }

        if (x - xMargin >= GameConstants.getScreenWidth() || x - xMargin<= 0) {
            isOnTheWay = false;
        }

        if (y - yMargin>= GameConstants.getScreenHeight() || y - yMargin <= 0) {
            isOnTheWay = false;
        }

        time++;
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

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
