import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * this class inherits combat vehicle
 */
public class EnemyTank2 extends CombatVehicle {

    private static final int HEALTH = 50;
    private static final int SPEED = 3;


    /**
     * the constructor of the enemy tank (2)
     */
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

    /**
     * //todo
     */
    @Override
    public void update() {
        super.update(SPEED);
    }

}
