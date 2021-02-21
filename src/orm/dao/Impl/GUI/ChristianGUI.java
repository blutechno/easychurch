/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChristianGUI.java
 *
 * Created on May 5, 2012, 11:20:40 AM
 */
package orm.dao.Impl.GUI;

import java.awt.Color;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import orm.Christian;
import orm.ChristianContact;
import orm.ChristianSacrement;
import orm.ChristianSacrementId;
import orm.ChristianZone;
import orm.DAOFactory;
import orm.Notifier;
import orm.Sacrement;
import orm.Zone;

/**
 *
 * @author jean pierre
 */
public class ChristianGUI extends javax.swing.JInternalFrame {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private String celebrant = "None";
    private ChristianModule module;

    /** Creates new form ChristianGUI */
    public ChristianGUI() {
        initComponents();
        setParish();
        setChristianPin();
        setZone();
    }

    public void setModule(ChristianModule module) {
        this.module = module;
    }

    private void setZone() {
        cmbZone.removeAllItems();
        for (Zone z : daoFactory.getZoneDAO().findAll()) {
            cmbZone.addItem(z.getZoneName());
        }
    }

    private void setFather(String name) {
        List<Christian> temp = daoFactory.getChristianDAO().findByFatherName(name);
        Set<String> holder = new HashSet<String>();
        if (!temp.isEmpty()) {
            cmbFather.removeAllItems();
            for (Christian c : temp) {
                try {
                    holder.add(c.getFatherName());
                    if (c.getGender() == 'M') {
                        holder.add(c.getFullName());
                    }
                } catch (Exception ex) {
                }
            }
            for (String father : holder) {
                cmbFather.addItem(father);
            }
        }
    }

    private void setMother(String name) {
        List<Christian> temp = daoFactory.getChristianDAO().findByMotherName(name);
        Set<String> holder = new HashSet<String>();
        if (!temp.isEmpty()) {
            cmbMother.removeAllItems();
            for (Christian c : temp) {
                try {
                    holder.add(c.getMotherName());
                    if (c.getGender() == 'F') {
                        holder.add(c.getFullName());
                    }
                } catch (Exception ex) {
                }
            }
            for (String father : holder) {
                cmbMother.addItem(father);
            }
        }
    }

    private void setParish() {
        try {
            txtParish.setText(daoFactory.getParishDAO().findAll().get(0).getParishName());
        } catch (Exception ex) {
        }
    }

    private void setChristianPin() {
        try {
            int size = daoFactory.getChristianDAO().findAll().size();
            txtChristianPin.setText(String.format("%05d", (size + 1)));
        } catch (Exception ex) {
            txtChristianPin.setText("0000" + 1);
        }
    }

    private String findChristian(String fName, String lName) {
        String result = "";
        for (Christian c : daoFactory.getChristianDAO().findAll()) {
            String f = c.getFirstName();
            String l = c.getLastName();
            if ((f.equalsIgnoreCase(fName) && l.equalsIgnoreCase(lName)) || (f.equalsIgnoreCase(lName) && l.equalsIgnoreCase(fName))) {
                result = c.getChristianPin();
                break;
            }
        }
        return result;
    }

    private void clear() {
        txtFirstName.setText(null);
        txtMiddleName.setText(null);
        txtLastName.setText(null);
        chDateOfBirth.setDate(null);
        rdMale.setSelected(true);
        try {
            cmbFather.removeAllItems();
            cmbMother.removeAllItems();
        } catch (Exception ex) {
        }
        setChristianPin();
        cmbZone.setSelectedIndex(0);
        txtChristianPin.setEditable(false);
        txtChristianPin.setBackground(new Color(153, 153, 153));
        txtTelephoneNumber.setText(null);
        txtOtherTelephone.setText(null);
        txtEmail.setText(null);
        txtOtherEmail.setText(null);
        chBaptismDate.setDate(null);
        baptizedHere.setSelected(false);
        isActive.setSelected(false);
        cmbMaritalStatus.setSelectedIndex(0);
        txtSpouseFirstName.setText(null);
        txtSpouseLastName.setText(null);
        cmbEducationalLevel.setSelectedIndex(0);
        cmbProfession.setSelectedIndex(0);
    }

