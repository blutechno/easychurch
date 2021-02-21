/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OffertoryReportsGUI.java
 *
 * Created on May 14, 2012, 11:17:43 AM
 */
package orm.dao.Impl.GUI;

import java.io.File;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.view.JasperViewer;
import orm.DAOFactory;
import orm.OffertoryType;

/**
 *
 * @author jean pierre
 */
public class OffertoryReportsGUI extends javax.swing.JInternalFrame {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private String church;
    /** Creates new form OffertoryReportsGUI */
    public OffertoryReportsGUI() {
        initComponents();
        church = daoFactory.getParishDAO().findAll().get(0).getParishName();
    }
    
    private void setOffertories() {
        cmbOffertory.removeAllItems();
        for (OffertoryType type : daoFactory.getOffertoryTypeDAO().findAll()) {
            cmbOffertory.addItem(type.getOffertoryTypeName());
        }
    }

    private void printout() {
        HashMap parameters = new HashMap();
        PathFinder finder = new PathFinder();
        String path = "";
        if (offertoryChecked.isSelected() && datesChecked.isSelected()) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("offertoryType", (String) cmbOffertory.getSelectedItem());
            parameters.put("date1", dateFrom.getDate());
            parameters.put("date2", dateTo.getDate());
            //path = finder.pathFinder("reports/offertoryByDateAndType.jasper");
            path = "C:/e_church_reports/offertoryByDateAndType.jasper";
            reportPreviewer(path, parameters);
        } else if (!offertoryChecked.isSelected() && datesChecked.isSelected()) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("date1", dateFrom.getDate());
            parameters.put("date2", dateTo.getDate());
            //path = finder.pathFinder("reports/offertoryByDates.jasper");
            path = "C:/e_church_reports/offertoryByDates.jasper";
            reportPreviewer(path, parameters);
        } else if (offertoryChecked.isSelected() && !datesChecked.isSelected()) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("offertoryType", (String) cmbOffertory.getSelectedItem());
            //path = finder.pathFinder("reports/offertoryType.jasper");
            path = "C:/e_church_reports/offertoryType.jasper";
            reportPreviewer(path, parameters);
        }
    }

    private void excellExport() {
        HashMap parameters = new HashMap();
        PathFinder finder = new PathFinder();
        String path = "";
        if (offertoryChecked.isSelected() && datesChecked.isSelected()) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("offertoryType", (String) cmbOffertory.getSelectedItem());
            parameters.put("date1", dateFrom.getDate());
            parameters.put("date2", dateTo.getDate());
            //path = finder.pathFinder("reports/offertoryByDateAndType.jasper");
            path = "C:/e_church_reports/offertoryByDateAndType.jasper";
        } else if (!offertoryChecked.isSelected() && datesChecked.isSelected()) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("date1", dateFrom.getDate());
            parameters.put("date2", dateTo.getDate());
            //path = finder.pathFinder("reports/offertoryByDates.jasper");
            path = "C:/e_church_reports/offertoryByDates.jasper";
        } else if (offertoryChecked.isSelected() && !datesChecked.isSelected()) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("offertoryType", (String) cmbOffertory.getSelectedItem());
            //path = finder.pathFinder("reports/offertoryType.jasper");
            path = "C:/e_church_reports/offertoryType.jasper";
        }
        String file = "";

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fc.showSaveDialog(this);
        File choice = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            choice = fc.getSelectedFile();
            file = choice.getAbsolutePath().toString();
            if (!file.endsWith(".xls")) {
                file = file + ".xls";
            }
            excellPrinter(path, parameters, file);
        }
    }

    private void excellPrinter(String path, HashMap parameters, String outputFile) {
        try {
            JasperPrint jp = JasperFillManager.fillReport(path, parameters, daoFactory.getConnection());
            JExcelApiExporter excelExporter = new JExcelApiExporter();
            excelExporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            excelExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile);
            excelExporter.exportReport();
            JOptionPane.showMessageDialog(null, "Operation Done Successfully", "Confirmation Message", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void reportPreviewer(String path, HashMap parameters) {
        try {
            JasperPrint jp = JasperFillManager.fillReport(path, parameters, daoFactory.getConnection());
            JasperViewer.viewReport(jp, false);
        } catch (Exception ex) {
            ex.printStackTrace();
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
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbOffertory = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        dateFrom = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        dateTo = new com.toedter.calendar.JDateChooser();
        datesChecked = new javax.swing.JCheckBox();
        offertoryChecked = new javax.swing.JCheckBox();

        setClosable(true);
        setTitle("Offertories Reports");

        jToolBar1.setBackground(new java.awt.Color(0, 0, 0));
        jToolBar1.setRollover(true);

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Preview and Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Export To Excel");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reporting Criteria", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 153, 0)));

        jLabel2.setText("Offertory Type");

        cmbOffertory.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbOffertoryItemStateChanged(evt);
            }
        });

        jLabel3.setText("Date From:");

        dateFrom.setDateFormatString("dd/MM/yyyy");

        jLabel1.setText("Date To:");

        dateTo.setDateFormatString("dd/MM/yyyy");

        datesChecked.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                datesCheckedItemStateChanged(evt);
            }
        });

        offertoryChecked.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                offertoryCheckedItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbOffertory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateTo, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(datesChecked))
                    .addComponent(offertoryChecked)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(cmbOffertory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(offertoryChecked))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(datesChecked)
                    .addComponent(jLabel3)
                    .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    printout();
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    this.dispose();
}//GEN-LAST:event_jButton6ActionPerformed

private void cmbOffertoryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbOffertoryItemStateChanged
}//GEN-LAST:event_cmbOffertoryItemStateChanged

private void offertoryCheckedItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_offertoryCheckedItemStateChanged
    if (offertoryChecked.isSelected()) {
        setOffertories();
    } else {
        cmbOffertory.removeAllItems();
    }
}//GEN-LAST:event_offertoryCheckedItemStateChanged

private void datesCheckedItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_datesCheckedItemStateChanged
    if (datesChecked.isSelected()) {
        dateFrom.setEnabled(true);
        dateTo.setEnabled(true);
    } else {
        dateFrom.setDate(null);
        dateTo.setDate(null);
        dateFrom.setEnabled(false);
        dateTo.setEnabled(false);
    }
}//GEN-LAST:event_datesCheckedItemStateChanged

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    excellExport();
}//GEN-LAST:event_jButton3ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbOffertory;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JCheckBox datesChecked;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JCheckBox offertoryChecked;
    // End of variables declaration//GEN-END:variables
}
