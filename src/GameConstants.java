import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
    private static final int INITIAL_NUMBER_OF_CANNON_BULLETS = 100;
    private static final int INITIAL_NUMBER_OF_MACHINE_GUN_BULLETS = 200;
    private static final int INITIAL_NUMBER_OF_ENEMY_GUN_BULLETS = Integer.MAX_VALUE;
    private static final int SOFT_WALL_CELL_HEALTH = 40;
    private static final int CANNON_PERIOD = 500;
    private static final int MACHINE_GUN_PERIOD = 200;
    private static final int ENEMY_FIRING_PEROID = 500;
    private static final int ENEMY_FIRING_ERROR = 0;
    private static final int BORDER_MARGIN = 10;
    private static final int HEALTH_CELLS = 5;

    private static Gun cannon;
    private static Gun machineGun;
    private static Gun enemyGun1;
    private static Gun enemyGun2;
    private static Gun smallEnemyGun;
    private static Gun bigEnemyGun;

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

    public static int getEnemyFiringPeroid() {
        return ENEMY_FIRING_PEROID;
    }

    public static int getEnemyFiringError() {
        return ENEMY_FIRING_ERROR;
    }

    public static int getBorderMargin() {
        return BORDER_MARGIN;
    }

    public static int getHealthCells() {
        return HEALTH_CELLS;
    }

    public static BufferedImage getCannonNumber(boolean isGrayscale) {
        try {
            if (isGrayscale) {
                return ImageIO.read(new File("res/images/ui/cannonG.png"));
            }
            else {
                return ImageIO.read(new File("res/images/ui/cannon.png"));
            }
        } catch (IOException e) { return null;}
    }

    public static BufferedImage getMachineGunNumber(boolean isGrayscale) {
        try {
            if (isGrayscale) {
                return ImageIO.read(new File("res/images/ui/machineGunG.png"));
            }
            else {
                return ImageIO.read(new File("res/images/ui/machineGun.png"));
            }
        } catch (IOException e) { return null;}
    }

    public static BufferedImage getHealth() {
        try {
            return ImageIO.read(new File("res/images/ui/health.png"));
        } catch (IOException e) { return null;}
    }

    public static BufferedImage getEmpty() {
        try {
            return ImageIO.read(new File("res/images/ui/empty.png"));
        } catch (IOException e) { return null;}
    }

    public static Gun getCannon() {
        try {
            cannon = new Gun(ImageIO.read(new File("res/images/tanks/guns/cannon.png")),INITIAL_NUMBER_OF_CANNON_BULLETS, new CannonBullet());
        }
        catch (IOException e) { }
        return cannon;
    }

    public static Gun getMachineGun() {
        try {
            machineGun = new Gun(ImageIO.read(new File("res/images/tanks/guns/machineGun.png")), INITIAL_NUMBER_OF_MACHINE_GUN_BULLETS, new MachineGunBullet());
        }
        catch (IOException e) { }
        return machineGun;
    }

    public static Gun getEnemyGun1() {

        try {
            enemyGun1 = new Gun(ImageIO.read(new File("res/images/tanks/guns/enemyGun1.png")), INITIAL_NUMBER_OF_ENEMY_GUN_BULLETS, new EnemyGunBullet1());
        }
        catch (IOException e) { }
        return enemyGun1;
    }

    public static Gun getEnemyGun2() {

        try {
            enemyGun2 = new Gun(ImageIO.read(new File("res/images/tanks/guns/enemyGun2.png")), INITIAL_NUMBER_OF_ENEMY_GUN_BULLETS, new EnemyGunBullet2());
        }
        catch (IOException e) { }
        return enemyGun2;
    }

    public static Gun getSmallEnemyGun() {

        try {
            smallEnemyGun = new Gun(ImageIO.read(new File("res/images/tanks/guns/smallEnemyGun.png")), INITIAL_NUMBER_OF_ENEMY_GUN_BULLETS, new SmallEnemyGunBullet());
        }
        catch (IOException e) { }
        return smallEnemyGun;
    }

    public static Gun getBigEnemyGun() {

        try {
            bigEnemyGun = new Gun(ImageIO.read(new File("res/images/tanks/guns/bigEnemyGun.png")), INITIAL_NUMBER_OF_ENEMY_GUN_BULLETS, new BigEnemyGunBullet());
        }
        catch (IOException e) { }
        return bigEnemyGun;
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

    public static CombatVehicle getEnemyByCode(int code) {
        if (code == 1) {
            return new EnemyTank1();
        }
        if (code == 2) {
            return new SmallEnemyTank();
        }
        if (code == 3) {
            return new BigEnemyTank();
        }
        if (code == 4) {
            return new EnemyTank2();
        }
        return null;
    }
}
