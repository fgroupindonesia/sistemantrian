package sistem.antrian.frames;

import java.awt.CardLayout;
import sistem.antrian.config.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import sistem.antrian.config.Antrian;
import sistem.antrian.connector.AntrianServer;
import sistem.antrian.fx.AudioPlayer;
import sistem.antrian.fx.AudioPlayer.Language;
import sistem.antrian.fx.Iconifier;
import sistem.antrian.fx.MarqueeFX;

/**
 * Class ServerFrame : used for displaying the app at the end-user
 *
 * @author fgroupindonesia
 */
public class ServerFrame extends javax.swing.JFrame {

    /**
     * Creates new form ClientFrame
     */
    DB db = new DB();
    String dmode;
    String runningMessage;
    String namaPerusahaan;

    AntrianServer antrianServer;
    CardLayout card = null;

    public ServerFrame() {
        initComponents();

        // preparation
        prepareUI();
        new Iconifier(this);

        dmode = db.getString(Keys.DISPLAY_MODE);
        runningMessage = db.getString(Keys.RUNNING_TEXT);
        namaPerusahaan = db.getString(Keys.COMPANY_NAME);

        labelTitlePerusahaan.setText(namaPerusahaan);

        antrianServer = new AntrianServer(this);
        new Thread(antrianServer).start();

        card = (CardLayout) panelMain.getLayout();

        showPanelByMode();
        animatedNews();

    }

    public void setAudio(Language lan) {
        audioPlayer = new AudioPlayer(lan);
    }

    private void prepareUI() {

        // resize me
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // clearing horizontal layout
        labelTitleMode1.setText("-");
        labelOrderNumMode1.setText("-");

        // clearing 3x3 display vertical
        labelTitleMode3_up_left.setText("-");
        labelOrderNumMode3_up_left.setText("-");

        labelTitleMode3_middle_left.setText("-");
        labelOrderNumMode3_middle_left.setText("-");

        labelTitleMode3_bottom_left.setText("-");
        labelOrderNumMode3_bottom_left.setText("-");

        labelTitleMode3_up_right.setText("-");
        labelOrderNumMode3_up_right.setText("-");

        labelTitleMode3_middle_right.setText("-");
        labelOrderNumMode3_middle_right.setText("-");

        labelTitleMode3_bottom_right.setText("-");
        labelOrderNumMode3_bottom_right.setText("-");

        // clearing 3 display vertical
        labelTitleMode2_up.setText("-");
        labelOrderNumMode2_up.setText("-");

        labelTitleMode2_middle.setText("-");
        labelOrderNumMode2_middle.setText("-");

        labelTitleMode2_bottom.setText("-");
        labelOrderNumMode2_bottom.setText("-");

    }

    public void updateAntrian(Antrian obj) {

        if (dmode != null) {

            if (dmode.toLowerCase().contains("horizontal")) {
                // this is the horizontal mode
                labelTitleMode1.setText(obj.getLoket());
                labelOrderNumMode1.setText(obj.asTicket());
            } else if (dmode.contains("3 + 3")) {

                // this is the 3 + 3 vertical
                if (obj.getClientNumberOrder() == 1) {
                    labelTitleMode3_up_left.setText(obj.getLoket());
                    labelOrderNumMode3_up_left.setText(obj.asTicket());
                } else if (obj.getClientNumberOrder() == 2) {
                    labelTitleMode3_middle_left.setText(obj.getLoket());
                    labelOrderNumMode3_middle_left.setText(obj.asTicket());
                } else if (obj.getClientNumberOrder() == 3) {
                    labelTitleMode3_bottom_left.setText(obj.getLoket());
                    labelOrderNumMode3_bottom_left.setText(obj.asTicket());
                } else if (obj.getClientNumberOrder() == 4) {
                    labelTitleMode3_up_right.setText(obj.getLoket());
                    labelOrderNumMode3_up_right.setText(obj.asTicket());
                } else if (obj.getClientNumberOrder() == 5) {
                    labelTitleMode3_middle_right.setText(obj.getLoket());
                    labelOrderNumMode3_middle_right.setText(obj.asTicket());
                } else if (obj.getClientNumberOrder() == 6) {
                    labelTitleMode3_bottom_right.setText(obj.getLoket());
                    labelOrderNumMode3_bottom_right.setText(obj.asTicket());
                }

            } else {
                // this is 3 Display Vertical
                if (obj.getClientNumberOrder() == 1) {
                    labelTitleMode2_up.setText(obj.getLoket());
                    labelOrderNumMode2_up.setText(obj.asTicket());
                } else if (obj.getClientNumberOrder() == 2) {
                    labelTitleMode2_middle.setText(obj.getLoket());
                    labelOrderNumMode2_middle.setText(obj.asTicket());
                } else if (obj.getClientNumberOrder() == 3) {
                    labelTitleMode2_bottom.setText(obj.getLoket());
                    labelOrderNumMode2_bottom.setText(obj.asTicket());
                }
            }

            // pass the number , along with the poly name
            audioPlayer.play(obj.getNumber(), obj.getAlphabet(), obj.getLoket().toLowerCase());

        }

    }

