package sistem.antrian.connector;

import com.google.gson.Gson;
import java.io.*;
import java.net.*;
import sistem.antrian.config.Antrian;
import sistem.antrian.config.DB;
import sistem.antrian.config.DataCache;
import sistem.antrian.config.Keys;
import sistem.antrian.frames.ClientFrame;
import sistem.antrian.fx.AudioPlayer.Language;

/**
 * Class AntrianClient : used for connecting the socket to the server
 *
 * @author fgroupindonesia
 */
public class AntrianClient implements Runnable {

    int portDefined = 0;
    int portDefault = 6666;

    int numberFromServer;

    String ipServerDefined;
    String ipServerDefault = "localhost";

    String dataObtained;

    // opening client database locally
    DB db = new DB(true);

    ClientFrame cframe;
    Language chosenLanguage;

    public AntrianClient(ClientFrame cf, Language pickedLanguage) {
        cframe = cf;
        chosenLanguage = pickedLanguage;
    }

    private void printout(String message) {
        System.out.println("As Client : " + message);
    }

    public void start() {
        try {

            printout("activated");

            preparingCridentials();

            printout("connecting at " + ipServerDefined + ":" + portDefined);
            Socket s = new Socket(ipServerDefined, portDefined);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            DataInputStream dis = new DataInputStream(s.getInputStream());

            // we post first time for connecting
            String json = new Gson().toJson(new Antrian(true, chosenLanguage));
            dout.writeUTF(json);

            printout("Writing data to server from client " + json);

            //dout.flush();
            //dout.close();
            dataObtained = dis.readUTF();
            Gson gson = new Gson();
            // lets read a message of json from server
            DataCache dcache = gson.fromJson(dataObtained, DataCache.class);

            cframe.eliminateAlphabets(dcache.getUsedAlphabet());
            cframe.setCompanyName(dcache.getCompanyName());

            numberFromServer = dcache.getClientNumberOrder();

            if (numberFromServer > 0) {
                cframe.setClientNumber(numberFromServer);
            }

            printout("Reading back as client from server : " + numberFromServer);

            s.close();
            printout("Closing...");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error at start() " + e.getMessage());
        }

    }

    private void preparingCridentials() {
        // get some cridentials from db
        String portFound = db.getString(Keys.PORT_DESTINATION_SERVER);
        if (portFound != null) {
            portDefined = Integer.parseInt(portFound);
        } else {
            portDefined = portDefault;
        }

        String ipServerFound = db.getString(Keys.IP_SERVER);
        if (ipServerFound != null) {
            ipServerDefined = ipServerFound;
        } else {
            ipServerDefined = ipServerDefault;
        }
        // now we are ready!
    }

    public void sendData(Antrian n) {
        try {

            preparingCridentials();

            Socket s = new Socket(ipServerDefined, portDefined);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            // we post first time for connecting
            String json = new Gson().toJson(n);
            dout.writeUTF(json);
            printout("sending " + json);

            s.close();
            printout("closing...");
        } catch (Exception e) {
            System.out.println("Error at sendData() " + e.getMessage());
        }

    }

    @Override
    public void run() {
        start();
    }

}
