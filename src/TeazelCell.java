import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TeazelCell extends MapCell {

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

    @Override
    public void destroy(Bullet bullet) {

    }
}
