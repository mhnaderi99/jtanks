import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * the game constant values have been kept here
 */

public class GameConstants {

    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;
    private static final int CELL_WIDTH = 120;
    private static final int CELL_HEIGHT = 120;
    private static final int STEP = 10;
    private static final int H_AMOUNT = 400;
    private static final int V_AMOUNt = 200;
    private static final int NUM = 4;
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
    private static final int ENEMY_TYPES = 4;
    private static final int PRIZE_TYPES = 4;
    private static final int PRIZE_CHANCE = 4;
    private static final Random random = new Random(2018);
    private static final String HEALTH_REPAIR_CODE = "jun";
    private static final String CANNON_FOOD = "tup";
    private static final String MACHINE_GUN_FOOD = "ragbar";

    private static Gun cannon;
    private static Gun machineGun;
    private static Gun enemyGun1;
    private static Gun enemyGun2;
    private static Gun smallEnemyGun;
    private static Gun bigEnemyGun;

    private static final SoilCell soilCell = new SoilCell();

    /**
     * to get the step of the tanks
     * @return the step of the tanks
     */
    public static int getStep() {
        return STEP;
    }

    public static int gethAmount() {
        return H_AMOUNT;
    }

    public static int getvAmount() {
        return V_AMOUNt;
    }

    /**
     * to get the num
     * @return the num
     */
    public static int getNum() {
        return NUM;
    }

    /**
     * to get the screen width
     * @return the screen width
     */
    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    /**
     * to get the soft-wall cell health
     * @return soft-wall cell health
     */
    public static int getSoftWallCellHealth() {
        return SOFT_WALL_CELL_HEALTH;
    }

    /**
     * to get the screen height
     * @return the screen height
     */
    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    /**
     * to get the each cell width
     * @return cell width
     */
    public static int getCellWidth() {
        return CELL_WIDTH;
    }

    /**
     * to get the each cell height
     * @return cell height
     */
    public static int getCellHeight() {
        return CELL_HEIGHT;
    }

    /**
     * to get the cannon period
     * @return the cannon period
     */
    public static int getCannonPeriod() {
        return CANNON_PERIOD;
    }

    /**
     * to get the machine gun period
     * @return the machine gun period
     */
    public static int getMachineGunPeriod() {
        return MACHINE_GUN_PERIOD;
    }

    /**
     * to get the enemy firing period
     * @return the enemy firing period
     */
    public static int getEnemyFiringPeroid() {
        return ENEMY_FIRING_PEROID;
    }

    /**
     * to get enemy firing error
     * @return enemy firing error
     */
    public static int getEnemyFiringError() {
        return ENEMY_FIRING_ERROR;
    }

    /**
     * to get border margin
     * @return the border margin
     */
    public static int getBorderMargin() {
        return BORDER_MARGIN;
    }

    /**
     * to get health cells
     * @return the health cells
     */
    public static int getHealthCells() {
        return HEALTH_CELLS;
    }

    /**
     * to get enemy types
     * @return the  enemy types
     */
    public static int getEnemyTypes() {
        return ENEMY_TYPES;
    }

    /**
     * to get the prize chance
     * @return the prize chance
     */
    public static int getPrizeChance() {
        return PRIZE_CHANCE;
    }

    /**
     * to get a random number
     * @return a random number
     */
    public static Random getRandom() {
        return random;
    }

    /**
     * to get the active bullet and disActive bullets of cannon gun
     * @param isGrayscale if is disActive or not
     * @return the image
     */
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

    /**
     * to get the active bullet and disActive bullets of machine gun
     * @param isGrayscale if is disActive or not
     * @return the image
     */
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

    /**
     * to get the full health image
     * @return the image
     */
    public static BufferedImage getHealth() {
        try {
            return ImageIO.read(new File("res/images/ui/health.png"));
        } catch (IOException e) { return null;}
    }

    /**
     * to get the empty health image
     * @return the image
     */
    public static BufferedImage getEmpty() {
        try {
            return ImageIO.read(new File("res/images/ui/empty.png"));
        } catch (IOException e) { return null;}
    }

    /**
     * to get the cannon gun
     * @return the cannon gun
     */
    public static Gun getCannon() {
        try {
            cannon = new Gun(ImageIO.read(new File("res/images/tanks/guns/cannon.png")),INITIAL_NUMBER_OF_CANNON_BULLETS, new CannonBullet());
        }
        catch (IOException e) { }
        return cannon;
    }

