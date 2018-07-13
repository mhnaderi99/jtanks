import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client implements Runnable{

    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try (Socket server = new Socket(host, port)) {
            System.out.println("Connected to server.");

            ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(server.getInputStream());
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
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("done.");
    }

}
