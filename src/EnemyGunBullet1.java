import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class EnemyGunBullet1 extends Bullet {

    private static final int SPEED = 5;
    private static final int DAMAGE = 5;

    public EnemyGunBullet1() {
        super(DAMAGE, SPEED);
        setEnemyBullet(true);
        setShootSound("enemyShot.wav");
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/enemyBullet1.png"));
        }
        catch (IOException e) { }
    }

    @Override
    public Bullet getBullet() {
        return new EnemyGunBullet1();
    }
}