    AudioPlayer audioPlayer = null;

    private void animatedNews() {

        JLabel labelRunningText = null;
        if (dmode != null) {

            if (dmode.toLowerCase().contains("horizontal")) {
                // this is the horizontal mode
                labelRunningText = labelNewsMode1;

            } else if (dmode.contains("3 + 3")) {
                // this is the 3 + 3 vertical
                labelRunningText = labelNewsMode3;
            } else {
                // this is 3 Display Vertical
                labelRunningText = labelNewsMode2;
            }

            MarqueeFX mFX = new MarqueeFX(labelRunningText);
            Timer t = new Timer(400, mFX); // set a timer
            t.start();

        }

    }

    private void showPanelByMode() {

        if (dmode != null) {

            String lokasiBg = db.getString(Keys.BACKGROUND);

            if (dmode.toLowerCase().contains("horizontal")) {
                // this is the horizontal mode
                card.show(panelMain, "panelMode1");
                applyBackgroundImage(lokasiBg, labelPictureMode1);
                labelNewsMode1.setText(runningMessage);
            } else if (dmode.contains("3 + 3")) {
                // this is the 3 + 3 vertical
                card.show(panelMain, "panelMode3");
                applyBackgroundImage(lokasiBg, labelPictureMode3);
                labelNewsMode3.setText(runningMessage);
            } else {
                // this is 3 Display Vertical
                card.show(panelMain, "panelMode2");
                applyBackgroundImage(lokasiBg, labelPictureMode2);
                labelNewsMode2.setText(runningMessage);
            }

        }

    }

