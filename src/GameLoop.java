public class GameLoop implements Runnable{


    private static GameFrame canvas;
    private static boolean gameOver = false;

    private static GameState state;

    public GameLoop(GameFrame frame) {
        canvas = frame;
        init();
    }

    public void init() {
        state = new GameState();
        Tank tank = state.getTank();
        canvas.addKeyListener(tank.getKeyHandler());
        canvas.addMouseMotionListener(tank.getMouseHandler());
        canvas.addMouseListener(tank.getMouseHandler());
    }

    public static GameState getState() {
        return state;
    }

    public static GameFrame getCanvas() {
        return canvas;
    }

    public static int getXOfCanvas() {
        return canvas.getXOfFrame();
    }

    public static int getYOfCanvas() {
        return canvas.getYOfFrame();
    }

    public static void setGameOver(boolean gameOver) {
        GameLoop.gameOver = gameOver;
    }

    @Override
    public void run() {
        canvas.render(state, true);
        while (!gameOver) {
            state.update();
            canvas.render(state, false);
        }
        canvas.render(state, true);
    }
}
