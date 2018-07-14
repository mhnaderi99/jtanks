import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * this class is for playing and stoping audios
 */

public class Audio {

    private Clip clip;

    protected Audio() {
    }

    public Audio(File source) throws LineUnavailableException, MalformedURLException, IOException, UnsupportedAudioFileException {
        this(source.toURI().toURL());
    }

    public Audio(URL source) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        this(source.openStream());
    }

    public Audio(InputStream source) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        init(source);
    }

    /**
     * opens an audio
     * @param source the file path
     * @throws LineUnavailableException
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    protected void init(InputStream source) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(source));
    }

    /**
     * to set repeats for the audio
     * @param repeats true or false
     */
    public void setRepeats(boolean repeats) {
        clip.loop(repeats ? Clip.LOOP_CONTINUOUSLY : 1);
    }

    /**
     * for playing the audio
     */
    public void play() {
        clip.start();
    }

    /**
     * for stopping the audio
     */
    public void stop() {
        clip.stop();
    }

}