import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * the enemy-gun bullet(2) class inherits the bullet class
 */

public class EnemyGunBullet2 extends Bullet {

    private static final int SPEED = 10;
    private static final int DAMAGE = 1;

    /**
     * the constructor of the enemy-gun bullet(2)
     */
    public EnemyGunBullet2() {
        super(DAMAGE, SPEED);
        setEnemyBullet(true);
        setShootSound("enemyShot.wav");
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/enemyBullet2.png"));
        }
        catch (IOException e) { }
    }

    /**
     * to get the enemy-gun bullet(2)
     * @return enemy-gun bullet(2)
     */
    @Override
    public Bullet getBullet() {
        return new EnemyGunBullet2();
    }
}
