import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SmallEnemyTank extends CombatVehicle {

    private static final int HEALTH = 30;
    private static final int SPEED = 3;


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

    @Override
    public void update() {
        super.update(SPEED);
    }

}