    private void save(Christian christian) {
        String pin=txtChristianPin.getText();
        christian.setFirstName(txtFirstName.getText());
        christian.setGender(rdMale.isSelected() ? 'M' : 'F');
        christian.setLastName(txtLastName.getText());
        christian.setMiddleName(txtMiddleName.getText());
        christian.setDateOfBirth(chDateOfBirth.getDate());
        christian.setFatherName((String) cmbFather.getSelectedItem());
        christian.setMotherName((String) cmbMother.getSelectedItem());
        christian.setParish(daoFactory.getParishDAO().findByName((String) txtParish.getText()));
        christian.setBaptisedHere(baptizedHere.isSelected() ? 'Y' : 'N');
        christian.setBaptismDate(chBaptismDate.getDate());
        christian.setIsActive(isActive.isSelected() ? 'Y' : 'N');
        christian.setMaritalStatus(((String) cmbMaritalStatus.getSelectedItem()).charAt(0));
        christian.setSpouseFirstName(txtSpouseFirstName.getText());
        christian.setSpouseLastName(txtSpouseLastName.getText());
        christian.setEducationLevel((String) cmbEducationalLevel.getSelectedItem());
        christian.setProfession((String) cmbProfession.getSelectedItem());
        christian.setCreatedOn(new Date());
        christian.setFullName(txtLastName.getText() + " " + txtFirstName.getText());

        boolean done = daoFactory.getChristianDAO().saveOrUpdate(christian);
        if (done) {
            if (daoFactory.getChristianZoneDAO().findByChristian(pin).isEmpty()) {
                saveChristianZone(txtChristianPin.getText());
            }
        }
        Notifier.notifier(done);

        if (done) {
            if (chBaptismDate.getDate() != null) {

                ChristianSacrement baptism = new ChristianSacrement();
                christian = daoFactory.getChristianDAO().findByChristianPin(txtChristianPin.getText());
                baptism.setChristian(christian);
                Sacrement sacrement = daoFactory.getSacrementDAO().findById(1);
                baptism.setSacrement(sacrement);
                ChristianSacrementId id = new ChristianSacrementId(christian.getChristianId(), sacrement.getSacramentId());

                if (daoFactory.getChristianSacrementDAO().findBId(id) != null) {
                    baptism = daoFactory.getChristianSacrementDAO().findBId(id);
                }

                baptism.setCelebrationDate(chBaptismDate.getDate());
                baptism.setCelebratedHere(baptizedHere.isSelected() ? 'Y' : 'N');
                baptism.setCelebrant(celebrant);
                baptism.setGodParent("None");
                baptism.setGodParentParish("None");
                baptism.setId(id);
                Notifier.notifier(daoFactory.getChristianSacrementDAO().saveOrUpdateChristianSacrement(baptism));

            }
            if (!txtTelephoneNumber.getText().isEmpty() && !txtEmail.getText().isEmpty()) {
                christian = daoFactory.getChristianDAO().findByChristianPin(txtChristianPin.getText());
                saveContact(christian.getChristianId());
            }
        }
        //Notifier.notifier(done);
    }

    private void saveContact(int christianId) {
        ChristianContact contact = new ChristianContact();
        Christian christian = daoFactory.getChristianDAO().findById(christianId);
        if (daoFactory.getChristianContactDAO().findById(christian.getChristianId()) != null) {
            contact = daoFactory.getChristianContactDAO().findById(christian.getChristianId());
        }
        contact.setChristian(christian.getChristianId());
        contact.setChristian_1(christian);
        contact.setPhoneNumber(txtTelephoneNumber.getText());
        contact.setOtherTelephone(txtOtherTelephone.getText());
        contact.setEmail(txtEmail.getText());
        contact.setOtherEmail(txtOtherEmail.getText());
        Notifier.notifier(daoFactory.getChristianContactDAO().saveOrUpdateChristianContact(contact));
    }

