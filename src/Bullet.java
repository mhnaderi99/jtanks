import java.awt.image.BufferedImage;

public abstract class Bullet {

    protected BufferedImage image;
    private int XPosition;
    private int YPosition;
    protected int XSpeed;
    protected int YSpeed;
    protected boolean isShoot;
    protected boolean isOnTheWay;
    protected int damage;
    protected double angle;
    protected int speed;

    public Bullet(int damage, int speed){
        this.damage = damage;
        this.speed = speed;
        isShoot = false;
        isOnTheWay = false;
    }
    public void update(){
        XPosition += XSpeed;
        YPosition += YSpeed;
        if(XPosition >= GameConstants.getScreenWidth() || XPosition<=0){
            isOnTheWay = false;

        }
        if(YPosition >= GameConstants.getScreenHeight() || YPosition<=0){
            isOnTheWay = false;
        }
    }

    public void setXPosition(int XPosition) {
        this.XPosition = XPosition;
    }

    public void setYPosition(int YPosition) {
        this.YPosition = YPosition;
    }

    public int getXPosition() {
        return XPosition;
    }

    public int getYPosition() {
        return YPosition;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }
}
