import javax.swing.*;
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
    private static int difficulty = 0;
    private static int mode;
    private static int stage;
    private static String mapName;
    private Timer timer, timer2;
    private TimerTask task, task2;
    private ExecutorService service;

    private static GameState state;

    public GameLoop(GameFrame frame, int mod, int diff, String map) {
        service = Executors.newFixedThreadPool(10);
        mapName = map;
        stage = 1;
        difficulty = diff;
        canvas = frame;
        init(mod, true);
        mode = mod;
    }

    public static int getDifficulty() {
        return difficulty;
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

    public static int getStage() {
        return stage;
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public void init(int mode, boolean first) {
        state = new GameState(mapName, difficulty);
        Tank tank = state.getTank();
        canvas.addKeyListener(tank.getKeyHandler());
        canvas.addMouseMotionListener(tank.getMouseHandler());
        canvas.addMouseListener(tank.getMouseHandler());
        if (! first) {
            stage = Integer.parseInt(mapName.substring(3, 4));
            for (int i = 0; i < 1000; i++) {
                canvas.render(state, false, true);
            }
            canvas.render(state, true, false);
        }
        if (mode == 1) {
            setMultiplayer(true);
            service.execute(new Server(2018));
        }
        if (mode == 2) {
            setMultiplayer(true);
            service.execute(new Client("172.26.6.219", 2018));
        }

        if (first) {
            service.execute(this);
        }
    }

    public static String getMapName() {
        return mapName;
    }

    public static void setState(GameState state) {
        GameLoop.state = state;
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
        canvas.render(state, true, false);
        if (isMultiplayer()) {
            timer = new Timer();
            task = new AlternativeUpdate();
            timer.scheduleAtFixedRate(task, 0, 200);
        }
        AudioPlayer.playSound("gameSound1.wav");
        if (isMultiplayer()) {

        }
        for (int i = 0; i < 1000; i++) {
            canvas.render(state, true, true);
        }
        canvas.render(state, true, false);
        while (!gameOver) {
            state.update();
            canvas.render(state, false, false);
            if (gameOver) {
                if (isMultiplayer()) {
                    if (state.getTank().isAlive() || state.getTank2().isAlive()) {
                        gameOver = false;
                        mapName = nextLevel(mapName);
                        init(mode, false);
                    }
                    else {
                    }
                }
                else {
                    if (state.getTank().isAlive()) {
                        gameOver = false;
                        mapName = nextLevel(mapName);
                        init(mode, false);
                    }
                    else {

                    }
                }
            }
        }
        if (isMultiplayer()) {
            task.cancel();
            timer.cancel();
        }
        task2.cancel();
        timer2.cancel();
        AudioPlayer.playSound("gameOver.wav");

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
    public static String nextLevel(String s) {
        String level = s.substring(3,4);
        s = s.substring(0, 3) + (Integer.parseInt(level) + 1) + s.substring(4, s.length());
        return s;
    }
}
