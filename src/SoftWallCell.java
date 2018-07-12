import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * this class inherits the map cell
 */

public class SoftWallCell extends MapCell {

    private int health;
    private static final int DEFAULT_HEALTH = 40;
    private static final int NUMBER_OF_STATES = 4;

    /**
     * the constructor of the soft wall cell
     */
    public SoftWallCell() {
        try {
            setImage(ImageIO.read(new File("res/images/map/softWallCell0.png")));
        }
        catch (IOException e) { }
        setDestroyable(true);
        setBarrier(true);
        setBarrierForBullet(true);
        setTransparent(true);
        health = GameConstants.getSoftWallCellHealth();
    }

    /**
     * the soft wall cell is the only map cell can be destroyed
     * @param bullet the bullet the bullet
     */
    @Override
    public void destroy(Bullet bullet, int i, int j) {
        if (health > 0) {
            health -= bullet.getDamage();
            AudioPlayer.playSound("softWallDamage.wav");
            double h = (double) health / (double) (DEFAULT_HEALTH / NUMBER_OF_STATES);
            int index = (int) Math.ceil(h);
            index = NUMBER_OF_STATES - index;
            if (index == NUMBER_OF_STATES) {
                Random random = new Random();
                setBarrier(false);
                setBarrierForBullet(false);
                setTransparent(false);
                if (random.nextInt(100) % GameConstants.getPrizeChance() == 0) {
                    Prize prize = GameConstants.randomPrize();
                    GameLoop.getState().getMap().placePrize(prize, i, j);
                }
            }
            try {
                setImage(ImageIO.read(new File("res/images/map/softWallCell" + index + ".png")));
                GameLoop.getCanvas().render(GameLoop.getState(), true);
            } catch (IOException e) {
            }
        }
    }
}
