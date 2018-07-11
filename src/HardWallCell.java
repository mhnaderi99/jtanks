import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class HardWallCell extends MapCell{

    public HardWallCell() {
        try {
            setImage(ImageIO.read(new File("res/images/map/hardWallCell.png")));
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
