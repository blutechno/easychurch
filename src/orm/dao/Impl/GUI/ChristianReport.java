/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChristianReport.java
 *
 * Created on May 16, 2012, 5:46:51 PM
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
import orm.Sacrement;
import orm.Zone;

/**
 *
 * @author jean pierre
 */
public class ChristianReport extends javax.swing.JInternalFrame {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private String church;

    /** Creates new form ChristianReport */
    public ChristianReport() {
        initComponents();
        church = daoFactory.getParishDAO().findAll().get(0).getParishName();
        setAgeFrom();
        setZone();
        setSacrament();
        enabler(0);
    }

    private void disabler() {
        cmbAgeFrom.setEnabled(false);
        cmbAgeTo.setEnabled(false);
        cmbGender.setEnabled(false);
        cmbZone.setEnabled(false);
        cmbEducation.setEnabled(false);
        cmbProfession.setEnabled(false);
        cmbSacrament.setEnabled(false);
        cmbMaritalStatus.setEnabled(false);
    }

    private void enabler(int index) {
        disabler();
        switch (index) {
            case 0:
                cmbAgeFrom.setEnabled(true);
                cmbAgeTo.setEnabled(true);
                break;
            case 1:
                cmbAgeFrom.setEnabled(true);
                cmbAgeTo.setEnabled(true);
                cmbGender.setEnabled(true);
                break;
            case 2:
                cmbGender.setEnabled(true);
                break;
            case 3:
                cmbZone.setEnabled(true);
                break;
            case 4:
                cmbEducation.setEnabled(true);
                break;
            case 5:
                cmbProfession.setEnabled(true);
                break;
            case 6:
                cmbSacrament.setEnabled(true);
                break;
            case 7:
                cmbMaritalStatus.setEnabled(true);
                break;
        }

    }

    private void setAgeFrom() {
        cmbAgeFrom.removeAllItems();
        for (int i = 0; i <= 120; i++) {
            cmbAgeFrom.addItem(i);
        }
    }

    private void setAgeTo(int begin) {
        cmbAgeTo.removeAllItems();
        for (int i = begin; i <= 120; i++) {
            cmbAgeTo.addItem(i);
        }
    }

    private void setZone() {
        cmbZone.removeAllItems();
        for (Zone zone : daoFactory.getZoneDAO().findAll()) {
            cmbZone.addItem(zone.getZoneName());
        }
    }

