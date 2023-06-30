package sistem.antrian.frames;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import sistem.antrian.config.DB;
import sistem.antrian.config.Keys;
import sistem.antrian.fx.Iconifier;

/**
 * Class SettingsFrame : used for configuring the app
 *
 * @author fgroupindonesia
 */
public class SettingsFrame extends javax.swing.JFrame {

    /**
     * Creates new form SettingsFrame
     */
    DB database = new DB();
    ImageIcon okIcon = new ImageIcon(getClass().getResource("/sistem/antrian/images/ok24.png"));
    ImageIcon errorIcon = new ImageIcon(getClass().getResource("/sistem/antrian/images/x24.png"));

    enum Picture_Mode {
        LOGO,
        BACKGROUND
    };

    public SettingsFrame() {
        initComponents();
        
         new Iconifier(this);
         
        checkbox_autoruns.setSelected(database.getBoolean(Keys.AUTORUNS_WINDOWS));
        textarea_runningText.setText(database.getString(Keys.RUNNING_TEXT));
        textfield_namaPerusahaan.setText(database.getString(Keys.COMPANY_NAME));
        textfield_serverPort.setText(database.getString(Keys.PORT_SERVER));

        String opsDisplay = database.getString(Keys.DISPLAY_MODE);

        if (opsDisplay != null) {
            if (opsDisplay.toLowerCase().contains("horizontal")) {
                radio_display1Horizontal.setSelected(true);
            } else if (opsDisplay.contains("3 + 3")) {
                radio_display33Vertical.setSelected(true);
            } else {
                radio_display3Vertical.setSelected(true);
            }
        }

        applyPicture(database.getString(Keys.COMPANY_LOGO), label_resetLogo, label_logoPreviewBrowse);
        applyPicture(database.getString(Keys.BACKGROUND), label_resetBackground, label_backgroundPreviewBrowse);

        label_portStatus.setText("");
    }

    private void validatePort() {
        ServerSocket ss = null;
        DatagramSocket ds = null;

        try {
            int val = Integer.parseInt(textfield_serverPort.getText());

            // UTP port 
            ss = new ServerSocket(val);
            // UDP port checking
            ds = new DatagramSocket(val);

            label_portStatus.setIcon(okIcon);
            label_portStatus.setText("ok");

        } catch (Exception ex) {
            label_portStatus.setIcon(errorIcon);
            label_portStatus.setText("invalid");

        } finally {
            try {
                ss.close();
                ds.close();
            } catch (Exception ex) {

            }
        }

    }

