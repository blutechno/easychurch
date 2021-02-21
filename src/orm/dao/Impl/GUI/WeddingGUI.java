/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * WeddingGUI.java
 *
 * Created on May 9, 2012, 4:19:49 PM
 */
package orm.dao.Impl.GUI;

import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import orm.Christian;
import orm.DAOFactory;
import orm.Notifier;
import orm.Pastor;
import orm.Wedding;

/**
 *
 * @author jean pierre
 */
public class WeddingGUI extends javax.swing.JInternalFrame {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private String committedPartner = "";
    private String church;

    /** Creates new form WeddingGUI */
    public WeddingGUI() {
        initComponents();
        church = daoFactory.getParishDAO().findAll().get(0).getParishName();
        setHusband();
        setWife();
        setCelebrant();
        setWeddingCode();
    }

    private void setWeddingCode() {
        try {
            int size = daoFactory.getWeddingDAO().findAll().size();
            txtWeddingCode.setText(String.format("%04d", (size + 1)));
        } catch (Exception ex) {
            txtWeddingCode.setText("000" + 1);
        }
    }

    private void setCelebrant() {
        cmbCelebrant.removeAllItems();
        for (Pastor pastor : daoFactory.getPastorDAO().findAll()) {
            cmbCelebrant.addItem(pastor.getLastName() + " " + pastor.getFirstName());
        }
    }

    private String findCouple(String husband, String wife) {
        String result = "";
        for (Wedding w : daoFactory.getWeddingDAO().findAll()) {
            String h = w.getHusband();
            String wi = w.getWife();
            if (h.equalsIgnoreCase(husband) && wi.equalsIgnoreCase(wife)) {
                result = w.getWeddingCode();
                break;
            }
        }
        return result;
    }

    private boolean stillCommitted(String husband, String wife) {
        boolean committed = false;
        for (Wedding w : daoFactory.getWeddingDAO().findAll()) {
            String h = w.getHusband();
            String wi = w.getWife();
            if (((husband.equalsIgnoreCase(h)) && (!wife.equalsIgnoreCase(wi))) && (w.getWeddingStatus() != 'S')) {
                committed = true;
                this.setCommittedPartner(h);
                break;
            } else {
                if (((!husband.equalsIgnoreCase(h)) && (wife.equalsIgnoreCase(wi))) && (w.getWeddingStatus() != 'S')) {
                    committed = true;
                    this.setCommittedPartner(wi);
                    break;
                }
            }
        }
        return committed;
    }

    public String getCommittedPartner() {
        return committedPartner;
    }

    public void setCommittedPartner(String committedPartner) {
        this.committedPartner = committedPartner;
    }

//        private boolean stillCommitted(String husband, String wife) {
//        boolean committed = false;
//        for (Wedding w : daoFactory.getWeddingDAO().findAll()) {
//            String h = w.getHusband();
//            String wi = w.getWife();
//            if ((((husband.equalsIgnoreCase(h)) && (!wife.equalsIgnoreCase(wi))) || ((!husband.equalsIgnoreCase(h)) && (wife.equalsIgnoreCase(wi)))) && (w.getWeddingStatus() != 'S')) {
//                committed = true;
//                break;
//            }
//        }
//        return committed;
//    }
    private void setHusband() {
        cmbHusband.removeAllItems();
        for (Christian christian : daoFactory.getChristianDAO().findByGender('M')) {
            int age = ((new Date().getYear()) - (christian.getDateOfBirth().getYear()));
            if (christian.getMaritalStatus() != 'M' && (age >= 18)) {
                cmbHusband.addItem(christian.getFullName());
            }
        }
    }

    private void setWife() {
        cmbWife.removeAllItems();
        for (Christian christian : daoFactory.getChristianDAO().findByGender('F')) {
            int age = ((new Date().getYear()) - (christian.getDateOfBirth().getYear()));
            if (christian.getMaritalStatus() != 'M' && (age >= 18)) {
                cmbWife.addItem(christian.getFullName());
            }
        }
    }

    private void clear() {
        cmbHusband.setSelectedIndex(0);
        cmbWife.setSelectedIndex(0);
        cmbCelebrant.setSelectedIndex(0);
        cmbWeddingStatus.setSelectedIndex(0);
        txtWeddingFees.setText(null);
        txtCautionFees.setText(null);
        weddingDate.setDate(null);
        cautionPaid.setSelected(false);
        txtHusbandGodFather.setText(null);
        txtWifeGodMother.setText(null);
        weddingFeesPaid.setSelected(false);
        cautionRefunded.setSelected(false);
        txtTime.setText(null);
        setWeddingCode();
        btnPrint.setEnabled(false);
    }

