import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 *this class inherits the prize class
 */

public class MachineGunFood extends Prize{

    private static final int AMOUNT = 50;

    public MachineGunFood() {
        try {
            setImage(ImageIO.read(new File("res/images/prizes/machineGunFood.png")));
        }
        catch (IOException e) { }
    }

    /**
     * reloads the machine gun bullets
     * @param vehicle
     */
    @Override
    public void work(CombatVehicle vehicle) {
        vehicle.getGuns().get(1).reload(AMOUNT);
    }
}
