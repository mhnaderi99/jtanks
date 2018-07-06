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
    protected int speed;

    public Bullet(int damage, int speed){
        this.damage = damage;
        this.speed = speed;
        isShoot = false;
        isOnTheWay = false;
    }
}
