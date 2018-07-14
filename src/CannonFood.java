import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 *this class inherits the prize class
 */

public class CannonFood extends Prize{

    private static final int AMOUNT = 50;

    public CannonFood() {
        try {
            setImage(ImageIO.read(new File("res/images/prizes/cannonFood.png")));
        }
        catch (IOException e) { }
    }

    /**
     * reloads the cannon bullets
     * @param vehicle
     */
    @Override
    public void work(CombatVehicle vehicle) {

        vehicle.getGuns().get(0).reload(AMOUNT);
    }
}