    private void applyPicture(String filePath, JLabel labelReset, JLabel labelPictHolder) {
        if (filePath != null) {

            File file = new File(filePath);

            if (file.exists()) {
                labelPictHolder.setName(filePath);
                labelPictHolder.setText("");

                BufferedImage buffImg = null;
                try {
                    buffImg = ImageIO.read(file);
                    Image smallImg = buffImg.getScaledInstance(labelPictHolder.getWidth(), labelPictHolder.getHeight(), Image.SCALE_SMOOTH);
                    labelPictHolder.setIcon(new ImageIcon(smallImg));

                    labelReset.setVisible(true);
                    saveSettings();
                } catch (Exception e) {
                    labelReset.setVisible(false);
                }

            } else {
                labelReset.setVisible(false);
            }

        } else {
            labelPictHolder.setText("browse...");
            labelReset.setVisible(false);

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        checkbox_autoruns = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        textfield_namaPerusahaan = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textarea_runningText = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        label_logoPreviewBrowse = new javax.swing.JLabel();
        label_resetLogo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textfield_serverPort = new javax.swing.JTextField();
        label_portStatus = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        radio_display33Vertical = new javax.swing.JRadioButton();
        radio_display1Horizontal = new javax.swing.JRadioButton();
        radio_display3Vertical = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        label_backgroundPreviewBrowse = new javax.swing.JLabel();
        label_resetBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistem Antrian - SETTINGS");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("SETTING APLIKASI"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        checkbox_autoruns.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        checkbox_autoruns.setText("Autoruns saat Windows bermula.");
        checkbox_autoruns.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkbox_autorunsMouseClicked(evt);
            }
        });
        jPanel1.add(checkbox_autoruns, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 37, 304, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Nama Perusahaan : ");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 83, 167, -1));

        textfield_namaPerusahaan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textfield_namaPerusahaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textfield_namaPerusahaanKeyReleased(evt);
            }
        });
        jPanel1.add(textfield_namaPerusahaan, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 80, 300, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Display Mode (Server) :");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 280, 167, -1));

        textarea_runningText.setColumns(20);
        textarea_runningText.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textarea_runningText.setRows(5);
        textarea_runningText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textarea_runningTextKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(textarea_runningText);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 311, 340, 98));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Logo"));
        jPanel2.setLayout(new java.awt.BorderLayout());

        label_logoPreviewBrowse.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_logoPreviewBrowse.setText("browse...");
        label_logoPreviewBrowse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_logoPreviewBrowse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_logoPreviewBrowseMouseClicked(evt);
            }
        });
        jPanel2.add(label_logoPreviewBrowse, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 158, 118));

        label_resetLogo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_resetLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/antrian/images/x24.png"))); // NOI18N
        label_resetLogo.setText("Reset Logo");
        label_resetLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_resetLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_resetLogoMouseClicked(evt);
            }
        });
        jPanel1.add(label_resetLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 108, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Server Port : ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 167, -1));

        textfield_serverPort.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textfield_serverPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textfield_serverPortKeyReleased(evt);
            }
        });
        jPanel1.add(textfield_serverPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 240, 120, -1));

        label_portStatus.setText("status");
        jPanel1.add(label_portStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 240, 90, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Running Text : ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 276, 167, -1));

        buttonGroup1.add(radio_display33Vertical);
        radio_display33Vertical.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        radio_display33Vertical.setText("3 + 3 Display Vertical");
        radio_display33Vertical.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radio_display33VerticalMouseClicked(evt);
            }
        });
        jPanel1.add(radio_display33Vertical, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, 180, -1));

        buttonGroup1.add(radio_display1Horizontal);
        radio_display1Horizontal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        radio_display1Horizontal.setSelected(true);
        radio_display1Horizontal.setText("1 Display Horizontal");
        radio_display1Horizontal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radio_display1HorizontalMouseClicked(evt);
            }
        });
        jPanel1.add(radio_display1Horizontal, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 310, 180, -1));

        buttonGroup1.add(radio_display3Vertical);
        radio_display3Vertical.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        radio_display3Vertical.setText("3 Display Vertical");
        radio_display3Vertical.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radio_display3VerticalMouseClicked(evt);
            }
        });
        jPanel1.add(radio_display3Vertical, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 340, 180, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Background"));
        jPanel3.setLayout(new java.awt.BorderLayout());

        label_backgroundPreviewBrowse.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_backgroundPreviewBrowse.setText("browse...");
        label_backgroundPreviewBrowse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_backgroundPreviewBrowse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_backgroundPreviewBrowseMouseClicked(evt);
            }
        });
        jPanel3.add(label_backgroundPreviewBrowse, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 158, 118));

        label_resetBackground.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_resetBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/antrian/images/x24.png"))); // NOI18N
        label_resetBackground.setText("Reset Background");
        label_resetBackground.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_resetBackground.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_resetBackgroundMouseClicked(evt);
            }
        });
        jPanel1.add(label_resetBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 170, 190, 30));

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean isAnyPicture(JLabel labelPicHolder) {
        if (labelPicHolder.getText().contains("browse")) {
            return false;
        }

        return true;
    }

    private void label_logoPreviewBrowseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_logoPreviewBrowseMouseClicked

        applyLogo(Picture_Mode.LOGO);

    }//GEN-LAST:event_label_logoPreviewBrowseMouseClicked

    private void applyLogo(Picture_Mode modeWork) {

        boolean isPictFound;

        if (modeWork == Picture_Mode.LOGO) {
            isPictFound = isAnyPicture(label_logoPreviewBrowse);
        } else {
            isPictFound = isAnyPicture(label_backgroundPreviewBrowse);
        }

        if (!isPictFound) {
            // browse file
            String ket = "Image Picture (*.GIF, *.JPG, *.JPEG, *.PNG, *.BMP)";
            String ext[] = {"GIF", "PNG", "JPG", "JPEG", "gif", "png", "jpg", "jpeg"};

            JFileChooser jfc = new JFileChooser("");
            jfc.setFileFilter(new FileNameExtensionFilter(ket, ext));

            int res = jfc.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                if (modeWork == Picture_Mode.LOGO) {
                    applyPicture(file.getAbsolutePath(), label_resetLogo, label_logoPreviewBrowse);
                } else {
                    applyPicture(file.getAbsolutePath(), label_resetBackground, label_backgroundPreviewBrowse);
                }

            }
        }
    }

    private void label_resetLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_resetLogoMouseClicked

        removePicture(label_resetLogo, label_logoPreviewBrowse);

    }//GEN-LAST:event_label_resetLogoMouseClicked

    private void textfield_namaPerusahaanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textfield_namaPerusahaanKeyReleased
        saveSettings();
    }//GEN-LAST:event_textfield_namaPerusahaanKeyReleased

    private void textarea_runningTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textarea_runningTextKeyReleased
        saveSettings();
    }//GEN-LAST:event_textarea_runningTextKeyReleased

    private void checkbox_autorunsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkbox_autorunsMouseClicked
        saveSettings();
    }//GEN-LAST:event_checkbox_autorunsMouseClicked

    private void textfield_serverPortKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textfield_serverPortKeyReleased
        validatePort();
        saveSettings();
    }//GEN-LAST:event_textfield_serverPortKeyReleased

    private void radio_display1HorizontalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radio_display1HorizontalMouseClicked
        saveSettings();
    }//GEN-LAST:event_radio_display1HorizontalMouseClicked

    private void radio_display3VerticalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radio_display3VerticalMouseClicked
        saveSettings();
    }//GEN-LAST:event_radio_display3VerticalMouseClicked

    private void radio_display33VerticalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radio_display33VerticalMouseClicked
        saveSettings();
    }//GEN-LAST:event_radio_display33VerticalMouseClicked

    private void label_backgroundPreviewBrowseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_backgroundPreviewBrowseMouseClicked
        applyLogo(Picture_Mode.BACKGROUND);
    }//GEN-LAST:event_label_backgroundPreviewBrowseMouseClicked

    private void label_resetBackgroundMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_resetBackgroundMouseClicked

        removePicture(label_resetBackground, label_backgroundPreviewBrowse);

    }//GEN-LAST:event_label_resetBackgroundMouseClicked

    private void removePicture(JLabel labelReset, JLabel labelPictHolder) {
        labelPictHolder.setText("browse...");
        labelPictHolder.setName(null);
        labelReset.setVisible(false);
        labelPictHolder.setIcon(null);
    }

    private void saveSettings() {
        database.set(Keys.COMPANY_NAME, textfield_namaPerusahaan.getText());
        database.set(Keys.RUNNING_TEXT, textarea_runningText.getText());
        database.set(Keys.PORT_SERVER, textfield_serverPort.getText());

        String opsDisplay = null;
        if (radio_display1Horizontal.isSelected()) {
            opsDisplay = radio_display1Horizontal.getText();
        } else if (radio_display3Vertical.isSelected()) {
            opsDisplay = radio_display3Vertical.getText();
        } else {
            opsDisplay = radio_display33Vertical.getText();
        }

        database.set(Keys.DISPLAY_MODE, opsDisplay);

        if (label_logoPreviewBrowse.getName() != null) {
            database.set(Keys.COMPANY_LOGO, label_logoPreviewBrowse.getName());
        }

        if (label_backgroundPreviewBrowse.getName() != null) {
            database.set(Keys.BACKGROUND, label_backgroundPreviewBrowse.getName());
        }

        database.set(Keys.AUTORUNS_WINDOWS, checkbox_autoruns.isSelected());
    }

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
            java.util.logging.Logger.getLogger(SettingsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettingsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettingsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettingsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SettingsFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox checkbox_autoruns;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_backgroundPreviewBrowse;
    private javax.swing.JLabel label_logoPreviewBrowse;
    private javax.swing.JLabel label_portStatus;
    private javax.swing.JLabel label_resetBackground;
    private javax.swing.JLabel label_resetLogo;
    private javax.swing.JRadioButton radio_display1Horizontal;
    private javax.swing.JRadioButton radio_display33Vertical;
    private javax.swing.JRadioButton radio_display3Vertical;
    private javax.swing.JTextArea textarea_runningText;
    private javax.swing.JTextField textfield_namaPerusahaan;
    private javax.swing.JTextField textfield_serverPort;
    // End of variables declaration//GEN-END:variables
}
