/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InvitedRow.java
 *
 * Created on May 10, 2012, 12:33:51 PM
 */
package orm.dao.Impl.GUI;

import java.util.Date;
import javax.swing.JComboBox;
import orm.Christian;
import orm.DAOFactory;
import orm.EventDetail;
import orm.EventInvited;

/**
 *
 * @author jean pierre
 */
public class InvitedRow extends javax.swing.JPanel {

    private DAOFactory daoFactory = DAOFactory.getInstance();

    /** Creates new form InvitedRow */
    public InvitedRow() {
        initComponents();
        setInvited();
        setEvents();
    }

    private void setInvited() {
        cmbInvited.removeAllItems();
        for (Christian c : daoFactory.getChristianDAO().findAll()) {
            int age = ((new Date()).getYear() - (c.getDateOfBirth()).getYear());
//            if (c.getIsActive() == 'Y' && age > 7) {
//                cmbInvited.addItem(c.getFullName());
//            }
            if (age > 7) {
                cmbInvited.addItem(c.getFullName());
            }
        }
    }

    private void setEvents() {
        cmbEvent.removeAllItems();
        for (EventDetail e : daoFactory.getEventDetailDAO().findAll()) {
            cmbEvent.addItem(e.getEventCode());
        }
    }

    public void setSelected(EventInvited e) {
        for (int i = 0; i < cmbEvent.getItemCount(); i++) {
            if (e.getEventDetail().getEventCode().equals((String) cmbEvent.getItemAt(i))) {
                cmbEvent.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < cmbInvited.getItemCount(); i++) {
            if (e.getChristian().getFullName().equals((String) cmbInvited.getItemAt(i))) {
                cmbInvited.setSelectedIndex(i);
                break;
            }
        }
        int index = (e.getAttended() == 'N') ? 0 : 1;
        cmbAttended.setSelectedIndex(index);
    }

    public JComboBox getCmbAttended() {
        return cmbAttended;
    }

    public JComboBox getCmbEvent() {
        return cmbEvent;
    }

    public JComboBox getCmbInvited() {
        return cmbInvited;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbInvited = new javax.swing.JComboBox();
        cmbEvent = new javax.swing.JComboBox();
        cmbAttended = new javax.swing.JComboBox();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cmbInvited.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbEvent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEventActionPerformed(evt);
            }
        });

        cmbAttended.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(cmbInvited, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbAttended, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cmbInvited, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbAttended, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void cmbEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEventActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_cmbEventActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbAttended;
    private javax.swing.JComboBox cmbEvent;
    private javax.swing.JComboBox cmbInvited;
    // End of variables declaration//GEN-END:variables
}