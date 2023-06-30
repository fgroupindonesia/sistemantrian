package sistem.antrian.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Class DB : used for storing the data locally
 *
 * @author fgroupindonesia
 */
public class DB {

    // we store the properties here
    Properties prop = new Properties();

    public boolean isRegistered() {
        return false;
    }

    private void scrambled() {

    }

    private void unscrambled() {

    }

    public DB() {
        load(fileSystem);
    }

    public DB(boolean asClientMode) {
        asClient = asClientMode;
        load(fileClient);
    }

    String fileNameSystemSettings = "sistem.antrian.prop";
    String fileNameClientSettings = "sistem.antrian.client.prop";
    String appPathLocation = "";
    File fileSystem = new File(fileNameSystemSettings);
    File fileClient = new File(fileNameClientSettings);
    boolean asClient = false;

    private void load(File file) {

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            InputStream in = new FileInputStream(file);
            prop.load(in);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error di load() " + e.getMessage());
        }
    }

    public void set(String key, String val) {
        prop.setProperty(key, val);
        save();
    }

    public void set(String key, Boolean val) {
        prop.setProperty(key, val.toString());
        save();
    }

    private void save() {
        if (asClient) {
            save(fileClient);
        } else {
            save(fileSystem);
        }
    }

    public String getString(String key) {
        return prop.getProperty(key, null);
    }

    public boolean getBoolean(String key) {
        boolean val = false;

        if (prop.getProperty(key, null) != null) {
            val = true;
        }

        return val;
    }

    private void save(File file) {
        try {
            // store the properties list in an output stream
            // prop.store(System.out, fileName);
            FileOutputStream fps = new FileOutputStream(file);
            prop.store(fps, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
