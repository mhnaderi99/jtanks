import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CannonFood extends Prize{

    private static final int AMOUNT = 50;

    public CannonFood() {
        try {
            setImage(ImageIO.read(new File("res/images/prizes/cannonFood.png")));
        }
        catch (IOException e) { }
    }
    @Override
    public void work(CombatVehicle vehicle) {

        vehicle.getGuns().get(0).reload(AMOUNT);
    }
}
