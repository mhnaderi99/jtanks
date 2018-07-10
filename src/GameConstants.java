import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GameConstants {

    private static final double SCREEN_RATIO = 9/16;
    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;
    private static final int CELL_WIDTH = 120;
    private static final int CELL_HEIGHT = 120;
    private static final int STEP = 10;
    private static final int AMOUNT = 300;
    private static final int NUM = 8;
    private static final int SOFT_WALL_CELL_HEALTH = 40;
    private static final int CANNON_PERIOD = 500;
    private static final int MACHINE_GUN_PERIOD = 200;

    private static Gun cannon;
    private static Gun machineGun;
    private static Bullet cannonBullet;
    private static Bullet machineGunBullet;
    private static final SoilCell soilCell = new SoilCell();

    public static int getStep() {
        return STEP;
    }

    public static int getAmount() {
        return AMOUNT;
    }

    public static int getNum() {
        return NUM;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getSoftWallCellHealth() {
        return SOFT_WALL_CELL_HEALTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static int getCellWidth() {
        return CELL_WIDTH;
    }

    public static int getCellHeight() {
        return CELL_HEIGHT;
    }

    public static int getCannonPeriod() {
        return CANNON_PERIOD;
    }

    public static int getMachineGunPeriod() {
        return MACHINE_GUN_PERIOD;
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

    public static MapCell getCellByCode(int code) {

        if (code == 1) {
            return new PlantCell();
        }

        if (code == 2) {
            return new HardWallCell();
        }

        if (code == 3) {
            return new SoftWallCell();
        }

        if (code == 4) {
            return soilCell;
        }

        if (code == 5) {
            return new TeazelCell();
        }

        if (code == 6) {
            return new PlantWallCell();
        }

        return null;
    }
}
