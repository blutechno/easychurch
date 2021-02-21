/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AccountingReportGUI.java
 *
 * Created on May 28, 2012, 3:16:10 PM
 */
package orm.dao.Impl.GUI;

import java.io.File;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import orm.DAOFactory;
import orm.IncomeType;
import orm.OutflowType;

/**
 *
 * @author jean pierre
 */
public class AccountingReportGUI extends javax.swing.JInternalFrame {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private String church;

    /**
     * Creates new form AccountingReportGUI
     */
    public AccountingReportGUI() {
        initComponents();
        church = daoFactory.getParishDAO().findAll().get(0).getParishName();
        cmbType.setEnabled(false);
    }

    private String printToExcel() {
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
        }
        return file;
    }

    private void print(int choice, int index, int type) {
        boolean go = true;
        if (index == 0 || index == 2) {
            if (dateFrom.getDate() == null || dateTo.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Both date to and from should be set to continue");
                go = false;
            } else {
                if (dateFrom.getDate().after(dateTo.getDate())) {
                    JOptionPane.showMessageDialog(null, "Date To should be greater or to to date From");
                    go = false;
                }
            }
        }

        if (go) {
            HashMap parameters = new HashMap();
            PathFinder finder = new PathFinder();
            String path = "";
            parameters.clear();
            if (choice == 0) {
                if (index == 0) {
                    parameters.put("church", church);
                    parameters.put("date1", dateFrom.getDate());
                    parameters.put("date2", dateTo.getDate());
                    //path = finder.pathFinder("reports/periodicalIncomeReport.jasper");
                    path = "C:/e_church_reports/periodicalIncomeReport.jasper";
                } else if (index == 1) {
                    parameters.put("church", church);
                    parameters.put("income_type", (String) cmbType.getSelectedItem());
                    //path = finder.pathFinder("reports/incomeByTypeReport.jasper");
                    path = "C:/e_church_reports/incomeByTypeReport.jasper";
                } else if (index == 2) {
                    parameters.put("church", church);
                    parameters.put("date1", dateFrom.getDate());
                    parameters.put("date2", dateTo.getDate());
                    parameters.put("income_type", (String) cmbType.getSelectedItem());
                    //path = finder.pathFinder("reports/incomeByTypeAndPeriodReport.jasper");
                    path = "C:/e_church_reports/incomeByTypeAndPeriodReport.jasper";
                } else if (index == 3) {
                    parameters.put("church", church);
                    //path = finder.pathFinder("reports/yearIncomeReport.jasper");
                    path = "C:/e_church_reports/yearIncomeReport.jasper";
                }
            } else if (choice == 1) {
                if (index == 0) {
                    parameters.put("church", church);
                    parameters.put("date1", dateFrom.getDate());
                    parameters.put("date2", dateTo.getDate());
                    //path = finder.pathFinder("reports/periodicalExpense.jasper");
                    path = "C:/e_church_reports/periodicalExpense.jasper";
                } else if (index == 1) {
                    parameters.put("church", church);
                    parameters.put("outflow_type", (String) cmbType.getSelectedItem());
                    //path = finder.pathFinder("reports/expensesByType.jasper");
                    path = "C:/e_church_reports/expensesByType.jasper";
                } else if (index == 2) {
                    parameters.put("church", church);
                    parameters.put("date1", dateFrom.getDate());
                    parameters.put("date2", dateTo.getDate());
                    parameters.put("outflow_type", (String) cmbType.getSelectedItem());
                    //path = finder.pathFinder("reports/expenseByTypeAndPeriodReport.jasper");
                    path = "C:/e_church_reports/expenseByTypeAndPeriodReport.jasper";
                } else if (index == 3) {
                    parameters.put("church", church);
                    //path = finder.pathFinder("reports/yearExpensesReport.jasper");
                    path = "C:/e_church_reports/yearExpensesReport.jasper";
                }
            } else {
                parameters.put("church", church);
                parameters.put("date1", dateFrom.getDate());
                parameters.put("date2", dateTo.getDate());
                //path = finder.pathFinder("reports/incomeStatement.jasper");
                path = "C:/e_church_reports/incomeStatement.jasper";
            }
            if (type == 0) {
                ReportPrinter.reportPreviewer(path, parameters);
            } else if (type == 1) {
                ReportPrinter.excellPrinter(path, parameters, printToExcel());
            }
        }
    }

    private void setDates(boolean b) {
        if (b) {
            dateFrom.setEnabled(true);
            dateTo.setEnabled(true);
        } else {
            dateTo.setDate(null);
            dateTo.setEnabled(false);
            dateFrom.setDate(null);
            dateFrom.setEnabled(false);
        }
    }

    private void setType(boolean b, int choice) {
        if (b && choice == 0) {
            cmbType.setEnabled(true);
            cmbType.removeAllItems();
            for (IncomeType t : daoFactory.getIncomeTypeDAO().findAll()) {
                cmbType.addItem(t.getIncomeType());
            }
        } else if (b && choice == 1) {
            cmbType.setEnabled(true);
            cmbType.removeAllItems();
            for (OutflowType t : daoFactory.getOutflowTypeDAO().findAll()) {
                cmbType.addItem(t.getOutflowTypeName());
            }
        } else {
            cmbType.removeAllItems();
            cmbType.setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnPrint = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cmbReport = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cmbCriteria = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        dateFrom = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        dateTo = new com.toedter.calendar.JDateChooser();
        lblMessage = new javax.swing.JLabel();
        cmbType = new javax.swing.JComboBox();

        setClosable(true);
        setTitle("Accounting Reports");

        jToolBar1.setBackground(new java.awt.Color(0, 0, 0));
        jToolBar1.setRollover(true);

        btnPrint.setBackground(new java.awt.Color(0, 0, 0));
        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setText("Print");
        btnPrint.setFocusable(false);
        btnPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPrint);

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Export to Excel");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

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

        jLabel1.setText("Report Type:");

        cmbReport.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Income", "Expenses", "Income and Expenses" }));
        cmbReport.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbReportItemStateChanged(evt);
            }
        });

        jLabel2.setText("Reporting Criteria");

        cmbCriteria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "By Period", "By Type", "By Period and Type", "All Passed Period" }));
        cmbCriteria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCriteriaItemStateChanged(evt);
            }
        });

        jLabel3.setText("Date From:");

        dateFrom.setDateFormatString("dd/MM/yyyy");

        jLabel4.setText("Date To:");

        dateTo.setDateFormatString("dd/MM/yyyy");

        lblMessage.setText("income Type:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(dateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbReport, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbCriteria, 0, 185, Short.MAX_VALUE))
                .addGap(22, 22, 22))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(184, Short.MAX_VALUE)
                .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(141, 141, 141))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cmbCriteria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMessage)
                    .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
    print(cmbReport.getSelectedIndex(), cmbCriteria.getSelectedIndex(), 0);
}//GEN-LAST:event_btnPrintActionPerformed

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    this.dispose();
}//GEN-LAST:event_jButton6ActionPerformed