    private ImageIcon applyBackgroundImage(String lokasiBg, JLabel picHolder) {
        ImageIcon img = null;

        picHolder.setText("picture");

        if (lokasiBg != null) {
            File file = new File(lokasiBg);
            if (file.exists()) {

                BufferedImage buffImg = null;
                try {
                    buffImg = ImageIO.read(file);
                    Image smallImg = buffImg.getScaledInstance(picHolder.getWidth(), picHolder.getHeight(), Image.SCALE_SMOOTH);
                    img = new ImageIcon(smallImg);
                    picHolder.setText("");
                    picHolder.setIcon(img);
                } catch (Exception e) {
                    System.out.println("Error... at applyBackgroundImage() " + e.getMessage());
                }
            }
        } else {
            ImageIcon imageIcon = (new ImageIcon(getClass().getResource("/sistem/antrian/images/building.jpg")));
            Image smallImg = imageIcon.getImage().getScaledInstance(picHolder.getWidth(), picHolder.getHeight(), Image.SCALE_SMOOTH);
            
            imageIcon = new ImageIcon(smallImg);
            
            picHolder.setText("");
            picHolder.setIcon(imageIcon);
        }

        return img;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelHeader = new javax.swing.JPanel();
        labelTitlePerusahaan = new javax.swing.JLabel();
        panelMain = new javax.swing.JPanel();
        panelMode1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        labelPictureMode1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        labelNewsMode1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        labelTitleMode1 = new javax.swing.JLabel();
        labelOrderNumMode1 = new javax.swing.JLabel();
        panelMode2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        panelMode2Loket1 = new javax.swing.JPanel();
        labelTitleMode2_up = new javax.swing.JLabel();
        labelOrderNumMode2_up = new javax.swing.JLabel();
        panelMode2Loket2 = new javax.swing.JPanel();
        labelTitleMode2_middle = new javax.swing.JLabel();
        labelOrderNumMode2_middle = new javax.swing.JLabel();
        panelMode2Loket3 = new javax.swing.JPanel();
        labelTitleMode2_bottom = new javax.swing.JLabel();
        labelOrderNumMode2_bottom = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        labelPictureMode2 = new javax.swing.JLabel();
        labelNewsMode2 = new javax.swing.JLabel();
        panelMode3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        panelMode3Loket1 = new javax.swing.JPanel();
        labelTitleMode3_up_left = new javax.swing.JLabel();
        labelOrderNumMode3_up_left = new javax.swing.JLabel();
        panelMode3Loket2 = new javax.swing.JPanel();
        labelTitleMode3_middle_left = new javax.swing.JLabel();
        labelOrderNumMode3_middle_left = new javax.swing.JLabel();
        panelMode3Loket3 = new javax.swing.JPanel();
        labelTitleMode3_bottom_left = new javax.swing.JLabel();
        labelOrderNumMode3_bottom_left = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        panelMode3Loket4 = new javax.swing.JPanel();
        labelTitleMode3_up_right = new javax.swing.JLabel();
        labelOrderNumMode3_up_right = new javax.swing.JLabel();
        panelMode3Loket5 = new javax.swing.JPanel();
        labelTitleMode3_middle_right = new javax.swing.JLabel();
        labelOrderNumMode3_middle_right = new javax.swing.JLabel();
        panelMode3Loket6 = new javax.swing.JPanel();
        labelTitleMode3_bottom_right = new javax.swing.JLabel();
        labelOrderNumMode3_bottom_right = new javax.swing.JLabel();
        labelPictureMode3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        labelNewsMode3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistem Antrian - SERVER");
        setPreferredSize(new java.awt.Dimension(500, 500));
        setType(java.awt.Window.Type.UTILITY);

        panelHeader.setBackground(new java.awt.Color(102, 255, 51));
        panelHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        panelHeader.setPreferredSize(new java.awt.Dimension(500, 75));

        labelTitlePerusahaan.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelTitlePerusahaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelTitlePerusahaan.setText("RUMAH SAKIT COMPLEX");

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 942, Short.MAX_VALUE)
            .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelHeaderLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(labelTitlePerusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, 899, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 73, Short.MAX_VALUE)
            .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelHeaderLayout.createSequentialGroup()
                    .addGap(0, 22, Short.MAX_VALUE)
                    .addComponent(labelTitlePerusahaan)
                    .addGap(0, 22, Short.MAX_VALUE)))
        );

        getContentPane().add(panelHeader, java.awt.BorderLayout.PAGE_START);

        panelMain.setLayout(new java.awt.CardLayout());

        panelMode1.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(575, 100));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(400, 400));
        jPanel3.setLayout(new java.awt.BorderLayout());

        labelPictureMode1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPictureMode1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/antrian/images/building.jpg"))); // NOI18N
        labelPictureMode1.setText("picture");
        jPanel3.add(labelPictureMode1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jPanel4.setPreferredSize(new java.awt.Dimension(575, 75));
        jPanel4.setLayout(new java.awt.BorderLayout());

        labelNewsMode1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelNewsMode1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelNewsMode1.setText("Berita Pengumuman disini ... Ga papa-panjang juga yang penting nongol...!");
        jPanel4.add(labelNewsMode1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        panelMode1.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jPanel2.setPreferredSize(new java.awt.Dimension(575, 100));
        jPanel2.setLayout(new java.awt.BorderLayout());

        labelTitleMode1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        labelTitleMode1.setForeground(new java.awt.Color(51, 0, 255));
        labelTitleMode1.setText("POLI GIGI");
        labelTitleMode1.setPreferredSize(new java.awt.Dimension(400, 44));
        jPanel2.add(labelTitleMode1, java.awt.BorderLayout.WEST);

        labelOrderNumMode1.setFont(new java.awt.Font("Tahoma", 1, 56)); // NOI18N
        labelOrderNumMode1.setForeground(new java.awt.Color(51, 0, 255));
        labelOrderNumMode1.setText("A.100");
        jPanel2.add(labelOrderNumMode1, java.awt.BorderLayout.EAST);

        panelMode1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        panelMain.add(panelMode1, "panelMode1");

        panelMode2.setLayout(new java.awt.BorderLayout());

        jPanel5.setPreferredSize(new java.awt.Dimension(500, 361));
        jPanel5.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        panelMode2Loket1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        panelMode2Loket1.setLayout(new java.awt.GridLayout(2, 1));

        labelTitleMode2_up.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelTitleMode2_up.setText("POLI ANAK :");
        panelMode2Loket1.add(labelTitleMode2_up);

        labelOrderNumMode2_up.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        labelOrderNumMode2_up.setText("A.100");
        panelMode2Loket1.add(labelOrderNumMode2_up);

        jPanel5.add(panelMode2Loket1);

        panelMode2Loket2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        panelMode2Loket2.setLayout(new java.awt.GridLayout(2, 1));

        labelTitleMode2_middle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelTitleMode2_middle.setText("POLI ANAK :");
        panelMode2Loket2.add(labelTitleMode2_middle);

        labelOrderNumMode2_middle.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        labelOrderNumMode2_middle.setText("A.100");
        panelMode2Loket2.add(labelOrderNumMode2_middle);

        jPanel5.add(panelMode2Loket2);

        panelMode2Loket3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        panelMode2Loket3.setLayout(new java.awt.GridLayout(2, 1));

        labelTitleMode2_bottom.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelTitleMode2_bottom.setText("POLI ANAK :");
        panelMode2Loket3.add(labelTitleMode2_bottom);

        labelOrderNumMode2_bottom.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        labelOrderNumMode2_bottom.setText("A.100");
        panelMode2Loket3.add(labelOrderNumMode2_bottom);

        jPanel5.add(panelMode2Loket3);

        panelMode2.add(jPanel5, java.awt.BorderLayout.EAST);

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jPanel6.setLayout(new java.awt.BorderLayout());

        labelPictureMode2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelPictureMode2.setForeground(new java.awt.Color(0, 0, 255));
        labelPictureMode2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPictureMode2.setText("picture");
        labelPictureMode2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelPictureMode2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(labelPictureMode2, java.awt.BorderLayout.CENTER);

        labelNewsMode2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelNewsMode2.setForeground(new java.awt.Color(0, 0, 204));
        labelNewsMode2.setText("Running Text disini....");
        labelNewsMode2.setPreferredSize(new java.awt.Dimension(34, 75));
        jPanel6.add(labelNewsMode2, java.awt.BorderLayout.PAGE_END);

        panelMode2.add(jPanel6, java.awt.BorderLayout.CENTER);

        panelMain.add(panelMode2, "panelMode2");

        panelMode3.setLayout(new java.awt.BorderLayout());

        jPanel7.setPreferredSize(new java.awt.Dimension(953, 200));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel9.setPreferredSize(new java.awt.Dimension(300, 509));
        jPanel9.setLayout(new java.awt.GridLayout(3, 1));

        panelMode3Loket1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        panelMode3Loket1.setLayout(new java.awt.GridLayout(2, 1));

        labelTitleMode3_up_left.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelTitleMode3_up_left.setText("POLI ANAK :");
        panelMode3Loket1.add(labelTitleMode3_up_left);

        labelOrderNumMode3_up_left.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        labelOrderNumMode3_up_left.setText("A.100");
        panelMode3Loket1.add(labelOrderNumMode3_up_left);

        jPanel9.add(panelMode3Loket1);

        panelMode3Loket2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        panelMode3Loket2.setLayout(new java.awt.GridLayout(2, 1));

        labelTitleMode3_middle_left.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelTitleMode3_middle_left.setText("POLI ANAK :");
        panelMode3Loket2.add(labelTitleMode3_middle_left);

        labelOrderNumMode3_middle_left.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        labelOrderNumMode3_middle_left.setText("A.100");
        panelMode3Loket2.add(labelOrderNumMode3_middle_left);

        jPanel9.add(panelMode3Loket2);

        panelMode3Loket3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        panelMode3Loket3.setLayout(new java.awt.GridLayout(2, 1));

        labelTitleMode3_bottom_left.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelTitleMode3_bottom_left.setText("POLI ANAK :");
        panelMode3Loket3.add(labelTitleMode3_bottom_left);

        labelOrderNumMode3_bottom_left.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        labelOrderNumMode3_bottom_left.setText("A.100");
        panelMode3Loket3.add(labelOrderNumMode3_bottom_left);

        jPanel9.add(panelMode3Loket3);

        jPanel7.add(jPanel9, java.awt.BorderLayout.WEST);

        jPanel10.setPreferredSize(new java.awt.Dimension(300, 509));
        jPanel10.setLayout(new java.awt.GridLayout(3, 1));

        panelMode3Loket4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        panelMode3Loket4.setLayout(new java.awt.GridLayout(2, 1));

        labelTitleMode3_up_right.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelTitleMode3_up_right.setText("POLI ANAK :");
        panelMode3Loket4.add(labelTitleMode3_up_right);

        labelOrderNumMode3_up_right.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        labelOrderNumMode3_up_right.setText("A.100");
        panelMode3Loket4.add(labelOrderNumMode3_up_right);

        jPanel10.add(panelMode3Loket4);

        panelMode3Loket5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        panelMode3Loket5.setLayout(new java.awt.GridLayout(2, 1));

        labelTitleMode3_middle_right.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelTitleMode3_middle_right.setText("POLI ANAK :");
        panelMode3Loket5.add(labelTitleMode3_middle_right);

        labelOrderNumMode3_middle_right.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        labelOrderNumMode3_middle_right.setText("A.100");
        panelMode3Loket5.add(labelOrderNumMode3_middle_right);

        jPanel10.add(panelMode3Loket5);

        panelMode3Loket6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        panelMode3Loket6.setLayout(new java.awt.GridLayout(2, 1));

        labelTitleMode3_bottom_right.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelTitleMode3_bottom_right.setText("POLI ANAK :");
        panelMode3Loket6.add(labelTitleMode3_bottom_right);

        labelOrderNumMode3_bottom_right.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        labelOrderNumMode3_bottom_right.setText("A.100");
        panelMode3Loket6.add(labelOrderNumMode3_bottom_right);

        jPanel10.add(panelMode3Loket6);

        jPanel7.add(jPanel10, java.awt.BorderLayout.EAST);

        labelPictureMode3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPictureMode3.setText("picture");
        jPanel7.add(labelPictureMode3, java.awt.BorderLayout.CENTER);

        panelMode3.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel8.setPreferredSize(new java.awt.Dimension(953, 100));
        jPanel8.setLayout(new java.awt.BorderLayout());

        labelNewsMode3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelNewsMode3.setForeground(new java.awt.Color(0, 0, 204));
        labelNewsMode3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNewsMode3.setText("Running Text Disini...");
        jPanel8.add(labelNewsMode3, java.awt.BorderLayout.CENTER);

        panelMode3.add(jPanel8, java.awt.BorderLayout.PAGE_END);

        panelMain.add(panelMode3, "panelMode3");

        getContentPane().add(panelMain, java.awt.BorderLayout.CENTER);

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
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel labelNewsMode1;
    private javax.swing.JLabel labelNewsMode2;
    private javax.swing.JLabel labelNewsMode3;
    private javax.swing.JLabel labelOrderNumMode1;
    private javax.swing.JLabel labelOrderNumMode2_bottom;
    private javax.swing.JLabel labelOrderNumMode2_middle;
    private javax.swing.JLabel labelOrderNumMode2_up;
    private javax.swing.JLabel labelOrderNumMode3_bottom_left;
    private javax.swing.JLabel labelOrderNumMode3_bottom_right;
    private javax.swing.JLabel labelOrderNumMode3_middle_left;
    private javax.swing.JLabel labelOrderNumMode3_middle_right;
    private javax.swing.JLabel labelOrderNumMode3_up_left;
    private javax.swing.JLabel labelOrderNumMode3_up_right;
    private javax.swing.JLabel labelPictureMode1;
    private javax.swing.JLabel labelPictureMode2;
    private javax.swing.JLabel labelPictureMode3;
    private javax.swing.JLabel labelTitleMode1;
    private javax.swing.JLabel labelTitleMode2_bottom;
    private javax.swing.JLabel labelTitleMode2_middle;
    private javax.swing.JLabel labelTitleMode2_up;
    private javax.swing.JLabel labelTitleMode3_bottom_left;
    private javax.swing.JLabel labelTitleMode3_bottom_right;
    private javax.swing.JLabel labelTitleMode3_middle_left;
    private javax.swing.JLabel labelTitleMode3_middle_right;
    private javax.swing.JLabel labelTitleMode3_up_left;
    private javax.swing.JLabel labelTitleMode3_up_right;
    private javax.swing.JLabel labelTitlePerusahaan;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelMain;
    private javax.swing.JPanel panelMode1;
    private javax.swing.JPanel panelMode2;
    private javax.swing.JPanel panelMode2Loket1;
    private javax.swing.JPanel panelMode2Loket2;
    private javax.swing.JPanel panelMode2Loket3;
    private javax.swing.JPanel panelMode3;
    private javax.swing.JPanel panelMode3Loket1;
    private javax.swing.JPanel panelMode3Loket2;
    private javax.swing.JPanel panelMode3Loket3;
    private javax.swing.JPanel panelMode3Loket4;
    private javax.swing.JPanel panelMode3Loket5;
    private javax.swing.JPanel panelMode3Loket6;
    // End of variables declaration//GEN-END:variables
}