    private void print() {
        HashMap parameters = new HashMap();
        //PathFinder finder = new PathFinder();
        String path = "";
        parameters.clear();
        parameters.put("church", church);
        parameters.put("wedding_code", txtWeddingCode.getText());
        path = "C:/e_church_reports/weddingBill.jasper";

        ReportPrinter.reportPreviewer(path, parameters);
    }

    private void save() {
        String tempCode = this.findCouple((String) cmbHusband.getSelectedItem(), (String) cmbWife.getSelectedItem());
        if (!tempCode.isEmpty() && !tempCode.equalsIgnoreCase(txtWeddingCode.getText())) {
            JOptionPane.showMessageDialog(null, "The wedding of " + (String) cmbHusband.getSelectedItem() + " and " + (String) cmbWife.getSelectedItem() + " is already recorded. Use the following code if you want to update their information:" + tempCode, "Existing Wedding", JOptionPane.WARNING_MESSAGE);
        } else {
            if (this.stillCommitted((String) cmbHusband.getSelectedItem(), (String) cmbWife.getSelectedItem())) {
                JOptionPane.showMessageDialog(null, this.getCommittedPartner() + " is already engaged in other wedding and that wedding is still valid", "Existing Wedding", JOptionPane.WARNING_MESSAGE);
            } else {
                Wedding wedding = new Wedding();
                if (daoFactory.getWeddingDAO().findByWeddingCode(txtWeddingCode.getText()) != null) {
                    int choice = JOptionPane.showConfirmDialog(null, "This wedding is already recorded. Do you want to go on updating its information?", "Existing Wedding", JOptionPane.OK_CANCEL_OPTION);
                    if (choice == 0) {
                        wedding = daoFactory.getWeddingDAO().findByWeddingCode(txtWeddingCode.getText());
                        if (wedding.getWeddingStatus() == 'C') {
                            wedding.setCautionRefundedStatus(cautionRefunded.isSelected() ? 'Y' : 'N');
                        } else {
                            int weddingFees = 0;
                            int cautionFees = 0;
                            try {
                                weddingFees = Integer.parseInt(txtWeddingFees.getText());
                                cautionFees = Integer.parseInt(txtCautionFees.getText());
                            } catch (Exception ex) {
                                weddingFees = 0;
                                cautionFees = 0;
                            }
                            wedding.setCeremonyFees(weddingFees);
                            wedding.setWeddingCautionFees(cautionFees);
                            wedding.setWeddingDay(weddingDate.getDate());
                            Pastor pastor = new Pastor();
                            for (Pastor p : daoFactory.getPastorDAO().findAll()) {
                                String celebrant = p.getLastName() + " " + p.getFirstName();
                                if (celebrant.equals((String) cmbCelebrant.getSelectedItem())) {
                                    pastor = p;
                                    break;
                                }
                            }
                            wedding.setPastor(pastor);
                            wedding.setWeddingCautionPayStatus(cautionPaid.isSelected() ? 'Y' : 'N');
                            wedding.setHusbandGodFather(txtHusbandGodFather.getText());
                            wedding.setWifeGodMother(txtWifeGodMother.getText());
                            wedding.setWeddingFeesPayStatus(weddingFeesPaid.isSelected() ? 'Y' : 'N');
                            wedding.setCautionRefundedStatus(cautionRefunded.isSelected() ? 'Y' : 'N');
                            wedding.setWeddingTime(txtTime.getText());
                            wedding.setCreatedOn(new Date());
                            wedding.setWeddingStatus(((String) cmbWeddingStatus.getSelectedItem()).charAt(0));
                        }
                        Notifier.notifier(daoFactory.getWeddingDAO().saveOrUpdateWedding(wedding));
                    }
                } else {
                    int weddingFees = 0;
                    int cautionFees = 0;
                    try {
                        weddingFees = Integer.parseInt(txtWeddingFees.getText());
                        cautionFees = Integer.parseInt(txtCautionFees.getText());
                    } catch (Exception ex) {
                        weddingFees = 0;
                        cautionFees = 0;
                    }
                    wedding.setHusband((String) cmbHusband.getSelectedItem());
                    wedding.setWife((String) cmbWife.getSelectedItem());
                    wedding.setWeddingCode(txtWeddingCode.getText());
                    wedding.setCeremonyFees(weddingFees);
                    wedding.setWeddingCautionFees(cautionFees);
                    wedding.setWeddingDay(weddingDate.getDate());
                    Pastor pastor = new Pastor();
                    for (Pastor p : daoFactory.getPastorDAO().findAll()) {
                        String celebrant = p.getLastName() + " " + p.getFirstName();
                        if (celebrant.equals((String) cmbCelebrant.getSelectedItem())) {
                            pastor = p;
                            break;
                        }
                    }
                    wedding.setPastor(pastor);
                    wedding.setWeddingCautionPayStatus(cautionPaid.isSelected() ? 'Y' : 'N');
                    wedding.setHusbandGodFather(txtHusbandGodFather.getText());
                    wedding.setWifeGodMother(txtWifeGodMother.getText());
                    wedding.setWeddingFeesPayStatus(weddingFeesPaid.isSelected() ? 'Y' : 'N');
                    wedding.setCautionRefundedStatus(cautionRefunded.isSelected() ? 'Y' : 'N');
                    wedding.setWeddingTime(txtTime.getText());
                    wedding.setWeddingStatus(((String) cmbWeddingStatus.getSelectedItem()).charAt(0));
                    wedding.setCreatedOn(new Date());
                    Notifier.notifier(daoFactory.getWeddingDAO().saveOrUpdateWedding(wedding));
                }
            }
        }
    }

