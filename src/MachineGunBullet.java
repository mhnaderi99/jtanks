import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * the machine-gun bullet class inherits the bullet class
 */

public class MachineGunBullet extends Bullet{

    private static final int SPEED = 10;
    private static final int DAMAGE = 1;

    /**
     * the constructor of the machine-gun bullet
     */
    public MachineGunBullet() {
        super(DAMAGE, SPEED);
        setShootSound("machineGun.wav");
        setEnemyBullet(false);
        setReloadPeriod(GameConstants.getMachineGunPeriod());
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/machineGunBullet.png"));
        }
        catch (IOException e) { }
    }

    /**
     * to get the machine-gun bullet
     * @return machine-gun bullet
     */
    @Override
    public Bullet getBullet() {
        return new MachineGunBullet();
    }
}
