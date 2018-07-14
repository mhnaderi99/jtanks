import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 *
 */
public class Gun implements Serializable {

    private transient BufferedImage image;
    private boolean infinityBullets;
    private Bullet type;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> movingBullets;

    /**
     * the constructor of the gun
     * @param image the image of the gun
     * @param numberOfBullets the number of bullets of the gun
     * @param type the type of the gun
     */
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

    /**
     * to set the image
     * @param image the image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * to get the type of the gun
     * @return the type
     */


    public Bullet getType() {
        return type;
    }

    /**
     * to get moving bullets (bullets on the way)  of a gun
     * @return moving bullets
     */
    public ArrayList<Bullet> getMovingBullets() {
        return movingBullets;
    }

    /**
     * to get the image of the gun
     * @return image of the gun
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * to get bullets of the gun
     * @return the bullets
     */
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    /**
     * to check if the gun bullets are finished or not
     * @return true or false
     */
    public boolean isBulletsEmpty() {
        if (infinityBullets) {
            return false;
        }
        return bullets.isEmpty();
    }

    /**
     * to shoot
     * @param theta the angle of shooting
     */
    public void shoot(double theta) {
        if (! isBulletsEmpty()) {
            if (infinityBullets) {
                if (bullets.isEmpty()) {
                    reload(GameConstants.getvAmount());
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

    /**
     * to update the guns and bullets
     */
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

    /**
     * to reload the gun
     * @param number number of bullets
     */
    public void reload(int number) {
        if (number == (int)Double.POSITIVE_INFINITY) {
            return;
        }
        for (int i = 0; i < number; i++) {
            bullets.add(getType().getBullet());
        }
        if (! infinityBullets) {
            AudioPlayer.playSound("recosh.wav");
        }
    }


}
