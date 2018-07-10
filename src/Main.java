import javax.swing.*;
import java.awt.*;
import java.util.EventListener;

public class Main {

    public static void main(String[] args) {
        ThreadPool.init();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame frame = new GameFrame("Test");
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.initBufferStrategy();

                GameLoop game = new GameLoop(frame);
                //game.init();
                ThreadPool.execute(game);
            }
        });
    }
}