    private void setSacrament() {
        cmbSacrament.removeAllItems();
        for (Sacrement s : daoFactory.getSacrementDAO().findAll()) {
            cmbSacrament.addItem(s.getSacramentName());
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

    private void excellExport() {
        HashMap parameters = new HashMap();
        //PathFinder finder = new PathFinder();
        String path = "";

        if (cmbReportType.getSelectedIndex() == 0) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("range1", (Integer) cmbAgeFrom.getSelectedItem());
            parameters.put("range2", (Integer) cmbAgeTo.getSelectedItem());
            //path = finder.pathFinder("reports/christianByAgeRange.jasper");
            path = "C:/e_church_reports/christianByAgeRange.jasper";
        } else if (cmbReportType.getSelectedIndex() == 1) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("range1", (Integer) cmbAgeFrom.getSelectedItem());
            parameters.put("range2", (Integer) cmbAgeTo.getSelectedItem());
            parameters.put("gender", ((String) cmbGender.getSelectedItem()).substring(0, 1));
            //path = finder.pathFinder("reports/christianByAgeRangeAndGender.jasper");
            path = "C:/e_church_reports/christianByAgeRangeAndGender.jasper";
            reportPreviewer(path, parameters);
        } else if (cmbReportType.getSelectedIndex() == 2) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("gender", ((String) cmbGender.getSelectedItem()).substring(0, 1));
            //path = finder.pathFinder("reports/christianByGender.jasper");
            path = "C:/e_church_reports/christianByGender.jasper";
        } else if (cmbReportType.getSelectedIndex() == 3) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("zoneName", (String) cmbZone.getSelectedItem());
            //path = finder.pathFinder("reports/christianByZone.jasper");
            path = "C:/e_church_reports/christianByZone.jasper";
        } else if (cmbReportType.getSelectedIndex() == 4) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("education", (String) cmbEducation.getSelectedItem());
            //path = finder.pathFinder("reports/christianByEducationLevel.jasper");
            path = "C:/e_church_reports/christianByEducationLevel.jasper";
        } else if (cmbReportType.getSelectedIndex() == 5) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("profession", (String) cmbProfession.getSelectedItem());
            //path = finder.pathFinder("reports/christianByProfession.jasper");
            path = "C:/e_church_reports/christianByProfession.jasper";
        } else if (cmbReportType.getSelectedIndex() == 6) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("sacramentName", (String) cmbSacrament.getSelectedItem());
            //path = finder.pathFinder("reports/christianBySacrement.jasper");
            path = "C:/e_church_reports/christianBySacrement.jasper";
        } else if (cmbReportType.getSelectedIndex() == 7) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("maritalStatus", ((String) cmbMaritalStatus.getSelectedItem()).substring(0, 1));
            path = "C:/e_church_reports/christianByMaritalStatus.jasper";
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
            //excellPrinter(path, parameters, file);
            ReportPrinter.excellPrinter(path, parameters, file);
        }
    }

    private void printout() {
        HashMap parameters = new HashMap();
        //PathFinder finder = new PathFinder();
        String path = "";
        if (cmbReportType.getSelectedIndex() == 0) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("range1", (Integer) cmbAgeFrom.getSelectedItem());
            parameters.put("range2", (Integer) cmbAgeTo.getSelectedItem());
            //path = finder.pathFinder("reports/christianByAgeRange.jasper");
            path = "C:/e_church_reports/christianByAgeRange.jasper";
            //reportPreviewer(path, parameters);
            ReportPrinter.reportPreviewer(path, parameters);
        } else if (cmbReportType.getSelectedIndex() == 1) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("range1", (Integer) cmbAgeFrom.getSelectedItem());
            parameters.put("range2", (Integer) cmbAgeTo.getSelectedItem());
            parameters.put("gender", ((String) cmbGender.getSelectedItem()).substring(0, 1));
            //path = finder.pathFinder("reports/christianByAgeRangeAndGender.jasper");
            path = "C:/e_church_reports/christianByAgeRangeAndGender.jasper";
            //reportPreviewer(path, parameters);
            ReportPrinter.reportPreviewer(path, parameters);
        } else if (cmbReportType.getSelectedIndex() == 2) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("gender", ((String) cmbGender.getSelectedItem()).substring(0, 1));
            //path = finder.pathFinder("reports/christianByGender.jasper");
            path = "C:/e_church_reports/christianByGender.jasper";
            //reportPreviewer(path, parameters);
            ReportPrinter.reportPreviewer(path, parameters);
        } else if (cmbReportType.getSelectedIndex() == 3) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("zoneName", (String) cmbZone.getSelectedItem());
            //path = finder.pathFinder("reports/christianByZone.jasper");
            path = "C:/e_church_reports/christianByZone.jasper";
            //reportPreviewer(path, parameters);
            ReportPrinter.reportPreviewer(path, parameters);
        } else if (cmbReportType.getSelectedIndex() == 4) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("education", (String) cmbEducation.getSelectedItem());
            //path = finder.pathFinder("reports/christianByEducationLevel.jasper");
            path = "C:/e_church_reports/christianByEducationLevel.jasper";
            //reportPreviewer(path, parameters);
            ReportPrinter.reportPreviewer(path, parameters);
        } else if (cmbReportType.getSelectedIndex() == 5) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("profession", (String) cmbProfession.getSelectedItem());
            path = "C:/e_church_reports/christianByProfession.jasper";
            //reportPreviewer(path, parameters);
            ReportPrinter.reportPreviewer(path, parameters);
        } else if (cmbReportType.getSelectedIndex() == 6) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("sacramentName", (String) cmbSacrament.getSelectedItem());
            //path = finder.pathFinder("reports/christianBySacrement.jasper");
            path = "C:/e_church_reports/christianBySacrement.jasper";
            //reportPreviewer(path, parameters);
            ReportPrinter.reportPreviewer(path, parameters);
        } else if (cmbReportType.getSelectedIndex() == 7) {
            parameters.clear();
            parameters.put("church", church);
            parameters.put("maritalStatus", ((String) cmbMaritalStatus.getSelectedItem()).substring(0, 1));
           // path = finder.pathFinder("reports/christianByMaritalStatus.jasper");
            path = "C:/e_church_reports/christianByMaritalStatus.jasper";
            //reportPreviewer(path, parameters);
            ReportPrinter.reportPreviewer(path, parameters);
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
        jLabel1 = new javax.swing.JLabel();
        cmbReportType = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbAgeFrom = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cmbAgeTo = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        cmbGender = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cmbZone = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        cmbEducation = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cmbProfession = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        cmbSacrament = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        cmbMaritalStatus = new javax.swing.JComboBox();

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

        jLabel1.setText("Christian Report:");

        cmbReportType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "By Age Range", "By Age Range and Gender", "By Gender", "By Zone", "By Educational Level", "By Profession", "By Sacrament", "By Marital Status" }));
        cmbReportType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbReportTypeItemStateChanged(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Report Parameters", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 102, 0)));

        jLabel2.setText("Age From:");

        cmbAgeFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbAgeFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbAgeFromItemStateChanged(evt);
            }
        });

        jLabel3.setText("Age To:");

        cmbAgeTo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Gender:");

        cmbGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));

        jLabel5.setText("Zone:");

        cmbZone.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Education:");

        cmbEducation.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Primary School", "Secondary School", "Bachelor's Degree", "Masters Degree", "Doctorate", "Ph.D", "Professor", "None" }));

        jLabel7.setText("Profession:");

        cmbProfession.setEditable(true);
        cmbProfession.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Student", "Teacher", "Public Service", "Police", "Driver", "Carpenter", "Mason", "Military", "Businessman", "Private Entrepreneur", "Teacher" }));

        jLabel8.setText("Sacrament:");

        cmbSacrament.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("M.Status:");

        cmbMaritalStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Single", "Married", "Divorcee", "Widow(er)" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel5))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmbZone, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbAgeFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbAgeTo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbGender, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbEducation, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbProfession, 0, 159, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbMaritalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbSacrament, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(38, 38, 38))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbAgeFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cmbAgeTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbZone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmbEducation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cmbProfession, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmbSacrament, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(cmbMaritalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbReportType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbReportType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    printout();
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    excellExport();
}//GEN-LAST:event_jButton3ActionPerformed

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    this.dispose();
}//GEN-LAST:event_jButton6ActionPerformed

private void cmbAgeFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbAgeFromItemStateChanged
    try {
        int index = (Integer) cmbAgeFrom.getSelectedItem();
        setAgeTo(index);
    } catch (Exception ex) {
    }

}//GEN-LAST:event_cmbAgeFromItemStateChanged

private void cmbReportTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbReportTypeItemStateChanged
    try {
        enabler(cmbReportType.getSelectedIndex());
    } catch (Exception ex) {
    }
}//GEN-LAST:event_cmbReportTypeItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbAgeFrom;
    private javax.swing.JComboBox cmbAgeTo;
    private javax.swing.JComboBox cmbEducation;
    private javax.swing.JComboBox cmbGender;
    private javax.swing.JComboBox cmbMaritalStatus;
    private javax.swing.JComboBox cmbProfession;
    private javax.swing.JComboBox cmbReportType;
    private javax.swing.JComboBox cmbSacrament;
    private javax.swing.JComboBox cmbZone;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
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
    // End of variables declaration//GEN-END:variables
}