    /**
     * to get the machine-gun
     * @return the machine-gun
     */
    public static Gun getMachineGun() {
        try {
            machineGun = new Gun(ImageIO.read(new File("res/images/tanks/guns/machineGun.png")), INITIAL_NUMBER_OF_MACHINE_GUN_BULLETS, new MachineGunBullet());
        }
        catch (IOException e) { }
        return machineGun;
    }

    /**
     * to get the enemy gun (1)
     * @return the enemy gun (1)
     */
    public static Gun getEnemyGun1() {

        try {
            enemyGun1 = new Gun(ImageIO.read(new File("res/images/tanks/guns/enemyGun1.png")), INITIAL_NUMBER_OF_ENEMY_GUN_BULLETS, new EnemyGunBullet1());
        }
        catch (IOException e) { }
        return enemyGun1;
    }

    /**
     * to get the enemy gun (2)
     * @return  the enemy gun (2)
     */
    public static Gun getEnemyGun2() {

        try {
            enemyGun2 = new Gun(ImageIO.read(new File("res/images/tanks/guns/enemyGun2.png")), INITIAL_NUMBER_OF_ENEMY_GUN_BULLETS, new EnemyGunBullet2());
        }
        catch (IOException e) { }
        return enemyGun2;
    }

    /**
     * to get the small enemy gun
     * @return the small enemy gun
     */
    public static Gun getSmallEnemyGun() {

        try {
            smallEnemyGun = new Gun(ImageIO.read(new File("res/images/tanks/guns/smallEnemyGun.png")), INITIAL_NUMBER_OF_ENEMY_GUN_BULLETS, new SmallEnemyGunBullet());
        }
        catch (IOException e) { }
        return smallEnemyGun;
    }

    /**
     * to get the big enemy gun
     * @return the big enemy gun
     */
    public static Gun getBigEnemyGun() {

        try {
            bigEnemyGun = new Gun(ImageIO.read(new File("res/images/tanks/guns/bigEnemyGun.png")), INITIAL_NUMBER_OF_ENEMY_GUN_BULLETS, new BigEnemyGunBullet());
        }
        catch (IOException e) { }
        return bigEnemyGun;
    }

    /**
     * to covert the numeric codes of map to cells
     * @param code the numeric code
     * @return the map cell
     */
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

    /**
     * to get the enemy type
     * @param code the numeric code
     * @return the enemy type
     */
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

    /**
     * to get a prize by code
     * @param code the code
     * @return the prize
     */
    public static Prize getPrizeByCode(int code) {
        if (code == 5) {
            return new HealthPrize();
        }
        if (code == 6) {
            return new CannonFood();
        }
        if (code == 7) {
            return new MachineGunFood();
        }
        if (code == 8) {
            return new ActiveGunUpgrade();
        }
        return null;
    }

    /**
     * generates a random prize
     * @return the random prize
     */
    public static Prize randomPrize() {
        int n = PRIZE_TYPES;
        int rand = new Random().nextInt(n) + 1;
        if (rand == 1) {
            return new HealthPrize();
        }
        if (rand == 2) {
            return new CannonFood();
        }
        if (rand == 3) {
            return new MachineGunFood();
        }
        if (rand == 4) {
            return new ActiveGunUpgrade();
        }
        return null;
    }

    /**
     * determines the prize code
     * @param prize the prize
     * @return the code
     */
    public static int getPrizeCode(Prize prize) {
        if(prize instanceof HealthPrize) {
            return 5;
        }
        if (prize instanceof  CannonFood) {
            return 6;
        }
        if (prize instanceof  MachineGunFood) {
            return 7;
        }
        if (prize instanceof  ActiveGunUpgrade) {
            return 8;
        }
        return 0;
    }

    /**
     * for processing the cheat codes
     * @param tank the tank which cheat code is applied to
     * @param cheatCode the cheat code
     */
    public static void processCheatCode(Tank tank, String cheatCode) {
        cheatCode = cheatCode.toLowerCase();

        if (cheatCode.equals(HEALTH_REPAIR_CODE)) {
            tank.setHealth(Tank.getHEALTH());
        }
        if (cheatCode.equals(CANNON_FOOD)) {
            tank.getGuns().get(0).reload(100);
        }
        if (cheatCode.equals(MACHINE_GUN_FOOD)) {
            tank.getGuns().get(1).reload(200);
        }
    }
}
