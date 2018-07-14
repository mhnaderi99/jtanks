import java.awt.*;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Timer;
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
    private static boolean multiplayer = false;
    private static int mode;
    private Timer timer, timer2;
    private TimerTask task, task2;

    private static GameState state;

    public GameLoop(GameFrame frame, int mod) {
        canvas = frame;
        init(mod);
        mode = mod;
    }

    public static int getMode() {
        return mode;
    }

    public static void setMultiplayer(boolean multiplayer) {
        GameLoop.multiplayer = multiplayer;
    }

    public static boolean isMultiplayer() {
        return multiplayer;
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
            setMultiplayer(true);
            service.execute(new Server(2018));
        }
        if (mode == 2) {
            setMultiplayer(true);
            service.execute(new Client("localhost", 2018));
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
        timer2 = new Timer();
        task2 = new MusicPlayer();
        timer2.scheduleAtFixedRate(task2, 0, 33000);
        canvas.render(state, true);
        if (isMultiplayer()) {
            timer = new Timer();
            task = new AlternativeUpdate();
            timer.scheduleAtFixedRate(task, 0, 500);
        }
        AudioPlayer.playSound("gameSound1.wav");
        if (isMultiplayer()) {

        }
        while (!gameOver) {
            state.update();
            canvas.render(state, false);
        }
        AudioPlayer.playSound("endOfGame.wav");
        canvas.render(state, true);
    }

    private class AlternativeUpdate extends TimerTask {

        @Override
        public void run() {
            if (mode == 1) {
                int x = state.getTank().getXPosition(), y = state.getTank().getYPosition();
                String sender = "SERVER-", pre = "ALL-";
                String message = x + "," + y;
                ClientHandler.writeOnStream(sender + pre + message);
            }
            if (mode == 2) {
                int x = state.getTank().getXPosition(), y = state.getTank().getYPosition();
                String sender = "CLIENT-", pre = "ALL-";
                String message = x + "," + y;
                Client.writeOnStream(sender + pre + message);
            }
        }
    }

    private class MusicPlayer extends TimerTask {

        @Override
        public void run() {
            AudioPlayer.playSound("gameSound1.wav");
        }
    }
}
