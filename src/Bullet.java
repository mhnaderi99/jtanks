import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.awt.image.BufferedImage;

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


        if (x >= GameConstants.getScreenWidth() || x <= 0) {
            isOnTheWay = false;

        }

        if (y >= GameConstants.getScreenHeight() || y <= 0) {
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
