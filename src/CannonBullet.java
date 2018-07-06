import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CannonBullet extends Bullet {

    private static final int SPEED = 10;
    private static final int DAMAGE = 20;

    public CannonBullet() {
        super(DAMAGE, SPEED);
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/cannonBullet.png"));
        }
        catch (IOException e) { }
    }
}
