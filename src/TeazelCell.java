import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * this class inherits the map cell
 */

public class TeazelCell extends MapCell {

    /**
     * the constructor of the teazel cell
     */
    public TeazelCell() {
        try {
            setImage(ImageIO.read(new File("res/images/map/teazelCell.png")));
        }
        catch (IOException e) { }
        setDestroyable(false);
        setBarrier(true);
        setBarrierForBullet(false);
        setTransparent(true);
    }

    /**
     * it can't be destroyed
     * @param bullet the bullet
     */
    @Override
    public void destroy(Bullet bullet) {

    }
}
