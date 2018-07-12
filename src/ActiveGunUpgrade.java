import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ActiveGunUpgrade extends Prize{

    private static final int SPEED_UPGRADE = 2;
    private static final int DAMAGE_UPGRADE = 2;

    public ActiveGunUpgrade() {
        try {
            setImage(ImageIO.read(new File("res/images/prizes/activeGunUpgrade.png")));
        }
        catch (IOException e) { }
    }

    @Override
    public void work(CombatVehicle vehicle) {
        vehicle.getActiveGun().getType().setSpeed(vehicle.getActiveGun().getType().getSpeed() + SPEED_UPGRADE);
        vehicle.getActiveGun().getType().setDamage(vehicle.getActiveGun().getType().getDamage() + DAMAGE_UPGRADE);
    }
}
