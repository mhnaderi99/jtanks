import java.awt.image.BufferedImage;

public abstract class Prize {

    private BufferedImage image;
    private int XPosition;
    private int YPosition;

    /**
     * the work of the prize
     * @param vehicle the vehicle to be applied to
     */
    public abstract void work(CombatVehicle vehicle);

    /**
     * to set the image
     * @param image the image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * to get the y position
     * @return the y position
     */
    public int getYPosition() {
        return YPosition;
    }

    /**
     * to get the x position
     * @return the x position
     */
    public int getXPosition() {
        return XPosition;
    }

    /**
     * to get the image
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * to set the x position
     * @param XPosition the x position
     */
    public void setXPosition(int XPosition) {
        this.XPosition = XPosition;
    }

    /**
     * to set the y position
     * @param YPosition the y position
     */
    public void setYPosition(int YPosition) {
        this.YPosition = YPosition;
    }
}
