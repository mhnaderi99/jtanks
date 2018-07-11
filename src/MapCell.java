import java.awt.image.BufferedImage;

public abstract class MapCell {

    private BufferedImage image;
    private boolean isDestroyable;
    private boolean isBarrier;
    private boolean isBarrierForBullet;
    private boolean isTransparent;


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

    public boolean isBarrierForBullet() {
        return isBarrierForBullet;
    }

    public void setBarrierForBullet(boolean barrierForBullet) {
        isBarrierForBullet = barrierForBullet;
    }

    public boolean isTransparent() {
        return isTransparent;
    }

    public void setTransparent(boolean transparent) {
        isTransparent = transparent;
    }

    public abstract void destroy(Bullet bullet);
}
