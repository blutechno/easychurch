/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EventGUI.java
 *
 * Created on May 10, 2012, 10:37:30 AM
 */
package orm.dao.Impl.GUI;

import java.awt.Color;
import javax.swing.JOptionPane;
import orm.DAOFactory;
import orm.EventDetail;
import orm.EventType;
import orm.Notifier;
import orm.Pastor;

/**
 *
 * @author jean pierre
 */
public class EventGUI extends javax.swing.JInternalFrame {
    
    private DAOFactory daoFactory = DAOFactory.getInstance();

    /** Creates new form EventGUI */
    public EventGUI() {
        initComponents();
        setTypes();
        setMainSpeaker();
        setEventCode();
    }
    
    private void setTypes() {
        cmbEventType.removeAllItems();
        for (EventType type : daoFactory.getEventTypeDAO().findAll()) {
            cmbEventType.addItem(type.getDesignation());
        }
    }
    
    private void setEventCode() {
        try {
            int size = daoFactory.getEventDetailDAO().findAll().size();
            txtEventCode.setText(String.format("EV" + "%03d", (size + 1)));
        } catch (Exception ex) {
            txtEventCode.setText("EV00" + 1);
        }
    }
    
    private void setMainSpeaker() {
        cmbMainSpeaker.removeAllItems();
        for (Pastor pastor : daoFactory.getPastorDAO().findAll()) {
            cmbMainSpeaker.addItem(pastor.getFirstName() + " " + pastor.getLastName());
        }
    }
    
    private void clear() {
        cmbEventType.setSelectedIndex(0);
        cmbMainSpeaker.setSelectedIndex(0);
        txtStartTime.setText(null);
        txtEndTime.setText(null);
        startDate.setDate(null);
        endDate.setDate(null);
        txtTheme.setText(null);
        txtEventCost.setText(null);
        txtAttendance.setText(null);
        cmbStatus.setSelectedIndex(0);
        setEventCode();
    }
    
    private void save() {
        if (startDate.getDate().after(endDate.getDate())) {
            JOptionPane.showMessageDialog(null, "End date should be greater or equal to the starting date");
        } else {
            EventDetail event = new EventDetail();
            if (daoFactory.getEventDetailDAO().findByEventCode(txtEventCode.getText()) != null) {
                event = daoFactory.getEventDetailDAO().findByEventCode(txtEventCode.getText());
            }
            event.setEventType(daoFactory.getEventTypeDAO().findByDesignation((String) cmbEventType.getSelectedItem()));
            event.setMainSpeaker((String) cmbMainSpeaker.getSelectedItem());
            event.setEventCode(txtEventCode.getText());
            event.setStartTime(txtStartTime.getText());
            event.setEndTime(txtEndTime.getText());
            event.setStartDate(startDate.getDate());
            event.setEndDate(endDate.getDate());
            event.setTheme(txtTheme.getText());
            int cost = 0;
            int attendance = 0;
            try {
                cost = Integer.parseInt(txtEventCost.getText());
                attendance = Integer.parseInt(txtAttendance.getText());
            } catch (Exception ex) {
                cost = 0;
                attendance = 0;
            }
            
            event.setEventCost(cost);
            event.setExpectedAttendance(attendance);
            event.setEventStatus(((String) cmbStatus.getSelectedItem()).charAt(0));
            Notifier.notifier(daoFactory.getEventDetailDAO().saveOrUpdateEventDetail(event));
        }
    }
    
