/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SetLogo.java
 *
 * Created on May 28, 2012, 6:16:00 PM
 */
package orm.dao.Impl.GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author jean pierre
 */
public class SetLogo extends javax.swing.JFrame {

    String file = "";
    BufferedImage image = null;

    /** Creates new form SetLogo */
    public SetLogo() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public void picturePainter(File file) {
        try {
            image = ImageIO.read(file);
            this.setPreferredSize(new Dimension(
                    image.getWidth(), image.getHeight()));
        } catch (Exception e) {
            System.err.println(e);
        }
        repaint();
    }

    public static void copy(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            //PathFinder finder = new PathFinder();
            //String path = finder.pathFinder("reports/temp.txt");
            //path = path.substring(0, path.lastIndexOf("/"));
            FileOutputStream out = new FileOutputStream("C:/e_church_reports/logo.jpg");
            BufferedInputStream bin = new BufferedInputStream(in);
            BufferedOutputStream bout = new BufferedOutputStream(out);
            while (true) {
                int datum = bin.read();
                if (datum == -1) {
                    break;
                }
                bout.write(datum);
            }
            bout.flush();
            bin.close();
            bout.close();
            JOptionPane.showMessageDialog(null, "Log Copied successfully");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Copying file failed");
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Dimension d = photo.getSize();
        g.drawImage(image, photo.getX() + 8, photo.getY() + 30, d.width, d.height, null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        photo = new javax.swing.JLabel();
        btnBrowser = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        photo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        photo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnBrowser.setText("Browse");
        btnBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowserActionPerformed(evt);
            }
        });

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(photo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBrowser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(79, 79, 79)))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBrowser)
                .addGap(11, 11, 11)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowserActionPerformed
    JFileChooser fc = new JFileChooser();
    fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    int returnVal = fc.showOpenDialog(this);
    File choice = null;
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        choice = fc.getSelectedFile();
        file = choice.getAbsolutePath().toString();
        if (file.endsWith(".jpg") || file.endsWith(".png") || file.endsWith(".gif") || file.endsWith(".bmp") || file.endsWith(".tiff") || file.endsWith(".bmp") || file.endsWith(".JPG")) {
            picturePainter(choice);
        } else {
            JOptionPane.showMessageDialog(null, "The selected file is not a image", "Warning Message", JOptionPane.WARNING_MESSAGE);
            file = "";
        }
    }
}//GEN-LAST:event_btnBrowserActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    copy(new File(file));
}//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(SetLogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SetLogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SetLogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SetLogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SetLogo().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowser;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel photo;
    // End of variables declaration//GEN-END:variables
}
