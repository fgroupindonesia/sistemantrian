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
 * Class AntrianServer : used for receiving throughout socket to execute command
 * locally
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
                    
                    // then grab the alphabets from local data
                    String alphabets [] = getUsedAlphabet();

                    String msg = gson.toJson(new DataCache(companyName, currentPost, alphabets));

                    dos.writeUTF(msg);
                    printout("sending " + msg);

                } else {
                    // save the data locally
                    updateAntrianDataLocally(obj);

                    svFrame.updateAntrian(obj);
                }
            }

            ss.close();
            printout("closing...");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            turnOff(true);
            System.out.println("Error at 95.");
        }

    }

    private String[] getUsedAlphabet() {
        // this will read from local data

        String temp = db.getString(Keys.QUEUE_LIST);
        String allAlphabets[] = null;
        Antrian dataTemp[] = new Gson().fromJson(temp, Antrian[].class);

        if (dataTemp != null) {
            allAlphabets = new String[dataTemp.length];

            int i = 0;
            for (Antrian an : dataTemp) {
                allAlphabets[i] = an.getAlphabet();
                i++;
            }

        }

        return allAlphabets;

    }

    private void updateAntrianDataLocally(Antrian antri) {
        Antrian antrianList[] = null;
        Antrian antrianBaru[] = null;

        String temp = db.getString(Keys.QUEUE_LIST);
        if (temp != null) {
            antrianList = new Gson().fromJson(temp, Antrian[].class);
            antrianBaru = addCopyData(antrianList, antri);

        } else {
            antrianBaru = new Antrian[1];
            antrianBaru[0] = antri;
        }

        String data = new Gson().toJson(antrianBaru);
        db.set(Keys.QUEUE_LIST, data);

    }

    private Antrian[] addCopyData(Antrian firstList[], Antrian antriBaru) {
        Antrian[] newList = null;

        int foundPost = 0;
        boolean duplicate = false;
        for (Antrian n : firstList) {
            if (antriBaru.getLoket().equals(n.getLoket())) {
                duplicate = true;
                break;
            }
            foundPost++;

        }

        if (duplicate == false) {
            newList = new Antrian[firstList.length + 1];
            newList = copy(firstList, newList);
            // lastly
            newList[firstList.length] = antriBaru;

        } else {
            newList = new Antrian[firstList.length];
            newList = copy(firstList, newList);
            newList[foundPost].setNumber(antriBaru.getNumber());
        }

        return newList;
    }

    private Antrian[] copy(Antrian f[], Antrian s[]) {
        int i = 0;
        for (Antrian fx : f) {
            s[i] = fx;
            i++;
        }

        return s;
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
