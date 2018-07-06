import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MachineGunBullet extends Bullet{

    private static final int SPEED = 20;
    private static final int DAMAGE = 10;

    public MachineGunBullet() {
        super(DAMAGE, SPEED);
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/machineGunBullet.png"));
        }
        catch (IOException e) { }
    }
}
