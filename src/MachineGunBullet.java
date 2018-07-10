import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MachineGunBullet extends Bullet{

    private static final int SPEED = 10;
    private static final int DAMAGE = 1;

    public MachineGunBullet() {
        super(DAMAGE, SPEED);
        setReloadPeriod(GameConstants.getMachineGunPeriod());
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/machineGunBullet.png"));
        }
        catch (IOException e) { }
    }
}
