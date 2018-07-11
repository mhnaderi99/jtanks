import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BigEnemyGunBullet extends Bullet{



    private static final int SPEED = 5;
    private static final int DAMAGE = 10;

    public BigEnemyGunBullet() {
        super(DAMAGE, SPEED);
        setEnemyBullet(true);
        setShootSound("enemyShot.wav");
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/bigEnemyGunBullet.png"));
        }
        catch (IOException e) { }
    }

    @Override
    public Bullet getBullet() {
        return new BigEnemyGunBullet();
    }
}
