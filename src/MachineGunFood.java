import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MachineGunFood extends Prize{

    private static final int AMOUNT = 50;

    public MachineGunFood() {
        try {
            setImage(ImageIO.read(new File("res/images/prizes/machineGunFood.png")));
        }
        catch (IOException e) { }
    }
    @Override
    public void work(CombatVehicle vehicle) {
        vehicle.getGuns().get(1).reload(AMOUNT);
    }
}
