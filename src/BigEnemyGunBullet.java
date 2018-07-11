import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * the big-enemy gun bullet inherits the bullet class
 */
public class BigEnemyGunBullet extends Bullet{



    private static final int SPEED = 5;
    private static final int DAMAGE = 10;

    /**
     * the constructor of big-enemy gun bullet
     */
    public BigEnemyGunBullet() {
        super(DAMAGE, SPEED);
        setEnemyBullet(true);
        setShootSound("enemyShot.wav");
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/bigEnemyGunBullet.png"));
        }
        catch (IOException e) { }
    }

    /**
     * to get the big-enemy gun bullet
     * @return big-enemy gun bullet
     */
    @Override
    public Bullet getBullet() {
        return new BigEnemyGunBullet();
    }
}
