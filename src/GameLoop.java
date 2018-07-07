public class GameLoop implements Runnable{


    private static GameFrame canvas;

    private Tank tank;

    public GameLoop(GameFrame frame) {
        canvas = frame;
    }

    public void init() {
        tank = new Tank();
        canvas.addKeyListener(tank.getKeyHandler());
        canvas.addMouseMotionListener(tank.getMouseHandler());
        canvas.addMouseListener(tank.getMouseHandler());
    }

    public static int getXOfCanvas() {
        return canvas.getXOfFrame();
    }

    public static int getYOfCanvas() {
        return canvas.getYOfFrame();
    }

    @Override
    public void run() {
        boolean gameOver = false;
        while (!gameOver) {
            tank.update();
            canvas.render(tank);
        }
        canvas.render(tank);
    }
}
