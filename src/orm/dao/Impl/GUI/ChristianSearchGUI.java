/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChristianSearchGUI.java
 *
 * Created on May 20, 2012, 8:52:42 PM
 */
package orm.dao.Impl.GUI;

import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import orm.Christian;
import orm.DAOFactory;

/**
 *
 * @author jean pierre
 */
public class ChristianSearchGUI extends javax.swing.JInternalFrame {

    private LoanGUI parWindow;
    private ChristianGUI christianWindow;
    private int currentWindow = 0;
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private Dimension parDimension;
    private Dimension christianDimension;

    /** Creates new form ChristianSearchGUI */
    public ChristianSearchGUI() {
        initComponents();
    }

    public void setParDimension(Dimension parDimension) {
        this.parDimension = parDimension;
    }

    public void setChristianDimension(Dimension christianDimension) {
        this.christianDimension = christianDimension;
    }

    public void setChristianWindow(ChristianGUI christianWindow) {
        this.christianWindow = christianWindow;
    }

    public void setCurrentWindow(int choice) {
        currentWindow = choice;
    }

    public void setParWindow(LoanGUI parWindow) {
        this.parWindow = parWindow;
    }

    private void getResult(String key) {
        try {
            List<Christian> result = daoFactory.getChristianDAO().findByFullName(key);
            Vector<String> tableHeaders = new Vector<String>();
            Vector tableData = new Vector();
            tableHeaders.add("First Name");
            tableHeaders.add("Last Name");
            tableHeaders.add("Christian PIN");

            for (Christian ch : result) {
                Vector<Object> oneRow = new Vector<Object>();
                oneRow.add(ch.getFirstName());
                oneRow.add(ch.getLastName());
                oneRow.add(ch.getChristianPin());
                tableData.add(oneRow);
            }
            table.setModel(new DefaultTableModel(tableData, tableHeaders));
        } catch (Exception ex) {
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

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        txtKey = new javax.swing.JTextField();

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Christian PIN", "First Name", "Last Name"
            }
        ));
        jScrollPane1.setViewportView(table);

        txtKey.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtKeyCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtKey, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(txtKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    if (currentWindow == 1) {
        parWindow.show();
        parWindow.setSize(parDimension);
        this.dispose();
    } else if (currentWindow == 2) {
        christianWindow.show();
        christianWindow.setSize(christianDimension);
        this.dispose();
    }
}//GEN-LAST:event_jButton2ActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    try {
        int row = table.getSelectedRow();
        if (currentWindow == 1) {
            this.parWindow.getTxtChristianPIN().setText((String) table.getValueAt(row, 2));
            this.parWindow.getTxtFirstName().setText((String) table.getValueAt(row, 0));
            this.parWindow.getTxtLastName().setText((String) table.getValueAt(row, 1));
            parWindow.show();
            parWindow.setSize(parDimension);
        } else if (currentWindow == 2) {
            String christianPin = (String) table.getValueAt(row, 2);
            Christian christian = daoFactory.getChristianDAO().findByChristianPin(christianPin);
            christianWindow.display(christian);
            christianWindow.show();
            christianWindow.setSize(christianDimension);
        }
        this.dispose();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Select a christian before you hit ok Please");
    }
}//GEN-LAST:event_jButton1ActionPerformed

private void txtKeyCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtKeyCaretUpdate
    if (!txtKey.getText().isEmpty()) {
        this.getResult(txtKey.getText());
    }
}//GEN-LAST:event_txtKeyCaretUpdate
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtKey;
    // End of variables declaration//GEN-END:variables
}
