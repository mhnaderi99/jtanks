import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 *
 */

public class MenuFrame extends JFrame{

    private BufferStrategy bufferStrategy;
    private Graphics2D graphics2D;
    private BufferedImage background;
    private BufferedImage easy;
    private BufferedImage normal;
    private BufferedImage hard;
    private BufferedImage single;
    private BufferedImage multi;
    private BufferedImage create;
    private BufferedImage join;
    private BufferedImage mh;
    private boolean flag = true;
    private int difficulty;
    private boolean isMultiplayer = false;
    private boolean isJoin = false;


    private MouseHandler handler;

    /**
     * the constructor of menu frame
     * @param title title of menu frame
     */
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

    /**
     * the buffer strategy
     */
    public void initBufferstrategy() {
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }

    /**
     * draw menu frame
     */
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

    /**
     * render the menu frame
     * @param g2d
     */
    private void render(Graphics2D g2d) {
        int extraW = GameConstants.getScreenWidth() - getContentPane().getSize().width;
        int extraH = GameConstants.getScreenHeight() - getContentPane().getSize().height;
        g2d.drawImage(background, 0,0, this);
        if (flag) {
            g2d.drawImage(easy, 100, 250, this);
            g2d.drawImage(normal, 90, 310, this);
            g2d.drawImage(hard, 100, 370, this);
        }
        else if(! isMultiplayer){
            g2d.drawImage(single, 40, 300, this);
            g2d.drawImage(multi, 40, 360, this);
        }
        else {
            //232,40
            //184,40
            g2d.drawImage(create, 40,300, this);
            g2d.drawImage(join, 40,350, this);
        }
        g2d.drawImage(mh, 300, 510, this);

    }

    /**
     * load images of the menu
     */
    private void initImages() {
        try {
            background = ImageIO.read(new File("res/images/menu/startup.png"));
            drawDiffButtons("1", "1", "1");
            drawSingleMulti("1", "1");
            drawCreateJoin("1", "1");
            mh = ImageIO.read(new File("res/images/menu/mh.jpg"));

        }
        catch (IOException e) { }
    }

    public void start() {
        setVisible(true);
    }

