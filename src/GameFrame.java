import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

public class GameFrame extends JFrame {

    private BufferStrategy bufferStrategy;
    private static ArrayList<Point> updated;
    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GameConstants.getScreenWidth(), GameConstants.getScreenHeight());
    }

    /**
     * This must be called once after the JFrame is shown:
     * frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }


    public int getXOfFrame() {
        return getLocationOnScreen().x;
    }

    public int getYOfFrame() {
        return getLocationOnScreen().y;
    }

    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state, boolean isInitial) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state, isInitial);
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, GameState state, boolean isInitial) {
        // Draw background
        Map map = state.getMap();
        ArrayList<CombatVehicle> allVehicles = new ArrayList<CombatVehicle>(state.getEnemies());
        Tank tank = state.getTank();
        allVehicles.add(tank);

        int ox = state.getTopLeftPoint().x;
        int oy = state.getTopLeftPoint().y;
        int x1 = ox / GameConstants.getCellWidth();
        int y1 = oy / GameConstants.getCellHeight();
        int x2 = Math.min(((ox + GameConstants.getScreenWidth()) / GameConstants.getCellWidth() + 1), state.getMap().getWidth());
        int y2 = Math.min(((oy + GameConstants.getScreenHeight()) / GameConstants.getCellHeight() + 1), state.getMap().getHeight());


        if (isInitial) {

            for (int i = x1; i <= Math.min(x2, map.getWidth() - 1); i++) {
                for (int j = y1; j <= Math.min(y2, map.getHeight() - 1); j++) {
                    if (state.getMap().getMap()[i][j].isTransparent()) {
                        g2d.drawImage(GameConstants.getCellByCode(4).getImage(), i*GameConstants.getCellWidth() - ox, j*GameConstants.getCellHeight() - oy, this);
                    }
                    g2d.drawImage(state.getMap().getMap()[i][j].getImage(), i*GameConstants.getCellWidth() - ox, j*GameConstants.getCellHeight() - oy, this);
                }
            }

            for(CombatVehicle vehicle: allVehicles) {
                g2d.drawImage(vehicle.getBody(), vehicle.getXPosition() - ox, vehicle.getYPosition() - oy, this);
                AffineTransform transform = new AffineTransform();
                transform.setToTranslation(vehicle.getXPosition() - ox + 17, vehicle.getYPosition() - oy + vehicle.getBody().getHeight() / 2 - vehicle.getActiveGun().getImage().getHeight() / 2);

                double angle = -vehicle.getGunAngle();

                transform.rotate(angle, vehicle.getActiveGun().getImage().getWidth() / 2 - 17, vehicle.getActiveGun().getImage().getHeight() / 2);
                g2d.drawImage(vehicle.getActiveGun().getImage(), transform, this);
            }
            return;
        }

        HashSet<Point> refreshBackground = new HashSet<Point>();
        HashSet<Point> refreshForeGround = new HashSet<Point>();
        for (CombatVehicle vehicle: allVehicles) {
            int x = vehicle.getXPosition(), y = vehicle.getYPosition();
            int w = x / GameConstants.getCellWidth(), h = y / GameConstants.getCellHeight();

            for (int i = Math.max(x1, w - 1); i < Math.min(w + 2, x2); i++) {
                for (int j = Math.max(y1, h - 1); j < Math.min(h + 2, y2); j++) {
                    if (!map.getMap()[i][j].isTransparent()) {
                        refreshBackground.add(new Point(i, j));
                    } else {
                        refreshForeGround.add(new Point(i, j));
                    }
                }
            }

            for (Gun gun : vehicle.getGuns()) {
                for (Bullet bullet : gun.getMovingBullets()) {
                    int xx = (int) bullet.getX();
                    int yy = (int) bullet.getY();
                    int ww = xx / GameConstants.getCellWidth();
                    int hh = yy / GameConstants.getCellHeight();
                    for (int i = Math.max(x1, ww - 1); i < Math.min(ww + 3, x2); i++) {
                        for (int j = Math.max(y1, hh - 1); j < Math.min(hh + 3, y2); j++) {
                            if (!map.getMap()[i][j].isTransparent()) {
                                refreshBackground.add(new Point(i, j));
                            } else {
                                refreshForeGround.add(new Point(i, j));
                            }
                        }
                    }
                }
            }

            for (Point p : refreshBackground) {
                int i = p.x, j = p.y;
                if (map.getMap()[i][j].isTransparent()) {
                    g2d.drawImage(GameConstants.getCellByCode(4).getImage(), GameConstants.getCellWidth() * i - ox, GameConstants.getCellHeight() * j - oy, this);
                }
                g2d.drawImage(map.getMap()[i][j].getImage(), GameConstants.getCellWidth() * i - ox, GameConstants.getCellHeight() * j - oy, this);
            }

            for (Point p : refreshForeGround) {
                int i = p.x, j = p.y;
                g2d.drawImage(GameConstants.getCellByCode(4).getImage(), GameConstants.getCellWidth() * i - ox, GameConstants.getCellHeight() * j - oy, this);
            }
        }

        //Draw tanks

        for (CombatVehicle vehicle: allVehicles) {
            g2d.drawImage(vehicle.getBody(), vehicle.getXPosition() - ox, vehicle.getYPosition() - oy, this);
            AffineTransform transform = new AffineTransform();
            transform.setToTranslation(vehicle.getXPosition() - ox + 17, vehicle.getYPosition() - oy + vehicle.getBody().getHeight() / 2 - vehicle.getActiveGun().getImage().getHeight() / 2);

            double angle = -vehicle.getGunAngle();

            transform.rotate(angle, vehicle.getActiveGun().getImage().getWidth() / 2 - 17, vehicle.getActiveGun().getImage().getHeight() / 2);
            g2d.drawImage(vehicle.getActiveGun().getImage(), transform, this);


            //Draw bullets
            for (Gun gun : vehicle.getGuns()) {
                if (gun.getMovingBullets().size() != 0) {
                    for (Bullet bullet : gun.getMovingBullets()) {
                        if (bullet.isShoot() && bullet.isOnTheWay()) {
                            AffineTransform bulletTransform = new AffineTransform();
                            bulletTransform.setToTranslation(bullet.getX() - ox, bullet.getY() - oy);
                            bulletTransform.rotate(bullet.getAngle());
                            g2d.drawImage(bullet.image, bulletTransform, this);
                        }
                    }
                }
            }
        }

        //Draw Foreground Layer
        for (Point p : refreshForeGround) {
            int i = p.x, j = p.y;

            g2d.drawImage(map.getMap()[i][j].getImage(), GameConstants.getCellWidth()*i - ox, GameConstants.getCellHeight()*j - oy, this);
        }

    }

}
