import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class AudioPlayer {


    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try
                {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(new File("res/sounds/" + url)));
                    clip.start();
                }
                catch (Exception exc)
                {
                    exc.printStackTrace(System.out);
                }
            }
        }).start();
    }
}
