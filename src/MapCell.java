import java.awt.image.BufferedImage;

public class MapCell {

    private BufferedImage image;
    private boolean isDestroyable;
    private boolean isBarrier;


    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setDestroyable(boolean destroyable) {
        isDestroyable = destroyable;
    }

    public boolean isDestroyable() {
        return isDestroyable;
    }

    public void setBarrier(boolean barrier) {
        isBarrier = barrier;
    }

    public boolean isBarrier() {
        return isBarrier;
    }
}
