import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * the cannon bullet inherits the bullet class
 */

public class CannonBullet extends Bullet {

    private static final int SPEED = 5;
    private static final int DAMAGE = 10;

    /**
     * the constructor of the cannon bullet
     */
    public CannonBullet() {
        super(DAMAGE, SPEED);
        setShootSound("cannon.wav");
        setEnemyBullet(false);
        setReloadPeriod(GameConstants.getCannonPeriod());
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/cannonBullet.png"));
        }
        catch (IOException e) { }
    }

    /**
     * to get the cannon bullet
     * @return the cannon bullet
     */
    @Override
    public Bullet getBullet() {
        return new CannonBullet();
    }
}
