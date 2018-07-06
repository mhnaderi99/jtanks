import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Gun {

    private BufferedImage image;
    private Bullet type;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> movingBullets;

    public Gun(BufferedImage image, int numberOfBullets, Bullet type) {
        this.image = image;
        this.type = type;
        bullets = new ArrayList<Bullet>();
        movingBullets = new ArrayList<>();
        reload(numberOfBullets);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isBulletsEmpty() {
        return bullets.isEmpty();
    }

    public void shoot(double theta) {
        if (! isBulletsEmpty()) {
            Bullet bullet = bullets.get(0);
            bullet.isShoot = true;
            bullet.isOnTheWay = true;
            bullet.XSpeed = (int) (bullet.speed * Math.cos(theta));
            bullet.YSpeed = -(int) (bullet.speed * Math.sin(theta));
            movingBullets.add(bullet);
            bullets.remove(0);
            }
    }
    public void update(){
        ArrayList<Bullet> temp = new ArrayList<>();
        for(Bullet bullet : movingBullets){
            if(bullet.isShoot && bullet.isOnTheWay) {
                bullet.update();
            }
            if(bullet.isShoot && ! bullet.isOnTheWay){
                temp.add(bullet);
            }
        }
        for (Bullet bullet :temp){
            movingBullets.remove(bullet);
        }

    }
    public void reload(int number) {
        if (type instanceof CannonBullet) {
            for (int i = 0; i < number; i++) {
                bullets.add(new CannonBullet());
            }
        }
        if (type instanceof MachineGunBullet) {
            for (int i = 0; i < number; i++) {
                bullets.add(new MachineGunBullet());
            }
        }
    }

    public ArrayList<Bullet> getMovingBullets() {
        return movingBullets;
    }
}
