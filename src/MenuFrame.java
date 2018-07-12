import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuFrame extends JFrame{

    private BufferStrategy bufferStrategy;
    private Graphics2D graphics2D;
    private BufferedImage background;
    private BufferedImage easy;
    private BufferedImage normal;
    private BufferedImage hard;
    private BufferedImage mh;


    private MouseHandler handler;


    public MenuFrame(String title) {
        super(title);
        setSize(970 , 606);
        getContentPane().setBackground(Color.BLACK);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        handler = new MouseHandler();
        addMouseMotionListener(handler);
        addMouseListener(handler);
        initImages();
    }

    public void initBufferstrategy() {
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }

    public void draw() {
        do {
            do {
                graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    render(graphics2D);
                } finally {
                    graphics2D.dispose();
                }
            } while (bufferStrategy.contentsRestored());

            bufferStrategy.show();
            Toolkit.getDefaultToolkit().sync();
        } while (bufferStrategy.contentsLost());
        bufferStrategy.show();
    }

    private void render(Graphics2D g2d) {
        int extraW = GameConstants.getScreenWidth() - getContentPane().getSize().width;
        int extraH = GameConstants.getScreenHeight() - getContentPane().getSize().height;
        g2d.drawImage(background, 0,0, this);
        g2d.drawImage(easy, 100, 250, this);
        g2d.drawImage(normal, 90, 310, this);
        g2d.drawImage(hard, 100, 370, this);
        g2d.drawImage(mh, 300, 510, this);

    }

    private void initImages() {
        try {
            background = ImageIO.read(new File("res/images/menu/startup.png"));
            drawButtons("1", "1", "1");
            mh = ImageIO.read(new File("res/images/menu/mh.jpg"));

        }
        catch (IOException e) { }
    }

    public void start() {
        setVisible(true);
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            String e1 = "1", n = "1", h = "1";
            if (isInBoundsOfEasy(x, y)) {
                e1 = "2";
            }
            else if (isInBoundsOfNormal(x, y)) {
                n = "2";
            }
            else if (isInBoundsOfHard(x, y)) {
                h = "2";
            }
            drawButtons(e1,n,h);
            draw();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX(), y = e.getY();
            int difficulty = 1;
            String e1 = "1", n = "1", h = "1";
            if (isInBoundsOfEasy(x, y)) {
                e1 = "3";
            }
            else if (isInBoundsOfNormal(x, y)) {
                n = "3";
                difficulty = 2;
            }
            else if (isInBoundsOfHard(x, y)) {
                h = "3";
                difficulty = 3;
            }
            drawButtons(e1, n, h);
            draw();
            startGame(difficulty);
        }
    }

    private boolean isInBoundsOfEasy(int x, int y) {
        if (x >= 100 && y >= 250 && x <= 210 && y <= 295) {
            return true;
        }
        return false;
    }
    private boolean isInBoundsOfNormal(int x, int y) {
        if (x >= 90 && y >= 310 && x <= 290 && y <= 355) {
            return true;
        }
        return false;
    }

    private boolean isInBoundsOfHard(int x, int y) {
        if (x >= 100 && y >= 370 && x <= 220 && y <= 415) {
            return true;
        }
        return false;
    }

    private void drawButtons(String e, String n, String h) {
        try {
            easy = ImageIO.read(new File("res/images/menu/easy" + e + ".png"));
            normal = ImageIO.read(new File("res/images/menu/normal" + n + ".png"));
            hard = ImageIO.read(new File("res/images/menu/hard" + h + ".png"));
        }
        catch (IOException e2) { }
    }

    private void startGame(int difficulty) {
        ThreadPool.init();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame frame = new GameFrame("Test");
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                dispose();
                frame.setVisible(true);
                frame.initBufferStrategy();

                GameLoop game = new GameLoop(frame);
                //game.init();
                ThreadPool.execute(game);
            }
        });
    }
}