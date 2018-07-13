import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    private Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            //OutputStream out = client.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            while (! GameLoop.isGameOver()) {

                out.writeObject(GameLoop.getState().getTank());

                Tank tank = (Tank) in.readObject();
                tank.setBody(GameConstants.getTankBody());
                ArrayList<Gun> guns = new ArrayList<>();
                if (tank.getActiveGunIndex() == 0) {
                    tank.getActiveGun().setImage(GameConstants.getCannonImage());
                }
                if (tank.getActiveGunIndex() == 1) {
                    tank.getActiveGun().setImage(GameConstants.getMachineGunImage());
                }
                for (Gun gun: tank.getGuns()) {
                    for (Bullet bullet: gun.getBullets()) {
                        if (bullet instanceof CannonBullet) {
                            bullet.setImage(GameConstants.getCannonBullet().getImage());
                        }
                        if (bullet instanceof MachineGunBullet) {
                            bullet.setImage(GameConstants.getMachineGunBullet().getImage());
                        }
                    }
                    for (Bullet bullet: gun.getMovingBullets()) {
                        if (bullet instanceof CannonBullet) {
                            bullet.setImage(GameConstants.getCannonBullet().getImage());
                        }
                        if (bullet instanceof MachineGunBullet) {
                            bullet.setImage(GameConstants.getMachineGunBullet().getImage());
                        }
                    }
                }
                GameLoop.getState().setTank2(tank);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }


    public Socket getClient() {
        return client;
    }
}
