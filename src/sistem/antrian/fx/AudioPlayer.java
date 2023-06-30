package sistem.antrian.fx;

import jaco.mp3.player.MP3Player;
import java.io.File;

/**
 * Class AudioPlayer : used for playing audio by specific language
 *
 * @author fgroupindonesia
 */
public class AudioPlayer {

    public enum Language {
        BAHASA, ENGLISH
    };

    private Language userLanguage;

    public AudioPlayer(Language chosen) {
        userLanguage = chosen;
    }

    public void play(int num, String alphabet, String namaPoli) {

        try {
            String dir = this.getClass().getResource("/sistem/antrian/audio/").getPath();
            String queue; 
            
            if (userLanguage == Language.BAHASA) {
                dir += "indonesia/";
                queue = "nomor-antrian";
            } else {
                dir += "english/";
                queue = "queue-number";
            }

            String filenameNumeric = dir + num + ".mp3";
            filenameNumeric = filenameNumeric.replaceAll("%20", " ");
            File fNumeric = new File(filenameNumeric);

            String filenamePoly = dir + namaPoli.replaceAll(" ", "-") + ".mp3";
            filenamePoly = filenamePoly.replaceAll("%20", " ");
            File fPoly = new File(filenamePoly);

            String filenameQueue = dir + queue + ".mp3";
            filenameQueue = filenameQueue.replaceAll("%20", " ");
            File fQueueText = new File(filenameQueue);

            String filenameAlphabet = dir + alphabet + ".mp3";
            filenameAlphabet = filenameAlphabet.replaceAll("%20", " ");
            File fAlphabet = new File(filenameAlphabet);

            //System.out.println(f.getAbsolutePath());
            /*if(f.exists()){
            System.out.println("OK");
            }else{
            System.out.println("No");
            }*/
            new MP3Player(fPoly).play();
            Thread.sleep(2500);
            new MP3Player(fQueueText).play();
            Thread.sleep(2500);
            new MP3Player(fAlphabet).play();
            Thread.sleep(1500);
            new MP3Player(fNumeric).play();
            Thread.sleep(2000);
        } catch (Exception ex) {
            System.out.println("Error at play() " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
