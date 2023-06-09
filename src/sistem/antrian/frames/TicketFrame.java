package sistem.antrian.frames;

import com.google.gson.Gson;
import helper.PrintUtility;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import sistem.antrian.config.Antrian;
import sistem.antrian.config.DB;
import sistem.antrian.config.Keys;
import sistem.antrian.config.Ticket;
import sistem.antrian.fx.Iconifier;

/**
 *
 * @author staff
 */
public class TicketFrame extends javax.swing.JFrame {

    /**
     * Creates new form TicketFrame
     */
    DB db = new DB();

    public TicketFrame() {
        initComponents();

        new Iconifier(this);

        prepareUI();
    }

    private void prepareUI() {
        // resize me
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        String dataTemp = db.getString(Keys.QUEUE_LIST);
        Antrian antrianList[] = new Gson().fromJson(dataTemp, Antrian[].class);

        if (antrianList != null) {
            if (antrianList.length > 0) {
                // creating button
                for (Antrian al : antrianList) {
                    JButton button = new javax.swing.JButton();

                    button.setFont(new Font("Tahoma", 0, 18));

                    button.setText(al.getLoket());
                    setPictureAccordingly(al.getAlphabet(), button);

                    button.setPreferredSize(new java.awt.Dimension(300, 100));

                    setActionPerformed(button, al);

                    panelTicket.add(button);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Belum ada Poli yang terhubung!");
        }

    }

    PrintUtility printEngine;

    private void setActionPerformed(JButton btn, Antrian al) {
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                Ticket ticket = new Ticket(db.getString(Keys.COMPANY_NAME), db.getString(Keys.COMPANY_LOGO));
                printEngine.setTicket(ticket);

            }
        });
    }

    private void setPictureAccordingly(String alphabet, JButton btn) {

        String filename = "/sistem/antrian/images/alphabet/" + alphabet.toLowerCase() + ".png";
        System.out.println("filenya " + filename);
        btn.setIcon(new ImageIcon(getClass().getResource(filename)));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelTicket = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistem Antrian - TICKETING");
        setType(java.awt.Window.Type.UTILITY);

        panelInfo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        panelInfo.setPreferredSize(new java.awt.Dimension(400, 100));
        panelInfo.setLayout(new java.awt.GridLayout(2, 1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TEKAN  TOMBOL PELAYANAN");
        panelInfo.add(jLabel1);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("DAN AMBIL TIKETNYA");
        panelInfo.add(jLabel2);

        getContentPane().add(panelInfo, java.awt.BorderLayout.PAGE_START);

        panelTicket.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 45));
        getContentPane().add(panelTicket, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TicketFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TicketFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TicketFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TicketFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TicketFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelTicket;
    // End of variables declaration//GEN-END:variables
}
