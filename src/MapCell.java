import java.awt.image.BufferedImage;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 *
 */

public abstract class MapCell {

    private BufferedImage image;
    private boolean isDestroyable;
    private boolean isBarrier;
    private boolean isBarrierForBullet;
    private boolean isTransparent;

    /**
     * to get the image of the map cell
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * to set the image of the map cell
     * @param image the image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * to set whether the cell is destroyable or not
     * @param destroyable true or false
     */
    public void setDestroyable(boolean destroyable) {
        isDestroyable = destroyable;
    }

    /**
     * to check whether the cell is destroyable or not
     * @return true or false
     */
    public boolean isDestroyable() {
        return isDestroyable;
    }

    /**
     * to set whether the map cell is barrier for tank or not
     * @param barrier true or false
     */
    public void setBarrier(boolean barrier) {
        isBarrier = barrier;
    }

    /**
     * to check whether the map cell is barrier for tank or not
     * @return true or false
     */
    public boolean isBarrier() {
        return isBarrier;
    }

    /**
     * to check whether the map cell is barrier for bullet or not
     * @return true or false
     */
    public boolean isBarrierForBullet() {
        return isBarrierForBullet;
    }

    /**
     * to set whether the map cell is barrier for bullet or not
     * @param barrierForBullet true or false
     */
    public void setBarrierForBullet(boolean barrierForBullet) {
        isBarrierForBullet = barrierForBullet;
    }

    /**
     * to check whether the map cell is transient or not
     * @return true or false
     */
    public boolean isTransparent() {
        return isTransparent;
    }

    /**
     * to set whether the map cell is transient or not
     * @param transparent true or false
     */
    public void setTransparent(boolean transparent) {
        isTransparent = transparent;
    }

    /**
     * to destroy a map cell
     * @param bullet the bullet
     */
    public abstract void destroy(Bullet bullet);
}
