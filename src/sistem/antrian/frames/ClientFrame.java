package sistem.antrian.frames;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import sistem.antrian.config.Antrian;
import sistem.antrian.config.DB;
import sistem.antrian.config.Keys;
import sistem.antrian.connector.AntrianClient;
import sistem.antrian.fx.AudioPlayer.Language;
import sistem.antrian.fx.Iconifier;

/**
 * Class ClientFrame : used for client app as loket user
 *
 * @author fgroupindonesia
 */
public class ClientFrame extends javax.swing.JFrame {

    /**
     * Creates new form ServerFrame
     */
    CardLayout card = null;

    // DB as client mode
    DB db = new DB(true);

    AntrianClient antrianClient;

    String chosenPoly;
    int currentNum;
    String chosenAlphabet;
    Language chosenLanguage;

    int clientNumberOrder;

    ImageIcon activeIcon = new ImageIcon(getClass().getResource("/sistem/antrian/images/play.png"));
    ImageIcon restIcon = new ImageIcon(getClass().getResource("/sistem/antrian/images/warning.png"));

    enum Client_Mode {
        active,
        rest
    };

    // for label positioning
    Dimension dimKecil, dimLebar;

    public void setClientNumber(int c) {
        clientNumberOrder = c;
    }

    public void setCompanyName(String name) {
        labelCompanyName.setText(name);
    }

    public ClientFrame() {
        initComponents();

        new Iconifier(this);

        card = (CardLayout) panelMain.getLayout();
        textfield_ipServer.setText(db.getString(Keys.IP_SERVER));
        textfield_portServer.setText(db.getString(Keys.PORT_DESTINATION_SERVER));

        if (db.getString(Keys.AUDIO_OPERATOR) != null) {
            if (db.getString(Keys.AUDIO_OPERATOR).contains("inggris")) {
                radioButtonEnglish.setSelected(true);
                chosenLanguage = Language.ENGLISH;
            } else {
                radioButtonBahasaIndo.setSelected(true);
                chosenLanguage = Language.BAHASA;
            }

        }

        antrianClient = new AntrianClient(this, chosenLanguage);
        new Thread(antrianClient).start();

        readPoliNames();
        chosenPoly = db.getString(Keys.POLY_NAME);
        //System.out.println("ditemukan " + chosenPoly);
        setCombobox(comboboxPoliName, chosenPoly);

        // for alphabetic order
        prepareAlphabet();
        chosenAlphabet = db.getString(Keys.ALPHABET);
        //System.out.println("ditemukan " + chosenAlphabet);
        setCombobox(comboboxAlphabet, chosenAlphabet);

        // for ui
        prepareLabelDims();

        // before activated
        labelNoAntrian.setText("-");
        labelClientMode.setText("-");
        labelCompanyName.setText("-");
        labelPoliName.setText("-");
    }

    private void setCombobox(JComboBox combo, String val) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (val != null) {
                if (combo.getItemAt(i).equals(val)) {
                    combo.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void prepareAlphabet() {
        comboboxAlphabet.removeAllItems();

        String data = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
        for (int i = 0; i < data.length(); i++) {
            comboboxAlphabet.addItem("" + data.charAt(i));
        }
    }

    private void prepareLabelDims() {
        dimKecil = labelClientMode.getPreferredSize();
        int w = (int) (dimKecil.getWidth() + 100);
        int h = (int) dimKecil.getHeight();

        dimLebar = new Dimension(w, h);

    }

    private void switchPanelToSetting() {
        card.show(panelMain, "panelSettings");
    }

    private void switchPanelToWork() {
        card.show(panelMain, "panelWork");
    }

    private void readPoliNames() {

        // clear first
        comboboxPoliName.removeAllItems();

        String nama = this.getClass().getResource("/sistem/antrian/audio/").getPath();
        nama = nama.replaceAll("%20", " ");

        if (radioButtonBahasaIndo.isSelected()) {
            nama += "indonesia/nama-poli.txt";
        } else {
            nama += "english/clinic-name.txt";
        }

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(nama));
            String line = reader.readLine();

            while (line != null) {
                //System.out.println(line);
                // read next line
                line = reader.readLine();
                comboboxPoliName.addItem(line);
            }

            reader.close();
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
        }

    }

