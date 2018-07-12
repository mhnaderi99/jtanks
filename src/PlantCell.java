import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * this class inherits the map cell
 */

public class PlantCell extends MapCell {

    /**
     * the constructor of the plant cell
     */
    public PlantCell() {
        try {
            setImage(ImageIO.read(new File("res/images/map/plantCell.png")));
        }
        catch (IOException e) { }
        setDestroyable(false);
        setBarrier(false);
        setBarrierForBullet(false);
        setTransparent(true);
    }

    /**
     * it can't be destroyed
     * @param bullet the bullet
     */
    @Override
    public void destroy(Bullet bullet, int i, int j) {

    }
}
