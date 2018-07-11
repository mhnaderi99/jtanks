import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BigEnemyTank extends CombatVehicle {

    private static final int HEALTH = 60;
    private static final int SPEED = 0;


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

    @Override
    public void update() {
        super.update(SPEED);
    }

}
