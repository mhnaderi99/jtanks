import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * This class is for playing the audio sounds of the game
 */

public class AudioPlayer {

    /**
     * this function plays the audio sound
     * @param url the address of audio sound
     */

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
