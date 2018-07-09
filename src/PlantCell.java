import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PlantCell extends MapCell {

    public PlantCell() {
        try {
            setImage(ImageIO.read(new File("res/images/map/plantCell.png")));
        }
        catch (IOException e) { }
        setDestroyable(false);
        setBarrier(false);
    }
}
