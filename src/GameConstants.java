import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GameConstants {

    private static final double SCREEN_RATIO = 9/16;
    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;
    private static Gun cannon;
    private static Gun machineGun;
    private static Bullet cannonBullet;
    private static Bullet machineGunBullet;

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static Gun getCannon() {
        try {
            cannon = new Gun(ImageIO.read(new File("res/images/tanks/guns/cannon.png")),100, new CannonBullet());
        }
        catch (IOException e) { }
        return cannon;
    }

    public static Gun getMachineGun() {
        try {
            machineGun = new Gun(ImageIO.read(new File("res/images/tanks/guns/machineGun.png")), 200, new MachineGunBullet());
        }
        catch (IOException e) { }
        return machineGun;
    }

    public static Bullet getCannonBullet() {
        cannonBullet = new CannonBullet();
        return cannonBullet;
    }

    public static Bullet getMachineGunBullet() {
        machineGunBullet = new MachineGunBullet();
        return machineGunBullet;
    }

}
