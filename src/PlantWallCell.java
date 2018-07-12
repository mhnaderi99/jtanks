import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * this class inherits the map cell
 */

public class PlantWallCell extends MapCell{

    /**
     * the constructor of the plant wall cell
     */
    public PlantWallCell() {
        try {
            setImage(ImageIO.read(new File("res/images/map/plantWallCell.png")));
        }
        catch (IOException e) { }
        setDestroyable(false);
        setBarrier(true);
        setBarrierForBullet(true);
        setTransparent(false);
    }

    /**
     * it can't be destroyed
     * @param bullet the bullet
     */
    @Override
    public void destroy(Bullet bullet, int i, int j) {

    }
}
