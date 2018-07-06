import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Gun {

    private BufferedImage image;
    private Bullet type;
    private ArrayList<Bullet> bullets;

    public Gun(BufferedImage image, int numberOfBullets, Bullet type) {
        this.image = image;
        this.type = type;
        bullets = new ArrayList<Bullet>();
        reload(numberOfBullets);
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isEmpty() {
        return bullets.isEmpty();
    }

    public void shoot(double theta) {
        if (! isEmpty()) {
            Bullet bullet = bullets.get(0);
            bullet.isShoot = true;
            bullet.XSpeed = (int) (bullet.speed * Math.cos(theta));
            bullet.YSpeed = (int) (bullet.speed * Math.sin(theta));

            bullet.isOnTheWay = false;
            bullets.remove(0);
        }
    }

    public void reload(int number) {
        if (type instanceof CannonBullet) {
            for (int i = 0; i < number; i++) {
                bullets.add(new CannonBullet());
            }
        }
        if (type instanceof MachineGunBullet) {
            for (int i = 0; i < number; i++) {
                bullets.add(new MachineGunBullet());
            }
        }
    }
}
