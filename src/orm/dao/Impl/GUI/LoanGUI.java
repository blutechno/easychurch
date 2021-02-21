/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LoanGUI.java
 *
 * Created on May 18, 2012, 10:03:00 AM
 */
package orm.dao.Impl.GUI;

import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import orm.Christian;
import orm.DAOFactory;
import orm.Loan;
import orm.LoanRefunding;
import orm.LoanType;
import orm.Notifier;

/**
 *
 * @author jean pierre
 */
public class LoanGUI extends javax.swing.JInternalFrame {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private String unpaidLoan = "";
    private FinanceModule module;
    private String church;
    private String cashier;

    /** Creates new form LoanGUI */
    public LoanGUI() {
        initComponents();
        church = daoFactory.getParishDAO().findAll().get(0).getParishName();
        setLoanType();
        btnPrint.setEnabled(false);
    }

    private boolean hasUnpaidLoan(String christianPin) {
        // boolean result = false;
        for (Loan l : daoFactory.getLoanDAO().findByChristianCode(christianPin)) {
            if (l.getRefundStatus() == 'N') {
                unpaidLoan = l.getLoanCode();
                return true;
                //break;
            }
        }
        return false;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    private void setLoanType() {
        cmbLoanType.removeAllItems();
        for (LoanType type : daoFactory.getLoanTypeDAO().findAll()) {
            cmbLoanType.addItem(type.getLoanTypeName());
        }
    }

    private void clear() {
        txtLoanCode.setEditable(false);
        txtLoanCode.setBackground(new Color(204, 204, 204));
        txtLoanCode.setText(null);
        lblLoanRefunded.setText(null);
        txtChristianPIN.setText(null);
        txtFirstName.setText(null);
        txtLastName.setText(null);
        cmbLoanType.setSelectedIndex(0);
        txtLoanAmount.setText(null);
        txtInterestRate.setText(null);
        txtRefundAmount.setText(null);
        deadlineDate.setDate(null);
    }

    private void save() {
        if (this.hasUnpaidLoan(txtChristianPIN.getText())) {
            JOptionPane.showMessageDialog(null, "The Christian has been given another loan which is not paid yet. See loand code: " + unpaidLoan);
        } else {
            unpaidLoan = "";
            if (!txtChristianPIN.getText().isEmpty()) {
                if (!txtLoanAmount.getText().isEmpty()) {
                    if (deadlineDate.getDate() != null) {
                        if (deadlineDate.getDate().after(new Date())) {
                            Loan loan = new Loan();
                            String loanCode = this.setLoanCode();
                            loan.setLoanCode(loanCode);
                            loan.setLoanType(daoFactory.getLoanTypeDAO().findByName((String) cmbLoanType.getSelectedItem()));
                            loan.setChristian(daoFactory.getChristianDAO().findByChristianPin(txtChristianPIN.getText()));
                            int amount = 0;
                            int rate = 0;
                            int refund = 0;
                            if (!txtInterestRate.getText().isEmpty()) {
                                amount = Integer.parseInt(txtLoanAmount.getText());
                                rate = Integer.parseInt(txtInterestRate.getText());
                                refund = Integer.parseInt(txtRefundAmount.getText());
                            } else {
                                rate = 0;
                                refund = Integer.parseInt(txtLoanAmount.getText());
                                amount = Integer.parseInt(txtLoanAmount.getText());
                            }
                            //
                            loan.setLoanDate(new Date());
                            loan.setLoanAmount(amount);
                            loan.setInterestRate(rate);
                            loan.setAmountToRepay(refund);
                            loan.setRefundStatus('N');
                            loan.setRefundDeadline(deadlineDate.getDate());
                            boolean done = daoFactory.getLoanDAO().saveOrUpdateLoan(loan);
                            if (done) {
                                loan = daoFactory.getLoanDAO().findByLoanCode(loanCode);
                                LoanRefunding refunding = new LoanRefunding();
                                refunding.setLoan(loan);
                                refunding.setRefundDate(new Date());
                                refunding.setPaidAmount(0);
                                refunding.setRemainAmount(loan.getAmountToRepay());
                                refunding.setDeposer("None");
                                refunding.setUser(daoFactory.getUserDAO().findByUsername(cashier));
                                daoFactory.getLoanRefundinDAO().saveOrUpdateLoanRefunding(refunding);
                                Notifier.notifier(done);
                                txtLoanCode.setText(loanCode);
                                btnPrint.setEnabled(true);
                            } else {
                                Notifier.notifier(done);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Loan Refund Deadline date should be strictly after today's date");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Loan Refund deadline date is required", "Deadline Required", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Loan Amount is required", "Amount Required", JOptionPane.WARNING_MESSAGE);
                    txtLoanAmount.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Loan Beneficiary is required", "Beneficiary Required", JOptionPane.WARNING_MESSAGE);
                txtChristianPIN.requestFocus();
            }
        }
    }

    private void search() {
        if (!txtLoanCode.getText().isEmpty()) {
            try {
                Loan loan = daoFactory.getLoanDAO().findByLoanCode(txtLoanCode.getText());
                txtLoanCode.setText(loan.getLoanCode());
                lblLoanRefunded.setText((loan.getRefundStatus() == 'N') ? "Non" : "Yes");
                txtChristianPIN.setText(loan.getChristian().getChristianPin());
                txtFirstName.setText(loan.getChristian().getFirstName());
                txtLastName.setText(loan.getChristian().getLastName());
                for (int i = 0; i < cmbLoanType.getItemCount(); i++) {
                    if (loan.getLoanType().getLoanTypeName().equals((String) cmbLoanType.getItemAt(i))) {
                        cmbLoanType.setSelectedIndex(i);
                        break;
                    }
                }
                txtLoanAmount.setText("" + loan.getLoanAmount());
                txtInterestRate.setText("" + loan.getInterestRate());
                txtRefundAmount.setText("" + loan.getAmountToRepay());
                deadlineDate.setDate(loan.getRefundDeadline());
                btnSave.setEnabled(false);
                btnPrint.setEnabled(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Loan code that you entered does not exist", "No result found", JOptionPane.ERROR_MESSAGE);
            }
            txtLoanCode.setEditable(false);
            txtLoanCode.setBackground(new Color(204, 204, 204));
        } else {
            JOptionPane.showMessageDialog(null, "Loan code required to retrieve related information", "Loan Code Required", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void setModule(FinanceModule module) {
        this.module = module;
    }

    public JTextField getTxtChristianPIN() {
        return txtChristianPIN;
    }

    public JTextField getTxtFirstName() {
        return txtFirstName;
    }

    public JTextField getTxtLastName() {
        return txtLastName;
    }

    private String setLoanCode() {
        try {
            int size = daoFactory.getLoanDAO().findAll().size();
            size=daoFactory.getLoanDAO().findAll().get(size-1).getLoanId();
            return String.format("L" + "%05d", (size + 1));
        } catch (Exception ex) {
            return "L0000" + 1;
        }
    }

    private void setAmountToRefund() {
        int loan = 0;
        int rate = 0;
        try {
            loan = Integer.parseInt(txtLoanAmount.getText());
            rate = Integer.parseInt(txtInterestRate.getText());
        } catch (Exception ex) {
        }
        int refund = loan + (loan * rate) / 100;
        txtRefundAmount.setText("" + refund);
    }

    private void print() {
        HashMap parameters = new HashMap();
        //PathFinder finder = new PathFinder();
        String path = "";
        parameters.clear();
        if (!txtLoanCode.getText().isEmpty()) {
            parameters.put("church", church);
            parameters.put("code", txtLoanCode.getText());
            path = "C:/e_church_reports/loanPrintout.jasper";
            ReportPrinter.reportPreviewer(path, parameters);
        } else {
            JOptionPane.showMessageDialog(null, "Loan Code Required to printout loan information");
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
        btnPrint = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtChristianPIN = new javax.swing.JTextField();
        txtFirstName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtRefundAmount = new javax.swing.JTextField();
        txtInterestRate = new javax.swing.JTextField();
        txtLoanAmount = new javax.swing.JTextField();
        cmbLoanType = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        deadlineDate = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtLoanCode = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblLoanRefunded = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Loan");

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

        btnSave.setBackground(new java.awt.Color(0, 0, 0));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Beneficiary Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 102, 0)));

        jButton7.setText("Search For Beneficiary");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel1.setText("Christian PIN:");

        jLabel2.setText("First Name:");

        jLabel3.setText("Last Name:");

        txtFirstName.setEditable(false);
        txtFirstName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        txtLastName.setEditable(false);
        txtLastName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jButton8.setText("Check Christian");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtChristianPIN, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                            .addComponent(txtLastName, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtChristianPIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Loan Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 102, 0)));

        jLabel4.setText("Loan Type:");

        jLabel5.setText("Loan Amount:");

        jLabel6.setText("Interest Rate:");

        jLabel7.setText("Amount To Refund:");

        txtRefundAmount.setEditable(false);
        txtRefundAmount.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        txtInterestRate.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtInterestRateCaretUpdate(evt);
            }
        });

        txtLoanAmount.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtLoanAmountCaretUpdate(evt);
            }
        });