    private void save() {
//        String tempCode = this.findChristian(txtFirstName.getText(), txtLastName.getText());
//        if (!tempCode.isEmpty() && !tempCode.equals(txtChristianPin.getText())) {
//            JOptionPane.showMessageDialog(null, "Attempt to save same christian twice", "Warning Message", JOptionPane.WARNING_MESSAGE);
//        } else {
        if (chDateOfBirth.getDate().compareTo(new Date()) <= 0) {
            //if (chBaptismDate.getDate().compareTo(new Date()) < 0) {
            try {
                Christian christian = new Christian();
                if (daoFactory.getChristianDAO().findByChristianPin(txtChristianPin.getText()) != null) {
                    christian = daoFactory.getChristianDAO().findByChristianPin(txtChristianPin.getText());
                }
                christian.setChristianPin(txtChristianPin.getText());
                save(christian);
            } catch (Exception ex) {
            }
//                } else {
//                    JOptionPane.showMessageDialog(null, "Baptism Date cannot be greater than today", "Incorrect Date Error", JOptionPane.WARNING_MESSAGE);
//                }
        } else {
            JOptionPane.showMessageDialog(null, "The christian you attempting to save is unborn. Date of birth greater than today", "Incorrect Date Error", JOptionPane.WARNING_MESSAGE);
        }

        //}
    }

    private void saveChristianZone(String christianPin) {
        ChristianZone cZone = new ChristianZone();
        cZone.setChristian(daoFactory.getChristianDAO().findByChristianPin(christianPin));
        cZone.setCreatedOn(new Date());
        cZone.setZone(daoFactory.getZoneDAO().findByName((String) cmbZone.getSelectedItem()));
        cZone.setIsActive('Y');
        Notifier.notifier(daoFactory.getChristianZoneDAO().saveOrUpdateChristianZone(cZone));
    }

