import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SmallEnemyGunBullet extends Bullet {

    private static final int SPEED = 10;
    private static final int DAMAGE = 2;

    public SmallEnemyGunBullet() {
        super(DAMAGE, SPEED);
        setEnemyBullet(true);
        setShootSound("enemyShot.wav");
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/smallEnemyGunBullet.png"));
        }
        catch (IOException e) { }
    }

    @Override
    public Bullet getBullet() {
        return new SmallEnemyGunBullet();
    }
}
