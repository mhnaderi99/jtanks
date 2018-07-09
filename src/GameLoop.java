public class GameLoop implements Runnable{


    private static GameFrame canvas;

    private static GameState state;

    public GameLoop(GameFrame frame) {
        canvas = frame;
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

    @Override
    public void run() {
        boolean gameOver = false;
        canvas.render(state, true);
        while (!gameOver) {
            state.update();
            canvas.render(state, false);
        }
        canvas.render(state, true);
    }
}
