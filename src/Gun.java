import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class Gun {

    private BufferedImage image;
    private boolean infinityBullets;
    private Bullet type;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> movingBullets;

    public Gun(BufferedImage image, int numberOfBullets, Bullet type) {
        this.image = image;
        this.type = type;
        if (numberOfBullets == Integer.MAX_VALUE) {
            infinityBullets = true;
        }
        else {
            infinityBullets = false;
        }
        bullets = new ArrayList<Bullet>();
        movingBullets = new ArrayList<Bullet>();
        if (! infinityBullets) {
            reload(numberOfBullets);
        }
    }


    public Bullet getType() {
        return type;
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
        if (infinityBullets) {
            return false;
        }
        return bullets.isEmpty();
    }

    public void shoot(double theta) {

        if (! isBulletsEmpty()) {
            if (infinityBullets) {
                if (bullets.isEmpty()) {
                    reload(GameConstants.getAmount());
                }
            }
            Bullet bullet = bullets.get(bullets.size() - 1);
            bullet.setShoot(true);
            bullet.setOnTheWay(true);
            bullet.setXSpeed (bullet.getSpeed() * Math.cos(theta));
            bullet.setYSpeed(-(bullet.getSpeed() * Math.sin(theta)));
            AudioPlayer.playSound(getType().getShootSound());
            movingBullets.add(bullet);

            bullets.remove(bullets.size() - 1);
        }
        else {
            AudioPlayer.playSound("emptyGun.wav");
        }
    }

    public void update() {
        Iterator<Bullet> iter = movingBullets.iterator();

        while (iter.hasNext()) {
            Bullet bullet = null;
            try {
                bullet = iter.next();
            }
            catch (ConcurrentModificationException e) {
                break;
            }

            if (bullet.isShoot() && bullet.isOnTheWay()){
                bullet.update();
            }
            if (bullet.isShoot() && ! bullet.isOnTheWay()) {
                try {
                    iter.remove();
                } catch (ConcurrentModificationException e) {
                    break;
                }
            }
        }



    }

    public void reload(int number) {
        if (number == (int)Double.POSITIVE_INFINITY) {
            return;
        }
        for (int i = 0; i < number; i++) {
            bullets.add(getType().getBullet());
        }
    }


}
