import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EnemyTank2 extends CombatVehicle {

    private static final int HEALTH = 50;
    private static final int SPEED = 3;


    public EnemyTank2() {

        setMobile(false);
        setHealth(HEALTH);
        setEnemy(true);

        try {
            setBody(ImageIO.read(new File("res/images/tanks/bodies/enemy2.png")));
        } catch (IOException e) {
        }

        setGuns(new ArrayList<Gun>());
        getGuns().add(GameConstants.getEnemyGun2());
        setActiveGun(getGuns().get(0));
        setGunAngle(0);

        setSpeed(SPEED);
    }

    @Override
    public void update() {
        super.update(SPEED);
    }

}