    private void saveSettings() {

        String valAudio = null;
        if (radioButtonBahasaIndo.isSelected()) {
            valAudio = radioButtonBahasaIndo.getText();
        } else {
            valAudio = radioButtonEnglish.getText();
        }

        db.set(Keys.POLY_NAME, comboboxPoliName.getSelectedItem().toString());
        db.set(Keys.ALPHABET, comboboxAlphabet.getSelectedItem().toString());

        db.set(Keys.AUDIO_OPERATOR, valAudio);
        db.set(Keys.IP_SERVER, textfield_ipServer.getText());
        db.set(Keys.CLIENT_MODE, buttonMode.getText());

        db.set(Keys.PORT_DESTINATION_SERVER, textfield_portServer.getText());
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
        panelHeader = new javax.swing.JPanel();
        labelCompanyName = new javax.swing.JLabel();
        panelMain = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        labelNoAntrian = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        labelClientMode = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        buttonMode = new javax.swing.JButton();
        buttonNext = new javax.swing.JButton();
        buttonSettings = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        labelPoliName = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        comboboxPoliName = new javax.swing.JComboBox<>();
        comboboxAlphabet = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        radioButtonEnglish = new javax.swing.JRadioButton();
        radioButtonBahasaIndo = new javax.swing.JRadioButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        textfield_ipServer = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        textfield_portServer = new javax.swing.JTextField();
        buttonConnectServer = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        buttonBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistem Antrian - LOKET");
        setType(java.awt.Window.Type.UTILITY);

        panelHeader.setBackground(new java.awt.Color(0, 255, 0));
        panelHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        panelHeader.setPreferredSize(new java.awt.Dimension(500, 75));
        panelHeader.setLayout(new java.awt.BorderLayout());

        labelCompanyName.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelCompanyName.setText("RUMAH SAKIT MEDIKAKOM");
        panelHeader.add(labelCompanyName, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelHeader, java.awt.BorderLayout.PAGE_START);

        panelMain.setPreferredSize(new java.awt.Dimension(751, 436));
        panelMain.setLayout(new java.awt.CardLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("LOKET"));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(739, 100));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("SAAT INI"));
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 100));
        jPanel4.setLayout(new java.awt.BorderLayout());

        labelNoAntrian.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        labelNoAntrian.setText("A.100");
        labelNoAntrian.setPreferredSize(new java.awt.Dimension(120, 44));
        jPanel4.add(labelNoAntrian, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel4, java.awt.BorderLayout.WEST);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("MODE"));
        jPanel5.setPreferredSize(new java.awt.Dimension(300, 100));
        jPanel5.setLayout(new java.awt.BorderLayout());

        labelClientMode.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        labelClientMode.setText("AKTIF");
        labelClientMode.setPreferredSize(new java.awt.Dimension(130, 44));
        jPanel5.add(labelClientMode, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel5, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 1, 10, 1));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        buttonMode.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/antrian/images/warning.png"))); // NOI18N
        buttonMode.setText("Istirahat");
        buttonMode.setEnabled(false);
        buttonMode.setPreferredSize(new java.awt.Dimension(200, 100));
        buttonMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonModeActionPerformed(evt);
            }
        });
        jPanel3.add(buttonMode);

        buttonNext.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/antrian/images/next.png"))); // NOI18N
        buttonNext.setText("Selanjutnya");
        buttonNext.setPreferredSize(new java.awt.Dimension(200, 100));
        buttonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });
        jPanel3.add(buttonNext);

        buttonSettings.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/antrian/images/edit.png"))); // NOI18N
        buttonSettings.setText("Penyetelan");
        buttonSettings.setPreferredSize(new java.awt.Dimension(200, 100));
        buttonSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSettingsActionPerformed(evt);
            }
        });
        jPanel3.add(buttonSettings);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("NAMA POLI"));
        jPanel6.setPreferredSize(new java.awt.Dimension(608, 100));
        jPanel6.setLayout(new java.awt.BorderLayout());

        labelPoliName.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        labelPoliName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPoliName.setText("POLI ANAK");
        jPanel6.add(labelPoliName, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        panelMain.add(jPanel1, "panelWork");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("LOKET"));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel8.setPreferredSize(new java.awt.Dimension(739, 100));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("NAMA POLI"));
        jPanel9.setPreferredSize(new java.awt.Dimension(300, 100));
        jPanel9.setLayout(new java.awt.BorderLayout());

        comboboxPoliName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboboxPoliName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxPoliNameActionPerformed(evt);
            }
        });
        jPanel9.add(comboboxPoliName, java.awt.BorderLayout.CENTER);

        comboboxAlphabet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboboxAlphabet.setPreferredSize(new java.awt.Dimension(100, 20));
        comboboxAlphabet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxAlphabetActionPerformed(evt);
            }
        });
        jPanel9.add(comboboxAlphabet, java.awt.BorderLayout.WEST);

        jPanel8.add(jPanel9, java.awt.BorderLayout.WEST);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("AUDIO OPERATOR"));
        jPanel10.setPreferredSize(new java.awt.Dimension(300, 100));
        jPanel10.setLayout(new java.awt.GridLayout(2, 1));

        buttonGroup1.add(radioButtonEnglish);
        radioButtonEnglish.setSelected(true);
        radioButtonEnglish.setText("bahasa inggris");
        radioButtonEnglish.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioButtonEnglishMouseClicked(evt);
            }
        });
        jPanel10.add(radioButtonEnglish);

        buttonGroup1.add(radioButtonBahasaIndo);
        radioButtonBahasaIndo.setText("bahasa indonesia");
        radioButtonBahasaIndo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioButtonBahasaIndoMouseClicked(evt);
            }
        });
        jPanel10.add(radioButtonBahasaIndo);

        jPanel8.add(jPanel10, java.awt.BorderLayout.EAST);

        jPanel7.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 1, 10, 1));
        jPanel11.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("IP SERVER"));

        textfield_ipServer.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        textfield_ipServer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textfield_ipServer.setText("0.0.0.0");
        textfield_ipServer.setPreferredSize(new java.awt.Dimension(300, 50));
        textfield_ipServer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textfield_ipServerFocusLost(evt);
            }
        });
        textfield_ipServer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textfield_ipServerKeyReleased(evt);
            }
        });
        jPanel12.add(textfield_ipServer);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Port : ");
        jPanel12.add(jLabel2);

        textfield_portServer.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        textfield_portServer.setPreferredSize(new java.awt.Dimension(100, 50));
        textfield_portServer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textfield_portServerKeyReleased(evt);
            }
        });
        jPanel12.add(textfield_portServer);

        buttonConnectServer.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonConnectServer.setText("Hubungkan");
        jPanel12.add(buttonConnectServer);

        jPanel11.add(jPanel12);

        buttonBack.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/antrian/images/back.png"))); // NOI18N
        buttonBack.setText("Kembali");
        buttonBack.setPreferredSize(new java.awt.Dimension(200, 100));
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBackActionPerformed(evt);
            }
        });
        jPanel13.add(buttonBack);

        jPanel11.add(jPanel13);

        jPanel7.add(jPanel11, java.awt.BorderLayout.CENTER);

        panelMain.add(jPanel7, "panelSettings");

        getContentPane().add(panelMain, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBackActionPerformed
        switchPanelToWork();
    }//GEN-LAST:event_buttonBackActionPerformed

    private void buttonSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSettingsActionPerformed
        switchPanelToSetting();
    }//GEN-LAST:event_buttonSettingsActionPerformed

    private void radioButtonEnglishMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioButtonEnglishMouseClicked
        saveSettings();
        readPoliNames();
    }//GEN-LAST:event_radioButtonEnglishMouseClicked

    private void radioButtonBahasaIndoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioButtonBahasaIndoMouseClicked
        saveSettings();
        readPoliNames();
    }//GEN-LAST:event_radioButtonBahasaIndoMouseClicked

    private void lockButtonNext(boolean b) {
        buttonNext.setEnabled(!b);
    }

    private void buttonModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonModeActionPerformed

        db.set(Keys.CLIENT_MODE, buttonMode.getText());
        labelClientMode.setText(buttonMode.getText().toUpperCase());

        if (buttonMode.getText().toLowerCase().contains("istirahat")) {
            buttonMode.setText("aktif");
            changePictureMode(Client_Mode.active);
            labelClientMode.setPreferredSize(dimLebar);

            lockButtonNext(true);
        } else {
            buttonMode.setText("istirahat");
            changePictureMode(Client_Mode.rest);
            labelClientMode.setPreferredSize(dimKecil);

            lockButtonNext(false);
        }

        saveSettings();

    }//GEN-LAST:event_buttonModeActionPerformed

    private void buttonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextActionPerformed

        labelClientMode.setText("AKTIF");
        buttonMode.setEnabled(true);

        // continue the iteration
        currentNum++;

        Antrian antrian = new Antrian();
        antrian.setLoket(chosenPoly);
        antrian.setNumber(currentNum);
        antrian.setAlphabet(chosenAlphabet);
        antrian.setClientNumberOrder(clientNumberOrder);

        antrianClient.sendData(antrian);

        // update the local no
        labelNoAntrian.setText(antrian.asTicket());
        labelPoliName.setText(chosenPoly);

    }//GEN-LAST:event_buttonNextActionPerformed

    public void eliminateAlphabets(String data[]) {

        if (data != null) {
            for (String huruf : data) {
                comboboxAlphabet.removeItem(huruf);
            }

            comboboxAlphabet.setSelectedIndex(0);
            chosenAlphabet = comboboxAlphabet.getSelectedItem().toString();
            saveSettings();

        }

    }


    private void textfield_ipServerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textfield_ipServerKeyReleased
        saveSettings();
        showButtonConnect();
    }//GEN-LAST:event_textfield_ipServerKeyReleased

    private void textfield_ipServerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textfield_ipServerFocusLost

        if (textfield_ipServer.getText().isBlank()) {
            textfield_ipServer.setText("0.0.0.0");
        }
    }//GEN-LAST:event_textfield_ipServerFocusLost

    private void comboboxPoliNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxPoliNameActionPerformed
        if (comboboxPoliName.isPopupVisible()) {
            saveSettings();
            chosenPoly = comboboxPoliName.getSelectedItem().toString();
            labelPoliName.setText(chosenPoly);
        }
    }//GEN-LAST:event_comboboxPoliNameActionPerformed

    private void comboboxAlphabetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxAlphabetActionPerformed
        if (comboboxAlphabet.isPopupVisible()) {
            saveSettings();
            chosenAlphabet = comboboxAlphabet.getSelectedItem().toString();
        }
    }//GEN-LAST:event_comboboxAlphabetActionPerformed

    private void textfield_portServerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textfield_portServerKeyReleased
        saveSettings();
        showButtonConnect();
    }//GEN-LAST:event_textfield_portServerKeyReleased

    private void showButtonConnect() {
        if (!textfield_ipServer.getText().isBlank() && !textfield_portServer.getText().isBlank()) {
            buttonConnectServer.setVisible(true);
        } else {
            buttonConnectServer.setVisible(false);
        }
    }

    private void changePictureMode(Client_Mode c) {
        if (c == Client_Mode.active) {
            buttonMode.setIcon(activeIcon);
        } else {
            buttonMode.setIcon(restIcon);
        }
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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBack;
    private javax.swing.JButton buttonConnectServer;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton buttonMode;
    private javax.swing.JButton buttonNext;
    private javax.swing.JButton buttonSettings;
    private javax.swing.JComboBox<String> comboboxAlphabet;
    private javax.swing.JComboBox<String> comboboxPoliName;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel labelClientMode;
    private javax.swing.JLabel labelCompanyName;
    private javax.swing.JLabel labelNoAntrian;
    private javax.swing.JLabel labelPoliName;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelMain;
    private javax.swing.JRadioButton radioButtonBahasaIndo;
    private javax.swing.JRadioButton radioButtonEnglish;
    private javax.swing.JTextField textfield_ipServer;
    private javax.swing.JTextField textfield_portServer;
    // End of variables declaration//GEN-END:variables

}
