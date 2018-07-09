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
        Tank tank = state.getTank();

        int ox = state.getTopLeftPoint().x;
        int oy = state.getTopLeftPoint().y;
        int x1 = ox / GameConstants.getCellWidth();
        int y1 = oy / GameConstants.getCellHeight();
        int x2 = Math.min(((ox + GameConstants.getScreenWidth()) / GameConstants.getCellWidth() + 1), state.getMap().getWidth() - 1);
        int y2 = Math.min(((oy + GameConstants.getScreenHeight()) / GameConstants.getCellHeight() + 1), state.getMap().getHeight());

        if (isInitial) {

            for (int i = x1; i <= x2; i++) {
                for (int j = y1; j <= y2; j++) {
                    if (state.getMap().getMap()[i][j] instanceof TeazelCell) {
                        g2d.drawImage(GameConstants.getCellByCode(4).getImage(), i*GameConstants.getCellWidth() - ox, j*GameConstants.getCellHeight() - oy, this);
                    }
                    g2d.drawImage(state.getMap().getMap()[i][j].getImage(), i*GameConstants.getCellWidth() - ox, j*GameConstants.getCellHeight() - oy, this);
                }
            }

            g2d.drawImage(tank.getBody(), tank.getXPosition() - ox, tank.getYPosition() - oy, this);
            AffineTransform transform = new AffineTransform();
            transform.setToTranslation(tank.getXPosition() - ox+ 17, tank.getYPosition() - oy + tank.getBody().getHeight() / 2 - tank.getActiveGun().getImage().getHeight() / 2);

            double angle = -tank.getGunAngle();

            transform.rotate(angle, tank.getActiveGun().getImage().getWidth() / 2 - 17, tank.getActiveGun().getImage().getHeight() / 2);
            g2d.drawImage(tank.getActiveGun().getImage(), transform, this);
            return;
        }

        int x = tank.getXPosition(), y = tank.getYPosition();
        int w = x / GameConstants.getCellWidth(), h = y / GameConstants.getCellHeight();

        HashSet<Point> refreshBackground = new HashSet<Point>();

        for (int i = Math.max(x1, w - 1); i < Math.min(w + 3, x2); i++) {
            for (int j = Math.max(y1, h - 1); j < Math.min(h + 3, y2); j++) {
                refreshBackground.add(new Point(i,j));
            }
        }

        for (Gun gun: tank.getGuns()) {
            for (Bullet bullet: gun.getMovingBullets()) {
                int xx = (int) bullet.getX();
                int yy = (int) bullet.getY();
                int ww = xx / GameConstants.getCellWidth();
                int hh = yy / GameConstants.getCellHeight();
                for (int i = Math.max(x1, ww - 1); i < Math.min(ww + 2, x2); i++) {
                    for (int j = Math.max(y1, hh - 1); j < Math.min(hh + 2, y2); j++) {
                        refreshBackground.add(new Point(i,j));
                    }
                }
            }
        }

        for (Point p : refreshBackground) {
            int i = p.x, j = p.y;
            if (map.getMap()[i][j] instanceof TeazelCell) {
                g2d.drawImage(GameConstants.getCellByCode(4).getImage(), GameConstants.getCellWidth()*i - ox, GameConstants.getCellHeight()*j - oy, this);
            }
            g2d.drawImage(map.getMap()[i][j].getImage(), GameConstants.getCellWidth()*i - ox, GameConstants.getCellHeight()*j - oy, this);
        }


        //Draw tank

        g2d.drawImage(tank.getBody(), tank.getXPosition() - ox, tank.getYPosition() - oy, this);
        AffineTransform transform = new AffineTransform();
        transform.setToTranslation(tank.getXPosition() - ox+ 17, tank.getYPosition() - oy + tank.getBody().getHeight() / 2 - tank.getActiveGun().getImage().getHeight() / 2);

        double angle = -tank.getGunAngle();

        transform.rotate(angle, tank.getActiveGun().getImage().getWidth() / 2 - 17, tank.getActiveGun().getImage().getHeight() / 2);
        g2d.drawImage(tank.getActiveGun().getImage(), transform, this);


        //Draw bullets
        for (Gun gun : tank.getGuns()) {
            if (gun.getMovingBullets().size() != 0) {
                for (Bullet bullet : gun.getMovingBullets()) {
                    if (bullet.isShoot && bullet.isOnTheWay) {
                        AffineTransform bulletTransform = new AffineTransform();
                        bulletTransform.setToTranslation(bullet.getX() - ox, bullet.getY() - oy);
                        bulletTransform.rotate(bullet.getAngle());
                        g2d.drawImage(bullet.image, bulletTransform, this);
                    }
                }
            }
        }

    }

}
