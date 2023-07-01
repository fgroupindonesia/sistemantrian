package sistem.antrian.config;

import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import sistem.antrian.frames.MainFrame;

/**
 * Class Activator : used for checking the current S/N applied.
 *
 * @author fgroupindonesia
 */
public class Activator {

    DB dbInstance;
    Entity entity;
    MainFrame mFrame;

    public enum Mode {
        OFFLINE,
        ONLINE
    };

    Mode mPreferred;

    public void setMode(Mode m) {
        mPreferred = m;
    }

    public Activator(MainFrame mf) {
        mFrame = mf;
        dbInstance = new DB();
    }

    public Activator() {
        dbInstance = new DB();
    }

    public void apply(String code) {
        dbInstance.set(Keys.ACTIVATION_CODE, code);
    }

    private boolean foundSingle(String find, String from) {
        boolean ans = false;
        int count = 0;

        String data[] = from.split("-");
        for (String n : data) {
            if (n.contains(find)) {
                count++;
            }
        }

        if (count == 1) {
            ans = true;
        }

        return ans;
    }

    private boolean foundMany(String find, String from, int expectedCount) {
        boolean ans = false;
        int count = 0;

        String data[] = from.split(find);
        if (data.length == expectedCount+1) {
            ans = true;
        }

        return ans;
    }

    private boolean foundOneCharacteristic(String codeIn) {
        boolean res = false;

        if (foundSingle("TR", codeIn) && foundSingle("BS", codeIn) == false && foundSingle("LM", codeIn) == false) {
            res = true;
        } else if (foundSingle("TR", codeIn) == false && foundSingle("BS", codeIn) && foundSingle("LM", codeIn) == false) {
            res = true;
        } else if (foundSingle("TR", codeIn) == false && foundSingle("BS", codeIn) == false && foundSingle("LM", codeIn)) {
            res = true;
        }

        return res;
    }

    public boolean validate(String code) {

        // call the API from here
        if (mPreferred == Mode.OFFLINE) {
            // we check it locally
            String codeTemp = "" + code;
            int length = code.replaceAll("-", "").length();
            if (length == 16) {
                // step 1 proceed
                System.out.println("step 1 - ok");

                System.out.println("Next checking " + code);
                if (foundOneCharacteristic(codeTemp)) {
                    // step 2 procceed
                    System.out.println("step 2 - ok");

                    if (foundMany("-", codeTemp, 3)) {
                        // step 3 proceed
                        System.out.println("step 3 - ok");
                        // then save it locally
                        apply(codeTemp);

                    } else {
                        System.out.println("step 3 - failed");
                        return false;
                    }

                } else {
                    System.out.println("step 2 - failed");
                    return false;
                }

            } else {
                System.out.println("step 1 - failed");
                return false;
            }

        } else {
            // we call the API through web
            System.err.println("API CALL NOT IMPLEMENTED YET!");

        }

        return true;
    }

    public void request() {
        String data = JOptionPane.showInputDialog(null, "Masukkan 45 digits Serial Number :", "Aktifasi", JOptionPane.INFORMATION_MESSAGE);

        if (data != null) {

            if (data.isBlank()) {
                reject();
            } else if (validate(data)) {
                mFrame.lockButtons(false);

            } else {
                rejectCode();
            }

        }

    }

    public boolean isActivated() {
        return dbInstance.isRegistered();
    }


    private void rejectCode() {
        JOptionPane.showMessageDialog(null, "Error", "Invalid Serial Number", JOptionPane.ERROR_MESSAGE);
    }

    private void reject() {
        JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
