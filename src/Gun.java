import java.awt.image.BufferedImage;

public class Gun {

    private int damage;
    private BufferedImage image;
    private int numberOfBullets;

    public Gun(int damage, BufferedImage image, int numberOfBullets) {
        this.damage = damage;
        this.image = image;
        this.numberOfBullets = numberOfBullets;
    }

    public BufferedImage getImage() {
        return image;
    }
}
