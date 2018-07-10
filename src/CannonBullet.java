import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CannonBullet extends Bullet {

    private static final int SPEED = 5;
    private static final int DAMAGE = 10;

    public CannonBullet() {
        super(DAMAGE, SPEED);
        setReloadPeriod(GameConstants.getCannonPeriod());
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/cannonBullet.png"));
        }
        catch (IOException e) { }
    }
}