    private void search() {
        if (txtChristianPin.getText().isEmpty()) {
            try {
                ChristianSearchGUI searchGUI = new ChristianSearchGUI();
                searchGUI.setChristianWindow(this);
                searchGUI.setChristianDimension(this.getSize());
                module.add(searchGUI);
                searchGUI.setCurrentWindow(2);
                searchGUI.show();
                this.setSize(0, 0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                display(daoFactory.getChristianDAO().findByChristianPin(txtChristianPin.getText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Not Result Found");
            }
        }
        txtChristianPin.setEditable(false);
        txtChristianPin.setBackground(new Color(153, 153, 153));
        btnSearch.setEnabled(false);
        btnFind.setEnabled(true);
    }

    private void seachChristianZone(String christianPin) {
        if (daoFactory.getChristianZoneDAO().findActive(christianPin) != null) {
            try {
                ChristianZone cZone = daoFactory.getChristianZoneDAO().findActive(christianPin);
                for (int i = 0; i < cmbZone.getItemCount(); i++) {
                    if (cZone.getZone().getZoneName().equals((String) cmbZone.getItemAt(i))) {
                        cmbZone.setSelectedIndex(i);
                        break;
                    }
                }
                isActive.setSelected(cZone.getIsActive() == 'Y');
            } catch (Exception ex) {
            }
        } else {
        }
    }

    public void display(Christian c) {

        txtFirstName.setText(c.getFirstName());
        txtMiddleName.setText(c.getMiddleName());
        txtLastName.setText(c.getLastName());
        chDateOfBirth.setDate(c.getDateOfBirth());
        txtChristianPin.setText(c.getChristianPin());
        seachChristianZone(c.getChristianPin());
        //Setting the radio button corresponding to the gender of the retrieved christian
        if (c.getGender() == 'M') {
            rdMale.setSelected(true);
        } else {
            rdFemale.setSelected(true);
        }
        boolean isFound = false;
        //Setting christian parents' Names
        for (int i = 0; i < cmbFather.getItemCount(); i++) {
            if (c.getFatherName().equals((String) cmbFather.getItemAt(i))) {
                isFound = true;
                cmbFather.setSelectedIndex(i);
                break;
            }
        }
        if (!isFound) {
            cmbFather.addItem(c.getFatherName());
            cmbFather.setSelectedIndex(cmbFather.getItemCount() - 1);
        }
        isFound = false;
        for (int i = 0; i < cmbMother.getItemCount(); i++) {
            if (c.getMotherName().equals((String) cmbMother.getItemAt(i))) {
                isFound = true;
                cmbMother.setSelectedIndex(i);
                break;
            }
        }
        if (!isFound) {
            cmbMother.addItem(c.getMotherName());
            cmbMother.setSelectedIndex(cmbMother.getItemCount() - 1);
        }
        for (int j = 0; j < cmbMaritalStatus.getItemCount(); j++) {
            if (((String) cmbMaritalStatus.getItemAt(j)).charAt(0) == c.getMaritalStatus()) {
                cmbMaritalStatus.setSelectedIndex(j);
                break;
            }
        }

        //Trying to set christian contact
        if (daoFactory.getChristianContactDAO().findById(c.getChristianId()) != null) {
            ChristianContact contact = daoFactory.getChristianContactDAO().findById(c.getChristianId());
            txtTelephoneNumber.setText(contact.getPhoneNumber());
            txtOtherTelephone.setText(contact.getOtherTelephone());
            txtEmail.setText(contact.getEmail());
            txtOtherEmail.setText(contact.getOtherEmail());
        }

        baptizedHere.setSelected(c.getBaptisedHere() == 'Y');
        isActive.setSelected(c.getIsActive() == 'Y');
        txtSpouseFirstName.setText(c.getSpouseFirstName());
        txtSpouseLastName.setText(c.getSpouseLastName());
        //Setting Profession and Educational Level
        for (int j = 0; j < cmbEducationalLevel.getItemCount(); j++) {
            if (c.getEducationLevel().equals((String) cmbEducationalLevel.getItemAt(j))) {
                cmbEducationalLevel.setSelectedIndex(j);
                break;
            }
        }
        isFound = false;
        for (int i = 0; i < cmbProfession.getItemCount(); i++) {
            if (c.getProfession().equals((String) cmbProfession.getItemAt(i))) {
                isFound = true;
                cmbProfession.setSelectedIndex(i);
                break;
            }
        }
        if (!isFound) {
            cmbProfession.addItem(c.getProfession());
            cmbProfession.setSelectedIndex(cmbProfession.getItemCount() - 1);
        }
        chBaptismDate.setDate(c.getBaptismDate());

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnFind = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbMother = new javax.swing.JComboBox();
        cmbFather = new javax.swing.JComboBox();
        rdMale = new javax.swing.JRadioButton();
        rdFemale = new javax.swing.JRadioButton();
        chDateOfBirth = new com.toedter.calendar.JDateChooser();
        txtLastName = new javax.swing.JTextField();
        txtMiddleName = new javax.swing.JTextField();
        txtFirstName = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtChristianPin = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtOtherEmail = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtOtherTelephone = new javax.swing.JFormattedTextField();
        txtTelephoneNumber = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cmbMaritalStatus = new javax.swing.JComboBox();
        txtSpouseFirstName = new javax.swing.JTextField();
        txtSpouseLastName = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        chBaptismDate = new com.toedter.calendar.JDateChooser();
        baptizedHere = new javax.swing.JCheckBox();
        isActive = new javax.swing.JCheckBox();
        txtParish = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        cmbZone = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cmbEducationalLevel = new javax.swing.JComboBox();
        cmbProfession = new javax.swing.JComboBox();

        setClosable(true);
        setTitle("Christian");

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

        btnFind.setBackground(new java.awt.Color(0, 0, 0));
        btnFind.setForeground(new java.awt.Color(255, 255, 255));
        btnFind.setText("Find");
        btnFind.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFind.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFind);

        btnSearch.setBackground(new java.awt.Color(0, 0, 0));
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Search");
        btnSearch.setEnabled(false);
        btnSearch.setFocusable(false);
        btnSearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSearch);

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "General Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 102, 0)));

        jLabel2.setText("First Name:");

        jLabel3.setText("Middle Name:");

        jLabel4.setText("Last Name:");

        jLabel5.setText("Date of Birth:");

        jLabel6.setText("Gender:");

        jLabel7.setText("Father's Name:");

        jLabel8.setText("Mother's Name:");

        cmbMother.setEditable(true);
        cmbMother.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMotherItemStateChanged(evt);
            }
        });

        cmbFather.setEditable(true);
        cmbFather.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbFatherItemStateChanged(evt);
            }
        });
        cmbFather.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cmbFatherKeyReleased(evt);
            }
        });

        rdMale.setSelected(true);
        rdMale.setText("Male");
        rdMale.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdMaleItemStateChanged(evt);
            }
        });

        rdFemale.setText("Famele");
        rdFemale.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdFemaleItemStateChanged(evt);
            }
        });

        chDateOfBirth.setDateFormatString("dd/MM/yyyy");

        jButton5.setText("load");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setText("load");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdMale)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdFemale))
                            .addComponent(cmbFather, 0, 209, Short.MAX_VALUE)
                            .addComponent(cmbMother, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7)
                            .addComponent(jButton5)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMiddleName)
                            .addComponent(txtFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(txtLastName))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMiddleName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(13, 13, 13)
                        .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rdMale)
                    .addComponent(rdFemale))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbFather, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmbMother, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel1.setText("Christian PIN:");

        txtChristianPin.setBackground(new java.awt.Color(204, 204, 204));
        txtChristianPin.setEditable(false);
        txtChristianPin.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contact Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 102, 0)));

        jLabel9.setText("Telephone Number:");

        jLabel10.setText("Other Telephone Number:");

        jLabel11.setText("Email Address:");

        jLabel12.setText("Other Email Address:");

        try {
            txtOtherTelephone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            txtTelephoneNumber.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOtherEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtOtherTelephone, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtTelephoneNumber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTelephoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtOtherTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtOtherEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Marital Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 153, 0)));

        jLabel19.setText("Marital Status:");

        jLabel20.setText("Spouse First Name:");

        jLabel21.setText("Spouse Last Name:");

        cmbMaritalStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Single", "Married", "Divorcee", "Widow(er)" }));
        cmbMaritalStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMaritalStatusItemStateChanged(evt);
            }
        });
        cmbMaritalStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMaritalStatusActionPerformed(evt);
            }
        });

        txtSpouseFirstName.setBackground(new java.awt.Color(204, 204, 204));
        txtSpouseFirstName.setEditable(false);

        txtSpouseLastName.setBackground(new java.awt.Color(204, 204, 204));
        txtSpouseLastName.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(34, 34, 34)
                        .addComponent(cmbMaritalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSpouseFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSpouseLastName)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cmbMaritalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtSpouseFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtSpouseLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Christianity Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 102, 0)));

        jLabel13.setText("Parish:");

        jLabel14.setText("Baptism Date:");

        jLabel15.setText("Baptized Here:");

        jLabel16.setText("Is Active");

        chBaptismDate.setDateFormatString("dd/MM/yyyy");

        baptizedHere.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                baptizedHereItemStateChanged(evt);
            }
        });

        txtParish.setBackground(new java.awt.Color(204, 204, 204));
        txtParish.setEditable(false);
        txtParish.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel22.setText("Zone:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtParish, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addComponent(chBaptismDate, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(isActive)
                            .addComponent(baptizedHere)
                            .addComponent(cmbZone, 0, 262, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chBaptismDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtParish, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(baptizedHere))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(isActive))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(cmbZone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Educational and Employment Background", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 102, 0)));

        jLabel17.setText("Educational Level:");

        jLabel18.setText("Profession:");

        cmbEducationalLevel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Primary School", "Secondary School", "Bachelor's Degree", "Masters Degree", "Doctorate", "Ph.D", "Professor", "None" }));

        cmbProfession.setEditable(true);
        cmbProfession.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Student", "Teacher", "Public Service", "Police", "Driver", "Carpenter", "Mason", "Military", "Businessman", "Private Entrepreneur", "Teacher" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbEducationalLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbProfession, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cmbEducationalLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cmbProfession, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(579, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtChristianPin, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtChristianPin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

private void baptizedHereItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_baptizedHereItemStateChanged
}//GEN-LAST:event_baptizedHereItemStateChanged

