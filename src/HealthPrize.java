import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 *this class inherits the prize class
 */

public class HealthPrize extends Prize {

    private static final int AMOUNT = 50;

    public HealthPrize() {
        try {
            setImage(ImageIO.read(new File("res/images/prizes/healthPrize.png")));
        }
        catch (IOException e) { }
    }

    /**
     * reloads the health
     * @param vehicle
     */
    @Override
    public void work(CombatVehicle vehicle) {

        try {
            setImage(ImageIO.read(new File("res/images/prizes/healthPrize.png")));
        }
        catch (IOException e) { }

        vehicle.setHealth(Math.max(vehicle.getHealth() + AMOUNT, Tank.getHEALTH()));
    }
}
