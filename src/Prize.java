import java.awt.image.BufferedImage;

public abstract class Prize {

    private BufferedImage image;
    private int XPosition;
    private int YPosition;

    public abstract void work(CombatVehicle vehicle);

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getYPosition() {
        return YPosition;
    }

    public int getXPosition() {
        return XPosition;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setXPosition(int XPosition) {
        this.XPosition = XPosition;
    }

    public void setYPosition(int YPosition) {
        this.YPosition = YPosition;
    }
}
