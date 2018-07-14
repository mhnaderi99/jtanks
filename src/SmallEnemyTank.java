import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * this class inherits the combat vehicle
 */
public class SmallEnemyTank extends CombatVehicle {

    private static final int HEALTH = 30 + 20*GameLoop.getDifficulty();
    private static final int SPEED = 6;


    /**
     * the constructor of small enemy tank
     */
    public SmallEnemyTank() {

        setMobile(true);
        setHealth(HEALTH);
        setEnemy(true);

        try {
            setBody(ImageIO.read(new File("res/images/tanks/bodies/smallEnemy.png")));
        } catch (IOException e) {
        }

        setGuns(new ArrayList<Gun>());
        getGuns().add(GameConstants.getSmallEnemyGun());
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
