import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * this class inherits the combat vehicle
 */
public class BigEnemyTank extends CombatVehicle {

    private static final int HEALTH = 60;
    private static final int SPEED = 0;


    /**
     * the constructor of big enemy tank
     */
    public BigEnemyTank() {

        setMobile(false);
        setHealth(HEALTH);
        setEnemy(true);

        try {
            setBody(ImageIO.read(new File("res/images/tanks/bodies/bigEnemy.png")));
        } catch (IOException e) {
        }

        setGuns(new ArrayList<Gun>());
        getGuns().add(GameConstants.getBigEnemyGun());
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
