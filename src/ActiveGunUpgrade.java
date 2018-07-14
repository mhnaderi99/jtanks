import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * this class inherits the prize class
 */

public class ActiveGunUpgrade extends Prize{

    private static final int SPEED_UPGRADE = 2;
    private static final int DAMAGE_UPGRADE = 2;

    /**
     * //todo
     */
    public ActiveGunUpgrade() {
        try {
            setImage(ImageIO.read(new File("res/images/prizes/activeGunUpgrade.png")));
        }
        catch (IOException e) { }
    }

    /**
     * upgraded guns have faster bullets and stronger ones
     * @param vehicle
     */
    @Override
    public void work(CombatVehicle vehicle) {
        for(Bullet bullet: vehicle.getActiveGun().getBullets()) {
            bullet.setSpeed(bullet.getSpeed() + SPEED_UPGRADE);
            bullet.setDamage(bullet.getDamage() + DAMAGE_UPGRADE);
        }
        vehicle.getActiveGun().getType().getBullet().setSpeed(vehicle.getActiveGun().getType().getBullet().getSpeed() + SPEED_UPGRADE);
        vehicle.getActiveGun().getType().getBullet().setDamage(vehicle.getActiveGun().getType().getBullet().getDamage() + DAMAGE_UPGRADE);
    }
}
