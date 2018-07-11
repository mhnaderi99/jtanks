import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EnemyTank1 extends CombatVehicle {

    private static final int HEALTH = 40;
    private static final int SPEED = 2;


    public EnemyTank1() {

        setMobile(true);
        setHealth(HEALTH);
        setEnemy(true);

        try {
            setBody(ImageIO.read(new File("res/images/tanks/bodies/enemy1.png")));
        } catch (IOException e) {
        }

        setGuns(new ArrayList<Gun>());
        getGuns().add(GameConstants.getEnemyGun1());
        setActiveGun(getGuns().get(0));
        setGunAngle(0);

        setSpeed(SPEED);
    }

    @Override
    public void update() {
        super.update(SPEED);
    }

}
