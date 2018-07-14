import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
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
    private Audio gameMusic;
    private Audio gameOverSound;
    private static GameState state;
    private static boolean exit = false;

    public GameLoop(GameFrame frame, int mod, int diff, String map) {
        service = Executors.newFixedThreadPool(10);
        try {
            gameMusic = new Audio(new File("res/sounds/gameSound1.wav"));
            gameMusic.setRepeats(true);
            gameOverSound = new Audio(new File("res/sounds/gameOver.wav"));
            gameOverSound.setRepeats(false);
        }
        catch (LineUnavailableException e) { }
        catch (IOException e) { }
        catch (UnsupportedAudioFileException e) { }
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

    public static void setExit(boolean e) {
        exit = e;
    }

    public static boolean isExit() {
        return exit;
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
                canvas.render(state, false, 1);
            }
            canvas.render(state, true, 0);
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
        canvas.render(state, true, 0);
        if (isMultiplayer()) {
            timer = new Timer();
            task = new AlternativeUpdate();
            timer.scheduleAtFixedRate(task, 0, 200);
        }
        gameMusic.play();
        for (int i = 0; i < 1000; i++) {
            canvas.render(state, true, 1);
        }
        canvas.render(state, true, 0);
        while (!gameOver) {
            state.update();
            canvas.render(state, false, 0);
            if (gameOver) {
                if (isMultiplayer()) {
                    if (state.getTank().isAlive() || state.getTank2().isAlive()) {
                        gameOver = false;
                        mapName = nextLevel(mapName);
                        init(mode, false);
                    }
                    else {
                        gameOver = true;
                    }
                }
                else {
                    if (state.getTank().isAlive()) {
                        gameOver = false;
                        mapName = nextLevel(mapName);
                        init(mode, false);
                    }
                    else {
                        gameOver = true;
                    }
                }
            }
        }
        if (isMultiplayer()) {
            task.cancel();
            timer.cancel();
        }
        gameMusic.stop();
        AudioPlayer.playSound("gameOver.wav");
        while (! exit) {
            canvas.render(state, true, 2);
        }
        System.exit(0);
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
            //gameMusic.play();
        }
    }
    public static String nextLevel(String s) {
        String level = s.substring(3,4);
        s = s.substring(0, 3) + (Integer.parseInt(level) + 1) + s.substring(4, s.length());
        return s;
    }

    private void pressAnyKeyToContinue() {
        try {
            System.in.read();
        }
        catch(Exception e)
        {}
    }
}
