
package sistem.antrian.config;

import java.util.Date;
import javax.swing.JOptionPane;
import sistem.antrian.frames.MainFrame;

/**
 * Class Activator : used for checking the current S/N applied.
 * @author fgroupindonesia
 */
public class Activator {

    DB dbInstance;
    Entity entity;
    MainFrame mFrame;
    
    public Activator(MainFrame mf){
        mFrame = mf;
        dbInstance = new DB();
    }
    
    
    public Activator(){
        dbInstance = new DB();
    }
    
    public void apply(String code){
        dbInstance.set(Keys.ACTIVATION_CODE, code);
    }
    
    public boolean validate(String code){
        
        // call the API from here
        // then save it locally
        apply(code);
        
        return true;
    }
    
    public void request(){
        String data = JOptionPane.showInputDialog(null, "Masukkan 45 digits Serial Number :", "Aktifasi", JOptionPane.INFORMATION_MESSAGE);
        if(data.isBlank()){
            reject();
        }else if(validate(data)){
            mFrame.lockButtons(false);
        }
    
    }
    
    public boolean isActivated(){
        return dbInstance.isRegistered();
    }
    
    public void message(String key){
        
    }
    
    private void reject(){
        JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
