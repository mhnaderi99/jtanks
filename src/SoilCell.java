import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SoilCell extends MapCell {

    public SoilCell() {
        try {
            setImage(ImageIO.read(new File("res/images/map/soilCell.png")));
        }
        catch (IOException e) { }
        setDestroyable(false);
        setBarrier(false);
        setBarrierForBullet(false);
        setTransparent(false);

    }

    @Override
    public void destroy(Bullet bullet) {

    }
}