private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
    txtChristianPin.setEditable(true);
    txtChristianPin.setText(null);
    txtChristianPin.setBackground(Color.WHITE);
    txtChristianPin.requestFocus();
    btnSearch.setEnabled(true);
    btnFind.setEnabled(false);
}//GEN-LAST:event_btnFindActionPerformed

private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
    search();
}//GEN-LAST:event_btnSearchActionPerformed

private void rdMaleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdMaleItemStateChanged
    if (rdMale.isSelected()) {
        rdFemale.setSelected(false);
    }
}//GEN-LAST:event_rdMaleItemStateChanged

private void rdFemaleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdFemaleItemStateChanged
    if (rdFemale.isSelected()) {
        rdMale.setSelected(false);
    }
}//GEN-LAST:event_rdFemaleItemStateChanged

private void cmbFatherItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbFatherItemStateChanged
}//GEN-LAST:event_cmbFatherItemStateChanged

private void cmbMotherItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMotherItemStateChanged
}//GEN-LAST:event_cmbMotherItemStateChanged

private void cmbFatherKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbFatherKeyReleased
}//GEN-LAST:event_cmbFatherKeyReleased

private void cmbMaritalStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMaritalStatusItemStateChanged
    if (cmbMaritalStatus.getSelectedIndex() == 1) {
        txtSpouseFirstName.setEditable(true);
        txtSpouseFirstName.setBackground(Color.WHITE);
        txtSpouseLastName.setEditable(true);
        txtSpouseLastName.setBackground(Color.WHITE);
    } else {
        txtSpouseFirstName.setEditable(false);
        txtSpouseFirstName.setBackground(new Color(153, 153, 153));
        txtSpouseLastName.setBackground(new Color(153, 153, 153));
        txtSpouseLastName.setEditable(false);
    }
}//GEN-LAST:event_cmbMaritalStatusItemStateChanged

