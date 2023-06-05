package sistem.antrian.fx;

import jaco.mp3.player.MP3Player;
import java.io.File;

/**
 *
 * @author staff
 */
public class AudioPlayer {

    public enum language {
        BAHASA, ENGLISH
    };

    private language userLanguage;

    public AudioPlayer(language chosen) {
        userLanguage = chosen;
    }

    public void play(int num) {

        try {
            String dir = this.getClass().getResource("/sistem/antrian/audio/").getPath();

            if (userLanguage == language.BAHASA) {
                dir += "indonesia/";
            } else {
                dir += "english/";
            }

            String filename = dir + num + ".mp3";
            filename = filename.replaceAll("%20", " ");
            File f = new File(filename);

            //System.out.println(f.getAbsolutePath());
            /*if(f.exists()){
            System.out.println("OK");
            }else{
            System.out.println("No");
            }*/
            new MP3Player(f).play();
            Thread.sleep(3000);
        } catch (Exception ex) {

        }
    }

    public static void main(String[] args) {
        AudioPlayer ap = new AudioPlayer(AudioPlayer.language.BAHASA);
        ap.play(9);
    }

}
