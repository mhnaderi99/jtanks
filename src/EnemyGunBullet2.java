import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class EnemyGunBullet2 extends Bullet {

    private static final int SPEED = 10;
    private static final int DAMAGE = 1;

    public EnemyGunBullet2() {
        super(DAMAGE, SPEED);
        setEnemyBullet(true);
        setShootSound("enemyShot.wav");
        try {
            image = ImageIO.read(new File("res/images/tanks/bullets/enemyBullet2.png"));
        }
        catch (IOException e) { }
    }

    @Override
    public Bullet getBullet() {
        return new EnemyGunBullet2();
    }
}
