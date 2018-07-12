import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class HealthPrize extends Prize {

    private static final int AMOUNT = 50;

    public HealthPrize() {
        try {
            setImage(ImageIO.read(new File("res/images/prizes/healthPrize.png")));
        }
        catch (IOException e) { }
    }

    @Override
    public void work(CombatVehicle vehicle) {

        try {
            setImage(ImageIO.read(new File("res/images/prizes/healthPrize.png")));
        }
        catch (IOException e) { }

        vehicle.setHealth(vehicle.getHealth() + AMOUNT);
    }
}
