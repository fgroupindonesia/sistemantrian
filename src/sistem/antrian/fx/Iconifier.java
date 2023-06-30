package sistem.antrian.fx;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author staff
 */
public class Iconifier {

    JFrame frame;
    
    public Iconifier(JFrame cframe){
        frame = cframe;
        changeIcon();
    }
    
    private void changeIcon(){
        
        //String filename = "logo.png";
        ImageIcon icon = new ImageIcon(getClass().getResource("/sistem/antrian/images/logo.png"));
        frame.setIconImage(icon.getImage());
    }
    
}
