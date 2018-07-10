import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SoftWallCell extends MapCell {

    private int health;
    private static final int DEFAULT_HEALTH = 40;
    private static final int NUMBER_OF_STATES = 4;

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

    @Override
    public void destroy(Bullet bullet) {
        health -= bullet.damage;
        double h = (double) health / (double) (DEFAULT_HEALTH / NUMBER_OF_STATES);
        int index = (int) Math.ceil(h);
        index = NUMBER_OF_STATES - index;
        if (index == NUMBER_OF_STATES) {
            setBarrier(false);
            setBarrierForBullet(false);
            setTransparent(false);
        }
        try {
            setImage(ImageIO.read(new File("res/images/map/softWallCell" + index + ".png")));
            GameLoop.getCanvas().render(GameLoop.getState(), true);
        }
        catch (IOException e) { }
    }
}
