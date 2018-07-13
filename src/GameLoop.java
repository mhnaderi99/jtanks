import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 *
 */
public class GameLoop implements Runnable{


    private static GameFrame canvas;
    private static boolean gameOver = false;

    private static GameState state;

    public GameLoop(GameFrame frame, int mode) {
        canvas = frame;
        init(mode);
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public void init(int mode) {
        state = new GameState();
        Tank tank = state.getTank();
        canvas.addKeyListener(tank.getKeyHandler());
        canvas.addMouseMotionListener(tank.getMouseHandler());
        canvas.addMouseListener(tank.getMouseHandler());
        ExecutorService service = Executors.newFixedThreadPool(2);
        if (mode == 1) {
            //new Server(2018).run();
            service.execute(new Server(2018));
        }
        if (mode == 2) {
            //new Client("127.0.0.1", 2018).run();
            service.execute(new Client("127.0.0.1", 2018));
        }
        service.execute(this);
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
        AudioPlayer.playSound("gameSound1.wav");
        while (!gameOver) {
            state.update();
            canvas.render(state, false);
        }
        AudioPlayer.playSound("endOfGame.wav");
        canvas.render(state, true);
    }
}
