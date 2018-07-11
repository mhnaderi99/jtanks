import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * the small-enemy gun bullet inherits the bullet class
 */

public class SmallEnemyGunBullet extends Bullet {

    private static final int SPEED = 10;
    private static final int DAMAGE = 2;

    /**
     * the constructor of small-enemy gun bullet
     */
    public SmallEnemyGunBullet() {
        super(DAMAGE, SPEED);
        setEnemyBullet(true);
        setShootSound("enemyShot.wav");
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/smallEnemyGunBullet.png"));
        }
        catch (IOException e) { }
    }

    /**
     * to get the small-enemy gun bullet
     * @return small-enemy gun bullet
     */
    @Override
    public Bullet getBullet() {
        return new SmallEnemyGunBullet();
    }
}