private void cmbReportItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbReportItemStateChanged
    cmbType.removeAllItems();
    if (cmbReport.getSelectedIndex() == 0) {
        lblMessage.setText("Income Type: ");
    } else if (cmbReport.getSelectedIndex() == 1) {
        lblMessage.setText("Expenses Type:");
    } else {
        lblMessage.setText("None");
        cmbCriteria.setSelectedIndex(0);
        cmbType.removeAllItems();
    }
}//GEN-LAST:event_cmbReportItemStateChanged

private void cmbCriteriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCriteriaItemStateChanged
    int choice = cmbReport.getSelectedIndex();
    int index = cmbCriteria.getSelectedIndex();
    boolean enDates = choice == 2 ? true : (index == 0 ? true : (index == 2 ? true : false));
    boolean enType = choice < 2 ? true : false;
    enType = (index == 1 ? true : (index == 2 ? true : false));
    setDates(enDates);
    setType(enType, choice);
}//GEN-LAST:event_cmbCriteriaItemStateChanged

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    print(cmbReport.getSelectedIndex(), cmbCriteria.getSelectedIndex(), 1);
}//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JComboBox cmbCriteria;
    private javax.swing.JComboBox cmbReport;
    private javax.swing.JComboBox cmbType;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblMessage;
    // End of variables declaration//GEN-END:variables
}
