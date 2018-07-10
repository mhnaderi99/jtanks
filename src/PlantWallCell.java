import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PlantWallCell extends MapCell{

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

    @Override
    public void destroy(Bullet bullet) {

    }
}