    private void search() {
        if (!txtEventCode.getText().isEmpty()) {
            try {
                display(daoFactory.getEventDetailDAO().findByEventCode(txtEventCode.getText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Not Results Found");
            }
            txtEventCode.setEditable(false);
            txtEventCode.setBackground(new Color(153, 153, 153));
        } else {
            JOptionPane.showMessageDialog(null, "Enter the event Code and Hit Search Again", "Notification Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void display(EventDetail e) {
        for (int i = 0; i < cmbEventType.getItemCount(); i++) {
            if (e.getEventType().getDesignation().equals((String) cmbEventType.getItemAt(i))) {
                cmbEventType.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < cmbMainSpeaker.getItemCount(); i++) {
            if (e.getMainSpeaker().equals((String) cmbMainSpeaker.getItemAt(i))) {
                cmbMainSpeaker.setSelectedIndex(i);
                break;
            }
        }
        int index = (e.getEventStatus() == 'P') ? 0 : 1;
        cmbStatus.setSelectedIndex(index);
        txtStartTime.setText(e.getStartTime());
        txtEndTime.setText(e.getEndTime());
        startDate.setDate(e.getStartDate());
        endDate.setDate(e.getEndDate());
        txtTheme.setText(e.getTheme());
        txtEventCost.setText("" + e.getEventCost());
        txtAttendance.setText("" + e.getExpectedAttendance());
        txtEventCode.setText(e.getEventCode());
        
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
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbEventType = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cmbMainSpeaker = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtStartTime = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEndTime = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        startDate = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        endDate = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        txtTheme = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtEventCost = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtAttendance = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        txtEventCode = new javax.swing.JFormattedTextField();

        setClosable(true);
        setTitle("Event");

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Event Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 102, 0)));

        jLabel2.setText("Type of Event:");

        cmbEventType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Main Speaker:");

        cmbMainSpeaker.setEditable(true);
        cmbMainSpeaker.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Start Time:");

        try {
            txtStartTime.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##h##min")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel5.setText("End Time:");

        try {
            txtEndTime.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##h##min")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel6.setText("Start Date:");

        startDate.setDateFormatString("dd/MM/yyyy");

        jLabel7.setText("End Date:");

        endDate.setDateFormatString("dd/MM/yyyy");

        jLabel8.setText("Theme:");

        jLabel9.setText("Event Cost:");

        txtEventCost.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtEventCostCaretUpdate(evt);
            }
        });

        jLabel10.setText("Expected Attendance:");

        txtAttendance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAttendanceActionPerformed(evt);
            }
        });

        jLabel11.setText("Event Status:");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pending", "Held" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEndTime))
                            .addComponent(cmbEventType, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbMainSpeaker, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txtTheme, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtEventCost, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAttendance, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbEventType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMainSpeaker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtEventCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAttendance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("Event Code:");

        txtEventCode.setBackground(new java.awt.Color(204, 204, 204));
        txtEventCode.setEditable(false);
        try {
            txtEventCode.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("EV###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(595, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtEventCode, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEventCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    clear();
}//GEN-LAST:event_jButton1ActionPerformed
    
private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    save();
}//GEN-LAST:event_jButton2ActionPerformed
    
private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    txtEventCode.setEditable(true);
    txtEventCode.setText("");
    txtEventCode.requestFocus();
    txtEventCode.setBackground(Color.WHITE);
}//GEN-LAST:event_jButton3ActionPerformed
    
private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    search();
}//GEN-LAST:event_jButton4ActionPerformed
    
private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    this.dispose();
}//GEN-LAST:event_jButton6ActionPerformed
    
private void txtEventCostCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtEventCostCaretUpdate
    if (!txtEventCost.getText().isEmpty()) {
        try {
            Integer.parseInt(txtEventCost.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Enter event cost as a number please", "A number Required", JOptionPane.WARNING_MESSAGE);
        }
    }
}//GEN-LAST:event_txtEventCostCaretUpdate
    
private void txtAttendanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAttendanceActionPerformed
    if (!txtAttendance.getText().isEmpty()) {
        try {
            Integer.parseInt(txtAttendance.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Enter event cost as a number please", "A number Required", JOptionPane.WARNING_MESSAGE);
        }
    }
}//GEN-LAST:event_txtAttendanceActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbEventType;
    private javax.swing.JComboBox cmbMainSpeaker;
    private javax.swing.JComboBox cmbStatus;
    private com.toedter.calendar.JDateChooser endDate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private com.toedter.calendar.JDateChooser startDate;
    private javax.swing.JTextField txtAttendance;
    private javax.swing.JFormattedTextField txtEndTime;
    private javax.swing.JFormattedTextField txtEventCode;
    private javax.swing.JTextField txtEventCost;
    private javax.swing.JFormattedTextField txtStartTime;
    private javax.swing.JTextField txtTheme;
    // End of variables declaration//GEN-END:variables
}
