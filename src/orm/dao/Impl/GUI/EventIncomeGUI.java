/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EventIncomeGUI.java
 *
 * Created on May 12, 2012, 8:47:49 AM
 */
package orm.dao.Impl.GUI;

import java.util.Date;
import javax.swing.JOptionPane;
import orm.DAOFactory;
import orm.EventDetail;
import orm.EventIncome;
import orm.Notifier;

/**
 *
 * @author jean pierre
 */
public class EventIncomeGUI extends javax.swing.JInternalFrame {
    
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private String logger;

    /** Creates new form EventIncomeGUI */
    public EventIncomeGUI() {
        initComponents();
        setEvents();
    }
    
    public void setLogger(String logger) {
        this.logger = logger;
        txtUser.setText(logger);
    }
    
    private void setEvents() {
        cmbEvent.removeAllItems();
        for (EventDetail e : daoFactory.getEventDetailDAO().findAll()) {
            cmbEvent.addItem(e.getEventCode());
        }
    }
    
    private void clear() {
        cmbEvent.setSelectedIndex(0);
        txtIncome.setText(null);
        lblLogTime.setText(null);
    }
    
    private void save(EventIncome income) {
        income.setCreatedOn(new Date());
        income.setEventDetail(daoFactory.getEventDetailDAO().findByEventCode((String) cmbEvent.getSelectedItem()));
        int amount = 0;
        try {
            amount = Integer.parseInt(txtIncome.getText());
        } catch (Exception ex) {
            amount = 0;
        }
        income.setIncome(amount);
        income.setUser(daoFactory.getUserDAO().findByUsername(txtUser.getText()));
        boolean done = daoFactory.getEventIncomeDAO().saveOrUpdateEventIncome(income);
        if (done) {
            lblLogTime.setText((new Date()).toLocaleString());
            Notifier.notifier(done);
        }
        
    }
    
    private void save() {
        if (!txtIncome.getText().isEmpty()) {
            EventIncome income = new EventIncome();
            if (daoFactory.getEventIncomeDAO().findByEventCode((String) cmbEvent.getSelectedItem()) != null) {
                income = daoFactory.getEventIncomeDAO().findByEventCode((String) cmbEvent.getSelectedItem());
                int choice = JOptionPane.showConfirmDialog(null, "Event income amount will be updated from " + income.getIncome() + " to " + txtIncome.getText() + ". Do you want to go on with updating?", "Confirmation Required", JOptionPane.OK_CANCEL_OPTION);
                if (choice == 0) {
                    save(income);
                }
            } else {
                save(income);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Specify the income amount please!", "Income Amount Required", JOptionPane.WARNING_MESSAGE);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        cmbEvent = new javax.swing.JComboBox();
        txtIncome = new javax.swing.JTextField();
        lblLogTime = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Event Income");

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Event Income", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 102, 0)));

        jLabel1.setText("Income Logger:");

        jLabel2.setText("Event:");

        jLabel3.setText("Income:");

        jLabel4.setText("Log Date:");

        txtUser.setBackground(new java.awt.Color(204, 204, 204));
        txtUser.setEditable(false);
        txtUser.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        cmbEvent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtIncome.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtIncomeCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cmbEvent, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblLogTime, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIncome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblLogTime, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
    
private void txtIncomeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtIncomeCaretUpdate
    if (!txtIncome.getText().isEmpty()) {
        try {
            Integer.parseInt(txtIncome.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Enter Income amount as number please!","Warning",JOptionPane.WARNING_MESSAGE);
        }
    }
}//GEN-LAST:event_txtIncomeCaretUpdate
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbEvent;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblLogTime;
    private javax.swing.JTextField txtIncome;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
