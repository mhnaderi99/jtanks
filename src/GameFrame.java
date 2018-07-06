import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class GameFrame extends JFrame{

    private BufferStrategy bufferStrategy;

    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GameConstants.getScreenWidth(), GameConstants.getScreenHeight());
    }

    /**
     * This must be called once after the JFrame is shown:
     *    frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }

    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(Tank tank) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, tank);
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
    private void doRendering(Graphics2D g2d, Tank tank) {
        // Draw background
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, GameConstants.getScreenWidth(), GameConstants.getScreenHeight());
        g2d.drawImage(tank.getBody(), tank.getXPosition(), tank.getYPosition(), null);
        AffineTransform transform = new AffineTransform();
        transform.setToTranslation(tank.getXPosition() + 17,tank.getYPosition() + tank.getBody().getHeight()/2 - tank.getActiveGun().getImage().getHeight()/2);

        double angle = -tank.getGunAngle();


        transform.rotate(angle, tank.getActiveGun().getImage().getWidth()/2 - 17,tank.getActiveGun().getImage().getHeight()/2);
        g2d.drawImage(tank.getActiveGun().getImage(), transform, this);

        for(Gun gun : tank.getGuns()) {
            if (gun.getMovingBullets().size() != 0) {
                for (Bullet bullet : gun.getMovingBullets()) {
                    if (bullet.isShoot && bullet.isOnTheWay) {
                        AffineTransform bulletTransform = new AffineTransform();
                        bulletTransform.setToTranslation(bullet.getXPosition(), bullet.getYPosition());
                        bulletTransform.rotate(bullet.getAngle());
                        g2d.drawImage(bullet.image, bulletTransform, null);
                    }
                }
            }
        }
    }

}