private void cmbMaritalStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMaritalStatusActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_cmbMaritalStatusActionPerformed

private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    try {
        String name = (String) cmbFather.getSelectedItem();
        setFather(name);
    } catch (Exception ex) {
    }
}//GEN-LAST:event_jButton5ActionPerformed

private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
    try {
        String name = (String) cmbMother.getSelectedItem();
        setMother(name);
    } catch (Exception ex) {
    }
}//GEN-LAST:event_jButton7ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox baptizedHere;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnSearch;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private com.toedter.calendar.JDateChooser chBaptismDate;
    private com.toedter.calendar.JDateChooser chDateOfBirth;
    private javax.swing.JComboBox cmbEducationalLevel;
    private javax.swing.JComboBox cmbFather;
    private javax.swing.JComboBox cmbMaritalStatus;
    private javax.swing.JComboBox cmbMother;
    private javax.swing.JComboBox cmbProfession;
    private javax.swing.JComboBox cmbZone;
    private javax.swing.JCheckBox isActive;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JRadioButton rdFemale;
    private javax.swing.JRadioButton rdMale;
    private javax.swing.JTextField txtChristianPin;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtMiddleName;
    private javax.swing.JTextField txtOtherEmail;
    private javax.swing.JFormattedTextField txtOtherTelephone;
    private javax.swing.JTextField txtParish;
    private javax.swing.JTextField txtSpouseFirstName;
    private javax.swing.JTextField txtSpouseLastName;
    private javax.swing.JFormattedTextField txtTelephoneNumber;
    // End of variables declaration//GEN-END:variables
}