    private void search() {
        if (!txtWeddingCode.getText().isEmpty()) {
//            if (txtWeddingCode.getText().length() < 4) {
//                int index = Integer.parseInt(txtWeddingCode.getText());
//                txtWeddingCode.setText(this.codeRefactor(index));
//            }
            try {
                display(daoFactory.getWeddingDAO().findByWeddingCode(txtWeddingCode.getText()));
                btnPrint.setEnabled(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Not Results Found. Not Existing Wedding Code", "Notification", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try {
                display(daoFactory.getWeddingDAO().findByPartners((String) cmbHusband.getSelectedItem(), (String) cmbWife.getSelectedItem()));
                btnPrint.setEnabled(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Inexisting Couple. Try different combination", "Notification", JOptionPane.ERROR_MESSAGE);
            }
        }
        txtWeddingCode.setEditable(false);
        txtWeddingCode.setBackground(new Color(153, 153, 153));
    }

    private void display(Wedding w) {
        boolean isFound = false;
        //Setting christian parents' Names
        for (int i = 0; i < cmbHusband.getItemCount(); i++) {
            if (w.getHusband().equals((String) cmbHusband.getItemAt(i))) {
                isFound = true;
                cmbHusband.setSelectedIndex(i);
                break;
            }
        }
        if (!isFound) {
            cmbHusband.addItem(w.getHusband());
            cmbHusband.setSelectedIndex(cmbHusband.getItemCount() - 1);
        }

        isFound = false;

        for (int i = 0; i < cmbWife.getItemCount(); i++) {
            if (w.getWife().equals((String) cmbWife.getItemAt(i))) {
                isFound = true;
                cmbWife.setSelectedIndex(i);
                break;
            }
        }
        if (!isFound) {
            cmbWife.addItem(w.getHusband());
            cmbWife.setSelectedIndex(cmbWife.getItemCount() - 1);
        }
        if (w.getPastor() == null) {
            cmbCelebrant.setSelectedIndex(0);
        } else {
            for (int i = 0; i < cmbCelebrant.getItemCount(); i++) {
                String celebrant = w.getPastor().getLastName() + " " + w.getPastor().getFirstName();
                if (celebrant.equals((String) cmbCelebrant.getItemAt(i))) {
                    isFound = true;
                    cmbCelebrant.setSelectedIndex(i);
                    break;
                }
            }
        }
        int index = (w.getWeddingStatus() == 'P') ? 0 : ((w.getWeddingStatus() == 'C')) ? 1 : 2;
        cmbWeddingStatus.setSelectedIndex(index);
        txtWeddingFees.setText("" + w.getCeremonyFees());
        weddingFeesPaid.setSelected(w.getWeddingFeesPayStatus() == 'Y');
        txtCautionFees.setText("" + w.getWeddingCautionFees());
        cautionPaid.setSelected(w.getWeddingCautionPayStatus() == 'Y');
        cautionRefunded.setSelected(w.getCautionRefundedStatus() == 'Y');
        weddingDate.setDate(w.getWeddingDay());
        txtTime.setText(w.getWeddingTime());
        txtHusbandGodFather.setText(w.getHusbandGodFather());
        txtWifeGodMother.setText(w.getWifeGodMother());
        txtWeddingCode.setText(w.getWeddingCode());


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
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtWeddingCode = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbHusband = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txtHusbandGodFather = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbWife = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtWifeGodMother = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtWeddingFees = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        weddingFeesPaid = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        txtCautionFees = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cautionRefunded = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        cautionPaid = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        weddingDate = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        txtTime = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        cmbCelebrant = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        cmbWeddingStatus = new javax.swing.JComboBox();

        setClosable(true);
        setTitle("Wedding");

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
        btnPrint.setText("Print Bill");
        btnPrint.setFocusable(false);
        btnPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPrint);

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

        jLabel1.setText("Wedding Code:");

        txtWeddingCode.setBackground(new java.awt.Color(204, 204, 204));
        txtWeddingCode.setEditable(false);
        txtWeddingCode.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtWeddingCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWeddingCodeActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wedding", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(255, 0, 102)));

        jLabel2.setText("Husband:");

        cmbHusband.setEditable(true);
        cmbHusband.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Husband's God Father:");

        jLabel4.setText("Wife:");

        cmbWife.setEditable(true);
        cmbWife.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Wife's God Mother:");

        jLabel6.setText("Wedding Fees:");

        txtWeddingFees.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtWeddingFeesCaretUpdate(evt);
            }
        });

        jLabel7.setText("Wedding Fees Paid:");

        weddingFeesPaid.setEnabled(false);

        jLabel8.setText("Caution Fees:");

        txtCautionFees.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCautionFeesCaretUpdate(evt);
            }
        });

        jLabel9.setText("Caution Refunded:");

        cautionRefunded.setEnabled(false);

        jLabel10.setText("Caution Paid");

        cautionPaid.setEnabled(false);

        jLabel11.setText("Wedding Date:");

        weddingDate.setDateFormatString("dd/MM/yyyy");

        jLabel12.setText("Wedding Time:");

        try {
            txtTime.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##h##min")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimeActionPerformed(evt);
            }
        });

        jLabel13.setText("Celebrant:");

        cmbCelebrant.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel14.setText("Wedding Status:");

        cmbWeddingStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pending", "Celebrated", "Suspended" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCautionFees, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cautionPaid))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbHusband, 0, 215, Short.MAX_VALUE)
                            .addComponent(txtWeddingFees, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbWife, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbCelebrant, 0, 215, Short.MAX_VALUE)
                            .addComponent(weddingDate, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(weddingFeesPaid)
                        .addComponent(cautionRefunded)
                        .addComponent(txtWifeGodMother)
                        .addComponent(txtHusbandGodFather, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbWeddingStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbHusband, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtHusbandGodFather, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbWife, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtWifeGodMother, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(txtWeddingFees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weddingFeesPaid))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCautionFees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cautionPaid)
                    .addComponent(jLabel9)
                    .addComponent(cautionRefunded))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(weddingDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cmbCelebrant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(cmbWeddingStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(492, 492, 492)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtWeddingCode, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtWeddingCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    clear();
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    save();
    cautionRefunded.setEnabled(false);
    btnPrint.setEnabled(true);
}//GEN-LAST:event_jButton2ActionPerformed

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    txtWeddingCode.setEditable(true);
    txtWeddingCode.setText(null);
    txtWeddingCode.setBackground(Color.WHITE);
}//GEN-LAST:event_jButton3ActionPerformed

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    cautionRefunded.setEnabled(true);
    search();
}//GEN-LAST:event_jButton4ActionPerformed

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    this.dispose();
}//GEN-LAST:event_jButton6ActionPerformed

