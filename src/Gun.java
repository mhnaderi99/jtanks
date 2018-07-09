import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class Gun {

    private BufferedImage image;
    private Bullet type;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> movingBullets;

    public Gun(BufferedImage image, int numberOfBullets, Bullet type) {
        this.image = image;
        this.type = type;
        bullets = new ArrayList<Bullet>();
        movingBullets = new ArrayList<Bullet>();
        reload(numberOfBullets);
    }

    public ArrayList<Bullet> getMovingBullets() {
        return movingBullets;
    }

    public BufferedImage getImage() {
        return image;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public boolean isBulletsEmpty() {
        return bullets.isEmpty();
    }

    public void shoot(double theta) {

        if (! isBulletsEmpty()) {
            Bullet bullet = bullets.get(bullets.size() - 1);
            bullet.isShoot = true;
            bullet.isOnTheWay = true;
            bullet.XSpeed = (bullet.speed * Math.cos(theta));
            bullet.YSpeed = - (bullet.speed * Math.sin(theta));
            movingBullets.add(bullet);
            bullets.remove(bullets.size() - 1);
        }
    }

    public void update() {
        Iterator<Bullet> iter = movingBullets.iterator();

        while (iter.hasNext()) {
            Bullet bullet = iter.next();

            if (bullet.isShoot && bullet.isOnTheWay){
                bullet.update();
            }
            if (bullet.isShoot && ! bullet.isOnTheWay) {
                iter.remove();
                bullet = null;
            }
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
