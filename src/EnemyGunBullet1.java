import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * the enemy-gun bullet(1) class inherits the bullet class
 */

public class EnemyGunBullet1 extends Bullet {

    private static final int SPEED = 5;
    private static final int DAMAGE = 5;

    /**
     * the constructor of the enemy-gun bullet(1)
     */
    public EnemyGunBullet1() {
        super(DAMAGE, SPEED);
        setEnemyBullet(true);
        setShootSound("enemyShot.wav");
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/enemyBullet1.png"));
        }
        catch (IOException e) { }
    }

    /**
     * to get the enemy-gun bullet(1)
     * @return enemy-gun bullet(1)
     */
    @Override
    public Bullet getBullet() {
        return new EnemyGunBullet1();
    }
}
