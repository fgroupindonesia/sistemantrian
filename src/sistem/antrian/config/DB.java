package sistem.antrian.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 *
 * @author fgroupindonesia
 */
public class DB {

    // we store the properties here
    Properties prop = new Properties();

    public DB() {

    }

    private void load() {
        File f = new File();
        InputStream in = getClass().getResourceAsStream("xyz.properties");
        prop.load(in);
    }

    private void save() {
        try {
            // store the properties list in an output stream
            prop.store(System.out, "sistem.antrian.conf");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
