import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Random;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 *
 */
public class GameFrame extends JFrame {

    private BufferStrategy bufferStrategy;
    private Graphics2D graphics;
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

    @Override
    public Graphics2D getGraphics() {
        return graphics;
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
                graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
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
                    renderCell(i,j);
                }
            }

            for (CombatVehicle vehicle : allVehicles) {
                g2d.drawImage(vehicle.getBody(), vehicle.getXPosition() - ox, vehicle.getYPosition() - oy, this);
                AffineTransform transform = new AffineTransform();
                transform.setToTranslation(vehicle.getXPosition() - ox + 17, vehicle.getYPosition() - oy + vehicle.getBody().getHeight() / 2 - vehicle.getActiveGun().getImage().getHeight() / 2);

                double angle = -vehicle.getGunAngle();

                transform.rotate(angle, vehicle.getActiveGun().getImage().getWidth() / 2 - 17, vehicle.getActiveGun().getImage().getHeight() / 2);
                g2d.drawImage(vehicle.getActiveGun().getImage(), transform, this);
                //renderDetails(g2d);
            }
            renderDetails();
            return;
        }

        HashSet<Point> refreshBackground = new HashSet<Point>();
        HashSet<Point> refreshForeGround = new HashSet<Point>();
        for (CombatVehicle vehicle : allVehicles) {
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
                try {
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
                } catch (ConcurrentModificationException e) {
                    continue;
                }
            }

            for (Point p : refreshBackground) {
                int i = p.x, j = p.y;
               renderCell(i,j);
            }

            for (Point p : refreshForeGround) {
                int i = p.x, j = p.y;
                g2d.drawImage(GameConstants.getCellByCode(4).getImage(), GameConstants.getCellWidth() * i - ox, GameConstants.getCellHeight() * j - oy, this);
                //renderDetails(g2d);
            }
        }

        //Draw tanks

        for (CombatVehicle vehicle : allVehicles) {
            g2d.drawImage(vehicle.getBody(), vehicle.getXPosition() - ox, vehicle.getYPosition() - oy, this);
            AffineTransform transform = new AffineTransform();
            transform.setToTranslation(vehicle.getXPosition() - ox + 17, vehicle.getYPosition() - oy + vehicle.getBody().getHeight() / 2 - vehicle.getActiveGun().getImage().getHeight() / 2);

            double angle = -vehicle.getGunAngle();

            transform.rotate(angle, vehicle.getActiveGun().getImage().getWidth() / 2 - 17, vehicle.getActiveGun().getImage().getHeight() / 2);
            g2d.drawImage(vehicle.getActiveGun().getImage(), transform, this);
            //renderDetails(g2d);

            //Draw bullets
            for (Gun gun : vehicle.getGuns()) {
                if (gun.getMovingBullets().size() != 0) {
                    try {
                        for (Bullet bullet : gun.getMovingBullets()) {
                            if (bullet.isShoot() && !bullet.isOnTheWay()) {
                                continue;
                            }
                            if (bullet.isShoot() && bullet.isOnTheWay()) {
                                AffineTransform bulletTransform = new AffineTransform();
                                bulletTransform.setToTranslation(bullet.getX() - ox, bullet.getY() - oy);
                                bulletTransform.rotate(bullet.getAngle());
                                g2d.drawImage(bullet.image, bulletTransform, this);
                                //renderDetails(g2d);
                            }
                        }
                    } catch (ConcurrentModificationException e) {
                        continue;
                    }
                }
            }
        }

        //Draw Foreground Layer
        for (Point p : refreshForeGround) {
            int i = p.x, j = p.y;

            g2d.drawImage(map.getMap()[i][j].getImage(), GameConstants.getCellWidth() * i - ox, GameConstants.getCellHeight() * j - oy, this);
            //
        }

        int rand = new Random().nextInt(Integer.MAX_VALUE);
        renderDetails();

        //Draw details


    }

    public void renderDetails() {
        BufferedImage cannon, machineGun;
        boolean isCannonActive;
        if (GameLoop.getState().getTank().getGuns().get(0) == GameLoop.getState().getTank().getActiveGun()) {
            isCannonActive = true;
        }
        else {
            isCannonActive = false;
        }

        int extraW = GameConstants.getScreenWidth() - getContentPane().getSize().width;
        int extraH = GameConstants.getScreenHeight() - getContentPane().getSize().height;
        int cannonNumber = GameLoop.getState().getTank().getGuns().get(0).getBullets().size();
        int machineGunNumber = GameLoop.getState().getTank().getGuns().get(1).getBullets().size();
        graphics.setFont(new Font("Gadugi", Font.PLAIN, 22));
        if (isCannonActive) {
            cannon = GameConstants.getCannonNumber(false);
            machineGun = GameConstants.getMachineGunNumber(true);
            graphics.drawImage(cannon, GameConstants.getBorderMargin() + extraW, GameConstants.getBorderMargin() + extraH, null);
            graphics.setColor(Color.CYAN);
            if (cannonNumber == 0) {
                graphics.setColor(Color.RED);
            }
            graphics.drawString("" + cannonNumber, GameConstants.getBorderMargin() + extraW, GameConstants.getBorderMargin() + extraH + cannon.getHeight());
            graphics.drawImage(machineGun, GameConstants.getBorderMargin() + extraW, 2 * GameConstants.getBorderMargin() + cannon.getHeight() + extraH, null);
            graphics.setColor(Color.GRAY);
            graphics.drawString("" + machineGunNumber, GameConstants.getBorderMargin() + extraW, 2 * GameConstants.getBorderMargin() + extraH + machineGun.getHeight() + machineGun.getHeight());
        }
        else {
            cannon = GameConstants.getCannonNumber(true);
            machineGun = GameConstants.getMachineGunNumber(false);
            graphics.drawImage(cannon, GameConstants.getBorderMargin() + extraW, GameConstants.getBorderMargin() + extraH, null);
            graphics.setColor(Color.GRAY);
            graphics.drawString("" + cannonNumber, GameConstants.getBorderMargin() + extraW, GameConstants.getBorderMargin() + extraH + cannon.getHeight());
            graphics.drawImage(machineGun, GameConstants.getBorderMargin() + extraW, 2 * GameConstants.getBorderMargin() + cannon.getHeight() + extraH, null);
            graphics.setColor(Color.CYAN);
            if (machineGunNumber == 0) {
                graphics.setColor(Color.RED);
            }
            graphics.drawString("" + machineGunNumber, GameConstants.getBorderMargin() + extraW, 2 * GameConstants.getBorderMargin() + extraH + machineGun.getHeight() + machineGun.getHeight());
        }


        int h = GameLoop.getState().getTank().getHealth();
        int dh = Tank.getDefaultHealth();
        int healthPercentage = (int) ((double) h / (double) dh * 100);
        int num = (int) Math.round((double) healthPercentage / ((double) 100 / (double) GameConstants.getHealthCells()));
        int mid = GameConstants.getHealthCells() / 2;
        for (int i = 0; i < GameConstants.getHealthCells(); i++) {
            BufferedImage image;
            if (i < num) {
                image = GameConstants.getHealth();
            } else {
                image = GameConstants.getEmpty();
            }

            int x, y;
            x = GameConstants.getScreenWidth() / 2 - (mid - i) * (GameConstants.getBorderMargin() + image.getWidth()) - image.getWidth() / 2;
            y = GameConstants.getBorderMargin() + extraH;

            graphics.drawImage(image, x, y, this);
        }

    }

    public void renderCell(int x, int y) {
        if (x < 0 || y < 0 || x >= Map.getWidth() || y >= Map.getHeight()) {
            return;
        }
        int ox = GameLoop.getState().getTopLeftPoint().x;
        int oy = GameLoop.getState().getTopLeftPoint().y;

        if (GameLoop.getState().getMap().getMap()[x][y].isTransparent()) {
            graphics.drawImage(GameConstants.getCellByCode(4).getImage(), x * GameConstants.getCellWidth() - ox, y * GameConstants.getCellHeight() - oy, this);
            //renderDetails(g2d);
        }
        graphics.drawImage(GameLoop.getState().getMap().getMap()[x][y].getImage(), x * GameConstants.getCellWidth() - ox, y * GameConstants.getCellHeight() - oy, this);
        //renderDetails(g2d);
    }

}
