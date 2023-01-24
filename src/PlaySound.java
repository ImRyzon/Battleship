import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlaySound {

    public void buttonSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("buttonsound.wav"));

        // create clip reference
        Clip clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.start();
    }

    public void playBackground1(boolean play) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("background_music1.wav"));

        // create clip reference
        Clip clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);

        clip.start();

        if(play == false) {
            clip.stop();
        }
    }
}
