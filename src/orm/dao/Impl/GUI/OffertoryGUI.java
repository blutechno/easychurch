/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OffertoryGUI.java
 *
 * Created on May 4, 2012, 12:53:14 PM
 */
package orm.dao.Impl.GUI;

import java.awt.Color;
import java.util.Date;
import javax.swing.JOptionPane;
import orm.DAOFactory;
import orm.Notifier;
import orm.Offertory;
import orm.OffertoryType;

/**
 *
 * @author jean pierre
 */
public class OffertoryGUI extends javax.swing.JInternalFrame {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private String encoder;

    /** Creates new form OffertoryGUI */
    public OffertoryGUI() {
        initComponents();
        setTypes();
    }

    private void setTypes() {
        cmbOffertoryType.removeAllItems();
        for (OffertoryType t : daoFactory.getOffertoryTypeDAO().findAll()) {
            cmbOffertoryType.addItem(t.getOffertoryTypeName());
        }
    }

    public void setEncoder(String encoder) {
        this.encoder = encoder;
        txtEncoder.setText(encoder);
    }

    private void setBack(int choice) {
        if (choice == 0) {
            txtId.setEditable(true);
            txtId.setBackground(Color.white);
        } else {
            txtId.setEditable(false);
            txtId.setBackground(new Color(204, 204, 204));
        }
    }

    private void clear() {
        cmbOffertoryType.setSelectedIndex(0);
        txtOffertoryAmount.setText(null);
        chDate.setDate(null);
        txtDescription.setText(null);
        txtId.setText(null);
    }

    private void search() {
        if (!txtId.getText().isEmpty()) {
            try {
                int id = Integer.parseInt(txtId.getText());
                try {
                    Offertory off = daoFactory.getOffertoryDAO().findById(id);
                    for (int i = 0; i < cmbOffertoryType.getItemCount(); i++) {
                        if (off.getOffertoryType().getOffertoryTypeName().equals(cmbOffertoryType.getItemAt(i))) {
                            cmbOffertoryType.setSelectedIndex(i);
                            break;
                        }
                    }
                    cmbOffertoryType.setSelectedIndex(0);
                    txtOffertoryAmount.setText("" + off.getOffertoryAmount());
                    chDate.setDate(off.getRecordDate());
                    txtDescription.setText(off.getDescription());
                    txtId.setText("" + off.getOffertoryId());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "No record found for the entered Id. Try again");
                }
                setBack(1);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Enter a number of the Record Id Please");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Enter Record Id before you hit search button");
        }
    }

    private void save() {
        if (chDate.getDate().after(new Date())) {
            JOptionPane.showMessageDialog(null, "Offertory Date cannot be greater that today's");
        } else {
            if (!txtDescription.getText().equals("")) {
                if (!txtOffertoryAmount.getText().isEmpty()) {
                    Offertory offertory = new Offertory();
                    boolean updated = false;
                    if (!txtId.getText().isEmpty()) {
                        try {
                            offertory = daoFactory.getOffertoryDAO().findById(Integer.parseInt(txtId.getText()));
                            updated = true;
                        } catch (Exception ex) {
                        }
                    }
                    offertory.setOffertoryType(daoFactory.getOffertoryTypeDAO().findByName((String) cmbOffertoryType.getSelectedItem()));
                    offertory.setOffertoryAmount(Integer.parseInt(txtOffertoryAmount.getText()));
                    offertory.setRecordDate(chDate.getDate());
                    offertory.setDescription(txtDescription.getText());
                    offertory.setUser(daoFactory.getUserDAO().findByUsername(txtEncoder.getText()));
                    boolean done = daoFactory.getOffertoryDAO().saveOrUpdateOffertory(offertory);
                    if (!updated && done) {
                        int size = daoFactory.getOffertoryDAO().findAll().size();
                        int id = daoFactory.getOffertoryDAO().findAll().get(size - 1).getOffertoryId();
                        txtId.setText("" + id);
                    }
                    Notifier.notifier(done);
                } else {
                    JOptionPane.showMessageDialog(null, "Enter Offertory amount before you attempt recording", "Income Missing", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Offertory Comment/Description is required.", "Recall Message", JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbOffertoryType = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtOffertoryAmount = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        chDate = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        txtEncoder = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Offertory");

        jToolBar1.setBackground(new java.awt.Color(0, 0, 0));
        jToolBar1.setRollover(true);

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Clear");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Save");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton6.setBackground(new java.awt.Color(0, 0, 0));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Exit");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Find");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jButton4.setBackground(new java.awt.Color(0, 0, 0));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Search");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Offertory Encoding", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 153, 0)));

        jLabel1.setText("Offertory Type:");

        cmbOffertoryType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Offertory Amount:");

        txtOffertoryAmount.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtOffertoryAmountCaretUpdate(evt);
            }
        });

        jLabel3.setText("Date:");

        chDate.setDateFormatString("dd/MM/yyyy");

        jLabel4.setText("Encoder:");

        txtEncoder.setBackground(new java.awt.Color(204, 204, 204));
        txtEncoder.setEditable(false);
        txtEncoder.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel5.setText("Comment on Offertory:");

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        jScrollPane1.setViewportView(txtDescription);

        jLabel6.setText("Recording Id:");

        txtId.setBackground(new java.awt.Color(204, 204, 204));
        txtId.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbOffertoryType, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chDate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEncoder, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                            .addComponent(txtOffertoryAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                            .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)))
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbOffertoryType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtOffertoryAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtEncoder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(chDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    clear();
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    save();
}//GEN-LAST:event_jButton2ActionPerformed

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    this.dispose();
}//GEN-LAST:event_jButton6ActionPerformed

private void txtOffertoryAmountCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtOffertoryAmountCaretUpdate
    if (!txtOffertoryAmount.getText().isEmpty()) {
        try {
            Integer.parseInt(txtOffertoryAmount.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Enter the Offertory amount as a number please");
        }
    }
}//GEN-LAST:event_txtOffertoryAmountCaretUpdate

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    setBack(0);
}//GEN-LAST:event_jButton3ActionPerformed

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    search();
}//GEN-LAST:event_jButton4ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser chDate;
    private javax.swing.JComboBox cmbOffertoryType;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtEncoder;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtOffertoryAmount;
    // End of variables declaration//GEN-END:variables
}
