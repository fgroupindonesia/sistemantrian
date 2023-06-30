
package sistem.antrian.fx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

/**
 * Class MarqueeFX : used for displaying text effect
 * @author fgroupindonesia
 */
public class MarqueeFX implements ActionListener {

    String firstText;
    JLabel label;
    int initialCountDisplayed = 100;
    int countDisplayed = initialCountDisplayed;
    int indexStarted = 0;

    public MarqueeFX(JLabel labelNa) {
        label = labelNa;
        firstText = label.getText();
        
        if(firstText.length()< initialCountDisplayed){
            // once the text isn't too long
            initialCountDisplayed = firstText.length();
            countDisplayed = initialCountDisplayed;
        }
        
    }

    public void setText(String n) {
        firstText = n;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String newText = firstText.substring(indexStarted, countDisplayed);
        label.setText(newText);

        indexStarted++;
        if (countDisplayed < firstText.length()) {
            countDisplayed++;
        }
        
        if(indexStarted>=firstText.length()){
            indexStarted = 0;
            countDisplayed = initialCountDisplayed;
        }
    }

}