        cmbLoanType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Refund Deadline:");

        deadlineDate.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbLoanType, javax.swing.GroupLayout.Alignment.TRAILING, 0, 253, Short.MAX_VALUE)
                    .addComponent(txtInterestRate, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLoanAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                    .addComponent(txtRefundAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                    .addComponent(deadlineDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(txtLoanAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtInterestRate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRefundAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbLoanType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(deadlineDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel8.setText("LOAN CODE:");

        txtLoanCode.setBackground(new java.awt.Color(204, 204, 204));
        txtLoanCode.setEditable(false);
        txtLoanCode.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtLoanCode.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel10.setText("LOAN REFUNDED:");

        lblLoanRefunded.setFont(new java.awt.Font("Tahoma", 1, 12));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLoanCode, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLoanRefunded, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(408, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addComponent(txtLoanCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblLoanRefunded, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 779, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    btnSave.setEnabled(true);
    btnPrint.setEnabled(false);
    clear();
}//GEN-LAST:event_jButton1ActionPerformed

private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
    save();
}//GEN-LAST:event_btnSaveActionPerformed

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    txtLoanCode.setEditable(true);
    txtLoanCode.setText(null);
    txtLoanCode.setBackground(Color.WHITE);
}//GEN-LAST:event_jButton3ActionPerformed

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    search();
}//GEN-LAST:event_jButton4ActionPerformed

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    this.dispose();
}//GEN-LAST:event_jButton6ActionPerformed

private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
    try {
        ChristianSearchGUI searchGUI = new ChristianSearchGUI();
        searchGUI.setParWindow(this);
        searchGUI.setParDimension(this.getSize());
        module.add(searchGUI);
        searchGUI.setCurrentWindow(1);
        searchGUI.show();
        this.setSize(0, 0);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}//GEN-LAST:event_jButton7ActionPerformed

private void txtInterestRateCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtInterestRateCaretUpdate
    if (!txtInterestRate.getText().isEmpty()) {
        if (txtInterestRate.getText().length() <= 2) {
            try {
                Integer.parseInt(txtInterestRate.getText());
                setAmountToRefund();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Enter interest rate as a number between 0 and 99 please");
                txtInterestRate.setText(null);
            }
        } else {
            try {
                JOptionPane.showMessageDialog(null, "Interest Rate should not go belong 99");
            } catch (Exception ex) {
                txtInterestRate.setText(null);
            }

        }
    }
}//GEN-LAST:event_txtInterestRateCaretUpdate

private void txtLoanAmountCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtLoanAmountCaretUpdate
    if (!txtLoanAmount.getText().isEmpty()) {
        try {
            Integer.parseInt(txtLoanAmount.getText());
            setAmountToRefund();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Enter Loan amount as a number please");
        }
    }
}//GEN-LAST:event_txtLoanAmountCaretUpdate

private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
    print();
}//GEN-LAST:event_btnPrintActionPerformed

private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
    if (!txtChristianPIN.getText().isEmpty()) {
        try {
            Christian christian = daoFactory.getChristianDAO().findByChristianPin(txtChristianPIN.getText());
            txtFirstName.setText(christian.getFirstName());
            txtLastName.setText(christian.getLastName());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Christian not found");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Christian not found");
    }
}//GEN-LAST:event_jButton8ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbLoanType;
    private com.toedter.calendar.JDateChooser deadlineDate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblLoanRefunded;
    private javax.swing.JTextField txtChristianPIN;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtInterestRate;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtLoanAmount;
    private javax.swing.JTextField txtLoanCode;
    private javax.swing.JTextField txtRefundAmount;
    // End of variables declaration//GEN-END:variables
}
