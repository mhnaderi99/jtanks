import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SoftWallCell extends MapCell {

    public SoftWallCell() {
        try {
            setImage(ImageIO.read(new File("res/images/map/softWallCell.png")));
        }
        catch (IOException e) { }
        setDestroyable(true);
        setBarrier(true);
    }
}
