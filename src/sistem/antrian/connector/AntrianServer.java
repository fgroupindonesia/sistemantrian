package sistem.antrian.connector;

import com.google.gson.Gson;
import java.io.*;
import java.net.*;
import sistem.antrian.config.Antrian;
import sistem.antrian.config.DB;
import sistem.antrian.config.DataCache;
import sistem.antrian.config.Keys;
import sistem.antrian.frames.ServerFrame;
import sistem.antrian.fx.AudioPlayer;

/**
 * Class AntrianServer : used for receiving throught out socket to execute
 * command locally
 *
 * @author fgroupindonesia
 */
public class AntrianServer implements Runnable {

    DB db = new DB();
    int portDefault = 6666;
    ServerFrame svFrame;
    String companyName;

    int currentPost;

    public AntrianServer(ServerFrame svFrameIn) {
        svFrame = svFrameIn;
    }

    private void printout(String msg) {
        System.out.println("As Server : " + msg);
    }

    public void start() {

        try {

            printout("activated");

            companyName = db.getString(Keys.COMPANY_NAME);

            int portDefined = 0;

            if (db.getString(Keys.PORT_SERVER) != null) {
                portDefined = Integer.parseInt(db.getString(Keys.PORT_SERVER));
            } else {
                portDefined = portDefault;
            }

            printout("server ready at port " + portDefined);
            ServerSocket ss = new ServerSocket(portDefined);
            Socket s = ss.accept();

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            String str = (String) dis.readUTF();
            printout("reading " + str);

            // 1.convert dulu ke JSON
            // 2.check loket mana yang bertambah?
            Antrian obj = null;
            Gson gson = new Gson();

            obj = gson.fromJson(str, Antrian.class);

            if (obj != null) {
                // pass to the view UI
                if (obj.isFirstTime()) {
                    currentPost++;

                    // set the audio
                    svFrame.setAudio(obj.getLanguage());

                    String msg = gson.toJson(new DataCache(companyName, currentPost));

                    dos.writeUTF(msg);
                    printout("sending " + msg);

                } else {
                    svFrame.updateAntrian(obj);
                }
            }

            ss.close();
            printout("closing...");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    boolean keepWork = true;

    public void turnOff(boolean b) {
        keepWork = !b;
    }

    @Override
    public void run() {
        while (keepWork) {
            start();
        }
    }

}
