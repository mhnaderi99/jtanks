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
            health = Math.max(health, 0);
            AudioPlayer.playSound("softWallDamage.wav");
            double h = (double) health / (double) (DEFAULT_HEALTH / NUMBER_OF_STATES);
            if (health < 0) {

            }
            int index = (int) Math.ceil(h);
            index = NUMBER_OF_STATES - index;
            if (index == NUMBER_OF_STATES) {
                Random random = GameConstants.getRandom();
                setBarrier(false);
                setBarrierForBullet(false);
                setTransparent(false);
                if (random.nextInt(100) % GameConstants.getPrizeChance() == 0) {
                    Prize prize = GameConstants.randomPrize();
                    int code = GameConstants.getPrizeCode(prize);
                    GameLoop.getState().getMap().placePrize(prize, i, j);
                    sendPrize(i,j,code);
                }
            }
            try {
                setImage(ImageIO.read(new File("res/images/map/softWallCell" + index + ".png")));
                GameLoop.getCanvas().render(GameLoop.getState(), true, 0);
            } catch (IOException e) {
            }
        }
        else {
        }
    }

    /**
     * to send the revealed prizes
     * @param i the x of the prize
     * @param j the y of the prize
     * @param code the code of the prize
     */
    private void sendPrize(int i, int j, int code) {
        String pre = "P-";
        String message = "" + i + "," + "," + code;
        String sender = "";
        if (GameLoop.getMode() == 1) {
            sender = "SERVER-";
            ClientHandler.writeOnStream(sender + pre + message);
        }
        if (GameLoop.getMode() == 2) {
            sender = "CLIENT-";
            Client.writeOnStream(sender + pre + message);
        }
    }
}
