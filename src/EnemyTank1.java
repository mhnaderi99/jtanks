import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * this class inherits combat vehicle
 */

public class EnemyTank1 extends CombatVehicle {

    private static final int HEALTH = 40 + 20*GameLoop.getDifficulty();
    private static final int SPEED = 3;

    /**
     * the constructor of the enemy tank (1)
     */
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

    /**
     * //todo
     */
    @Override
    public void update() {
        super.update(SPEED);
    }

}