private void txtWeddingCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWeddingCodeActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtWeddingCodeActionPerformed

private void txtTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimeActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtTimeActionPerformed

private void txtWeddingFeesCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtWeddingFeesCaretUpdate
    if (!txtWeddingFees.getText().isEmpty()) {
        weddingFeesPaid.setEnabled(true);
        try {
            Integer.parseInt(txtWeddingFees.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Enter wedding fees as a number please");
        }
    } else {
        weddingFeesPaid.setEnabled(false);
    }
}//GEN-LAST:event_txtWeddingFeesCaretUpdate

private void txtCautionFeesCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCautionFeesCaretUpdate
    if (!txtCautionFees.getText().isEmpty()) {
        cautionPaid.setEnabled(true);
        try {
            Integer.parseInt(txtCautionFees.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Enter Caution fees as a number please");
        }
    } else {
        cautionPaid.setEnabled(false);
    }
}//GEN-LAST:event_txtCautionFeesCaretUpdate

private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
    print();
}//GEN-LAST:event_btnPrintActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JCheckBox cautionPaid;
    private javax.swing.JCheckBox cautionRefunded;
    private javax.swing.JComboBox cmbCelebrant;
    private javax.swing.JComboBox cmbHusband;
    private javax.swing.JComboBox cmbWeddingStatus;
    private javax.swing.JComboBox cmbWife;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JTextField txtCautionFees;
    private javax.swing.JTextField txtHusbandGodFather;
    private javax.swing.JFormattedTextField txtTime;
    private javax.swing.JTextField txtWeddingCode;
    private javax.swing.JTextField txtWeddingFees;
    private javax.swing.JTextField txtWifeGodMother;
    private com.toedter.calendar.JDateChooser weddingDate;
    private javax.swing.JCheckBox weddingFeesPaid;
    // End of variables declaration//GEN-END:variables
}