    /**
     * mouse handler of the menu
     */
    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            if (flag) {
                String e1 = "1", n = "1", h = "1";
                if (isInBoundsOfEasy(x, y)) {
                    e1 = "2";
                } else if (isInBoundsOfNormal(x, y)) {
                    n = "2";
                } else if (isInBoundsOfHard(x, y)) {
                    h = "2";
                }
                drawDiffButtons(e1, n, h);
            }
            else if (! isMultiplayer){
                String s = "1", m = "1";
                if (isInBoundsOfSingle(x, y)) {
                    s = "2";
                }
                else if (isInBoundsOfMulti(x,y)) {
                    m = "2";
                }
                drawSingleMulti(s,m);
            }
            else {
                String c = "1", j = "1";
                if (isInBoundsOfCreate(x, y)) {
                    c = "2";
                }
                else if (isInBoundsOfJoin(x, y)) {
                    j = "2";
                }
                drawCreateJoin(c,j);
            }
            draw();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX(), y = e.getY();
            if (flag) {
                difficulty = 0;
                boolean f = true;
                String e1 = "1", n = "1", h = "1";
                if (isInBoundsOfEasy(x, y)) {
                    e1 = "3";
                }
                else if (isInBoundsOfNormal(x, y)) {
                    n = "3";
                    difficulty = 1;
                }
                else if (isInBoundsOfHard(x, y)) {
                    h = "3";
                    difficulty = 2;
                }
                else {
                    f = false;
                }
                drawDiffButtons(e1, n, h);
                draw();
                if (f) {
                    flag = false;
                    delay();
                    draw();
                }
            }
            else if (!isMultiplayer){
                String s = "1", m = "1";
                boolean f = true;
                if (isInBoundsOfSingle(x,y)) {
                    s = "3";
                    isMultiplayer = false;
                }
                else if (isInBoundsOfMulti(x,y)) {
                    m = "3";
                    isMultiplayer = true;
                }
                else {
                    f = false;
                }
                drawSingleMulti(s,m);
                draw();
                delay();
                if (f && ! isMultiplayer) {
                    startGame(0);
                }
            }
            else {
                boolean f = true;
                String c = "1", j = "1";
                if (isInBoundsOfCreate(x, y)) {
                    c = "3";
                    isJoin = false;
                }
                else if (isInBoundsOfJoin(x, y)) {
                    j = "3";
                    isJoin = true;
                }
                else {
                    f = false;
                }
                drawCreateJoin(c, j);
                draw();
                delay();
                if (f) {
                    if (isJoin) {
                        startGame(2);
                    }
                    else {
                        startGame(1);
                    }
                }
            }

        }
    }

    private void delay() {
        try {
            Thread.sleep(150);
        }
        catch (InterruptedException e) {}
    }

    /**
     * check if the mouse is in the bounds of easy icon
     * @param x x position of mouse
     * @param y y position of mouse
     * @return true or false
     */
    private boolean isInBoundsOfEasy(int x, int y) {
        if (x >= 100 && y >= 250 && x <= 210 && y <= 295 && flag && !isMultiplayer) {
            return true;
        }
        return false;
    }

    /**
     * check if the mouse is in the bounds of normal icon
     * @param x x position of mouse
     * @param y y position of mouse
     * @return true or false
     */
    private boolean isInBoundsOfNormal(int x, int y) {
        if (x >= 90 && y >= 310 && x <= 290 && y <= 355 && flag && !isMultiplayer) {
            return true;
        }
        return false;
    }

    /**
     * check if the mouse is in the bounds of hard icon
     * @param x x position of mouse
     * @param y y position of mouse
     * @return true or false
     */
    private boolean isInBoundsOfHard(int x, int y) {
        if (x >= 100 && y >= 370 && x <= 220 && y <= 415 && flag && !isMultiplayer) {
            return true;
        }
        return false;
    }

    /**
     * check if the mouse is in the bounds of single icon
     * @param x x position of mouse
     * @param y y position of mouse
     * @return true or false
     */
    private boolean isInBoundsOfSingle(int x, int y) {
        if (x >= 40 && x <= 304 && y >= 300 && y <= 336 && !flag && !isMultiplayer) {
            return true;
        }
        return false;
    }

    /**
     * check if the mouse is in the bounds of multiPlayer icon
     * @param x x position of mouse
     * @param y y position of mouse
     * @return true or false
     */
    private boolean isInBoundsOfMulti(int x, int y) {
        if (x >= 40 && x <= 280 && y >= 360 && y <= 396 && ! flag && !isMultiplayer) {
            return true;
        }
        return false;
    }

    /**
     * check if the mouse is in the bounds of create icon
     * @param x x position of mouse
     * @param y y position of mouse
     * @return true or false
     */
    private boolean isInBoundsOfCreate(int x, int y) {
        if (x >= 40 && x <=272 && y >= 300 && y <= 340 && !flag && isMultiplayer) {
            return true;
        }
        return false;
    }

    /**
     * check if the mouse is in the bounds of join icon
     * @param x x position of mouse
     * @param y y position of mouse
     * @return true or false
     */
    private boolean isInBoundsOfJoin(int x, int y) {
        if (x >= 40 && x <= 224 && y >= 350 && y <= 390 && !flag && isMultiplayer) {
            return true;
        }
        return false;
    }

    /**
     * draw easy,normal, hard icons
     * @param e file path of easy icon file
     * @param n file path of normal icon file
     * @param h file path of hard icon file
     */
    private void drawDiffButtons(String e, String n, String h) {
        try {
            easy = ImageIO.read(new File("res/images/menu/easy" + e + ".png"));
            normal = ImageIO.read(new File("res/images/menu/normal" + n + ".png"));
            hard = ImageIO.read(new File("res/images/menu/hard" + h + ".png"));
        }
        catch (IOException e2) { }
    }

    /***
     * draw single, multi player icons
     * @param s file path of single icon file
     * @param m file path of multi player icon file
     */
    private void drawSingleMulti(String s, String m) {
        try {
            single = ImageIO.read(new File("res/images/menu/single" + s + ".png"));
            multi = ImageIO.read(new File("res/images/menu/multi" + m + ".png"));
        }
        catch (IOException e) { }
    }

    /**
     * draw join and create icons
     * @param c file path of create icon file
     * @param j file path of join icon file
     */
    private void drawCreateJoin(String c, String j) {
        try {
            create = ImageIO.read(new File("res/images/menu/create" + c + ".png"));
            join = ImageIO.read(new File("res/images/menu/join" + j + ".png"));
        }
        catch (IOException e) { }
    }

    /**
     * start the game
     * @param mode the mode of the game
     */
    private void startGame(int mode) {
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
                GameLoop game = new GameLoop(frame, mode, difficulty, "map1(27,27)");
            }
        });
    }
}