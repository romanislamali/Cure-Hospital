/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cure_hospital.view;

import cure_hospital.util.DBConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Roman
 */
public class ViewHospital extends javax.swing.JFrame {

    //Get date
    Date getDate = Date.valueOf(LocalDate.now());
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String date = df.format(getDate);

    //Get local time
    LocalTime getTime = LocalTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
    String time = getTime.format(dateTimeFormatter);
    
    PreparedStatement pst;
    ResultSet rs;
    DBConnection db = new DBConnection();

    /**
     * Creates new form Home
     */
    public ViewHospital() {
        initComponents();
        admitPatientBTN.setVisible(false);
        addDiagonosisBTN.setVisible(false);
        patientHistoryBTN.setVisible(false);
        employeeInfoBtn.setVisible(false);
        dashBoardBTN.setVisible(false);
        logOutBTN.setVisible(false);
        
        userNameShowLab.setVisible(false);
        userNameShow.setVisible(false);

        tab.setSelectedIndex(5);
        homePanel.setVisible(true);
        loginPopup.setVisible(true);
        labelIDCheck.setVisible(false);
        labelPatientExistChack.setVisible(false);
        choiceWord.setVisible(false);
    }
    
    public void clear() {
        txtPName.setText("");
        txtPContact.setText("");
        txtPatAge.setText("");
        txtPAddress.setText("");
        txtPMajorDisaese.setText("");

//        txtSymtops.setText("");
//        txtReferDoctor.setText("");
//        txtTypeWord.setText("");
//        txtDiagosis.setText("");
//
//        txtAdmitFee.setText("");
//        txtWordFee.setText("");
//        txtDoctorFee.setText("");
//        txtTestFee.setText("");
//        txtMedicineFee.setText("");
//        txtTransportFee.setText("");
//        txtServiceFee.setText("");
//        txtTotalPayable.setText("");
    }

    //Login method
    public void login() {
        String sql = "SELECT first_name from user WHERE username=? AND password=?";
        String user = userNameFeild.getText();
        String pass = passField.getText();
        userNameShow.setText(user);
        
        try {
            pst = db.getCon().prepareStatement(sql);
            pst.setString(1, user);
            pst.setString(2, pass);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "   Congratulations  " + user + "\n Your Login is successfull !!");
                admitPatientBTN.setVisible(true);
                addDiagonosisBTN.setVisible(true);
                patientHistoryBTN.setVisible(true);
                employeeInfoBtn.setVisible(true);
                dashBoardBTN.setVisible(true);
                logOutBTN.setVisible(true);
                
                userNameShowLab.setVisible(true);
                userNameShow.setVisible(true);
                
                loginPopup.setVisible(false);
                
                userNameFeild.setText("");
                passField.setText("");
                
            } else {
                JOptionPane.showMessageDialog(this, "Your username or password is incorrect !! \nPlease try again...");
                admitPatientBTN.setVisible(false);
                addDiagonosisBTN.setVisible(false);
                patientHistoryBTN.setVisible(false);
                employeeInfoBtn.setVisible(false);
                dashBoardBTN.setVisible(false);
                logOutBTN.setVisible(false);
                
                userNameShowLab.setVisible(false);
                userNameShow.setVisible(false);
                
                loginPopup.setVisible(true);
                
                userNameFeild.setText("");
                passField.setText("");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ViewHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void logout() {
        JOptionPane.showMessageDialog(this, "user" + "Do you want to logout ?\n If you log out once, you will have to re-login later");
        admitPatientBTN.setVisible(false);
        addDiagonosisBTN.setVisible(false);
        patientHistoryBTN.setVisible(false);
        employeeInfoBtn.setVisible(false);
        dashBoardBTN.setVisible(false);
        logOutBTN.setVisible(false);
        
        userNameShowLab.setVisible(false);
        userNameShow.setVisible(false);
        
        loginPopup.setVisible(true);
        
        userNameFeild.setText("");
        passField.setText("");
        
    }

    //Admit patient method
    public void addPatient() {
        
        try {
            String sqlPatient = "insert into patient(name,contact,age,gender,blood_group,address,major_disease,admit_date,admit_time) values(?,?,?,?,?,?,?,?,?)";
            pst = db.getCon().prepareStatement(sqlPatient);
            pst.setString(1, txtPName.getText().trim());
            pst.setString(2, txtPContact.getText());
            pst.setString(3, txtPatAge.getText());
            pst.setString(4, buttonGroup1.getSelection().getActionCommand());
            pst.setString(5, bloodCombo.getSelectedItem().toString());
            pst.setString(6, txtPAddress.getText());
            pst.setString(7, txtPMajorDisaese.getText());
            pst.setString(8, date);
            pst.setString(9, time);
            
            pst.executeUpdate();
            pst.close();
            db.getCon().close();
            JOptionPane.showMessageDialog(this, "Patient Successfully Admited");
            
        } catch (SQLException ex) {
            Logger.getLogger(ViewHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showAdmitedPatient() throws SQLException {
        String columns[] = {"Patient ID ", "Name", "Contact", "Age", "Gender", "Blood Group", "Major Disease", "Address"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        sampleTable.setModel(model);
        
        String sqlShowData = "SELECT patient_ID,name, contact, age, gender, blood_group,major_disease,address FROM patient WHERE patient_ID=(SELECT max(patient_ID)FROM patient)";
        try {
            pst = db.getCon().prepareStatement(sqlShowData);
        } catch (SQLException ex) {
            Logger.getLogger(ViewHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        rs = pst.executeQuery();
        if (rs.next()) {
            int pID = rs.getInt("patient_ID");
            String name = rs.getString("name");
            String contact = rs.getString("contact");
            String age = rs.getString("age");
            String gender = rs.getString("gender");
            String blood = rs.getString("blood_group");
            String major_disease = rs.getString("major_disease");
            String address = rs.getString("address");
            
            model.addRow(new Object[]{pID, name, contact, age, gender, blood, major_disease, address});
            
            pst.close();
            db.getCon().close();
            
        }
    }
    
    public void showAllData() {
        String columnsAll[] = {"Patient_ID ", "Name", "Contact", "Age", "Gender", "Blood Group", "Major Disease",
            "Address", "Symptomps", "Diagonosis", "Word", "Admit Date", "Admit Time"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsAll);
        patientHistoryTable.setModel(model);
        String sqlAlldata = "SELECT * FROM patient p INNER JOIN diagonosis d ON p.patient_ID = d.patient_ID INNER JOIN fees f ON f.patient_ID = p.patient_ID";
        
        try {
            pst = db.getCon().prepareStatement(sqlAlldata);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                int patient_ID = rs.getInt("patient_ID");
                String name = rs.getString("name");
                String contact = rs.getString("contact");
                String age = rs.getString("age");
                String gender = rs.getString("gender");
                String blood = rs.getString("blood_group");
                String major_disease = rs.getString("major_disease");
                String address = rs.getString("address");
                String symptomps = rs.getString("symptomps");
                String diagonosis = rs.getString("diagonosis");
                String medical_word = rs.getString("medical_word");
                String admit_date = rs.getString("admit_date");
                String admit_time = rs.getString("admit_time");
                
                model.addRow(new Object[]{patient_ID, name, contact, age, gender, blood, major_disease, address, symptomps, diagonosis, medical_word, admit_date, admit_time});
                
            }
            pst.close();
            db.getCon().close();
            
        } catch (SQLException ex) {
            Logger.getLogger(ViewHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchPatient(String str) {
        DefaultTableModel model;
        model = (DefaultTableModel) patientHistoryTable.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        patientHistoryTable.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
    }
    
    public void userDetails() {
        String columnsAlladmin[] = {"Admin ID ", "First Name", "Last Name", "Designation", "Address", "Contact"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsAlladmin);
        adminsTableModel.setModel(model);
        
        String sqlAlldata = "SELECT * FROM user";
        
        try {
            pst = db.getCon().prepareStatement(sqlAlldata);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String designation = rs.getString("designation");
                String address = rs.getString("address");
                String contact = rs.getString("contact");
                
                model.addRow(new Object[]{user_id, first_name, last_name, designation, address, contact});
            }
            pst.close();
            db.getCon().close();
            
        } catch (SQLException ex) {
            Logger.getLogger(ViewHospital.class.getName()).log(Level.SEVERE, null, ex);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        addDiagonosisBTN = new javax.swing.JButton();
        patientHistoryBTN = new javax.swing.JButton();
        employeeInfoBtn = new javax.swing.JButton();
        dashBoardBTN = new javax.swing.JButton();
        logOutBTN = new javax.swing.JButton();
        admitPatientBTN = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        showTime = new javax.swing.JLabel();
        showDate = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tab = new javax.swing.JTabbedPane();
        admitPatientTab = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnAdmit = new javax.swing.JButton();
        txtPMajorDisaese = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPAddress = new javax.swing.JTextArea();
        bloodCombo = new javax.swing.JComboBox<>();
        rMale = new javax.swing.JRadioButton();
        rFemale = new javax.swing.JRadioButton();
        rOthers = new javax.swing.JRadioButton();
        txtPatAge = new javax.swing.JTextField();
        txtPContact = new javax.swing.JTextField();
        txtPName = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        sampleTable = new javax.swing.JTable();
        addDiagoPanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtPatientIDField = new javax.swing.JTextField();
        searChID = new javax.swing.JButton();
        labelIDCheck = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableModel = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtSymtops = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        comboDoctor = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        checkWord = new javax.swing.JCheckBox();
        choiceWord = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        test = new javax.swing.JComboBox<>();
        searChTest = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtAdmitFee = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtWordFee = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDoctorFee = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTestFee = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtMedicineFee = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtTransportFee = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtSerrviceFee = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        patuentHistorytTab = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        searchPatient = new javax.swing.JTextField();
        labelPatientExistChack = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        patientHistoryTable = new javax.swing.JTable();
        updateBTN = new javax.swing.JButton();
        resetBTN = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        empInfoTab = new javax.swing.JPanel();
        btnAdmin = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        adminsTableModel = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        homePanel = new javax.swing.JPanel();
        loginPopup = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        userNameFeild = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        passField = new javax.swing.JPasswordField();
        userLoginBTN = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        userNameShowLab = new javax.swing.JLabel();
        userNameShow = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(29, 153, 164));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 5));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.setPreferredSize(new java.awt.Dimension(1300, 100));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addDiagonosisBTN.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        addDiagonosisBTN.setText("Add Diagonosis");
        addDiagonosisBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(153, 255, 255), null, null));
        addDiagonosisBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDiagonosisBTNActionPerformed(evt);
            }
        });
        jPanel3.add(addDiagonosisBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 170, 40));

        patientHistoryBTN.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        patientHistoryBTN.setText("Patient History");
        patientHistoryBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(153, 255, 255), null, null));
        patientHistoryBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientHistoryBTNActionPerformed(evt);
            }
        });
        jPanel3.add(patientHistoryBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 170, 40));

        employeeInfoBtn.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        employeeInfoBtn.setText("Employee Info");
        employeeInfoBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(153, 255, 255), null, null));
        employeeInfoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeInfoBtnActionPerformed(evt);
            }
        });
        jPanel3.add(employeeInfoBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, 170, 40));

        dashBoardBTN.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        dashBoardBTN.setText("Admit Patient");
        dashBoardBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(153, 255, 255), null, null));
        dashBoardBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashBoardBTNActionPerformed(evt);
            }
        });
        jPanel3.add(dashBoardBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 170, 40));

        logOutBTN.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        logOutBTN.setText("Logout");
        logOutBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(153, 255, 255), null, null));
        logOutBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutBTNActionPerformed(evt);
            }
        });
        jPanel3.add(logOutBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 170, 40));

        admitPatientBTN.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        admitPatientBTN.setText("Admit Patient");
        admitPatientBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(153, 255, 255), null, null));
        admitPatientBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                admitPatientBTNActionPerformed(evt);
            }
        });
        jPanel3.add(admitPatientBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 170, 40));

        jPanel2.setBackground(new java.awt.Color(29, 153, 164));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(showTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 40, 130, 20));
        jPanel2.add(showDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 16, 130, 20));

        jLabel2.setBackground(new java.awt.Color(29, 153, 164));
        jLabel2.setFont(new java.awt.Font("Agency FB", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Cure Hospital");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 100));

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 1060, 100));

        tab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 5));

        admitPatientTab.setBackground(new java.awt.Color(231, 235, 239));
        admitPatientTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Admit Form");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        admitPatientTab.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 360, 40));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Name");
        jLabel3.setPreferredSize(new java.awt.Dimension(20, 5));
        admitPatientTab.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 90, 20));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Contact No");
        jLabel4.setPreferredSize(new java.awt.Dimension(20, 5));
        admitPatientTab.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 100, 20));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Age");
        jLabel5.setPreferredSize(new java.awt.Dimension(20, 5));
        admitPatientTab.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, 100, 20));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Gender");
        jLabel6.setPreferredSize(new java.awt.Dimension(20, 5));
        admitPatientTab.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 100, 20));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Blood Group");
        jLabel7.setPreferredSize(new java.awt.Dimension(20, 5));
        admitPatientTab.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, 110, 20));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Address");
        jLabel8.setPreferredSize(new java.awt.Dimension(20, 5));
        admitPatientTab.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, 110, 30));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Major Disease");
        jLabel10.setPreferredSize(new java.awt.Dimension(20, 5));
        admitPatientTab.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, 120, 20));

        btnAdmit.setBackground(new java.awt.Color(0, 102, 102));
        btnAdmit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAdmit.setForeground(new java.awt.Color(255, 255, 255));
        btnAdmit.setText("Admit");
        btnAdmit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdmit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAdmit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAdmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdmitActionPerformed(evt);
            }
        });
        admitPatientTab.add(btnAdmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 300, 110, 30));

        txtPMajorDisaese.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPMajorDisaese.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        admitPatientTab.add(txtPMajorDisaese, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 220, 290, 20));

        txtPAddress.setColumns(20);
        txtPAddress.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPAddress.setRows(5);
        txtPAddress.setText("     ");
        txtPAddress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(txtPAddress);

        admitPatientTab.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 250, 290, 40));

        bloodCombo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bloodCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A+", "B+", "AB+", "O+", "A-", "B-", "AB-", "O-", "N/A" }));
        bloodCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        admitPatientTab.add(bloodCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 120, 20));

        rMale.setActionCommand("Male");
        rMale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rMale);
        rMale.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rMale.setText("Male");
        rMale.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        admitPatientTab.add(rMale, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, 90, 20));

        rFemale.setActionCommand("Female");
        rFemale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rFemale);
        rFemale.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rFemale.setText("Female");
        rFemale.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rFemaleActionPerformed(evt);
            }
        });
        admitPatientTab.add(rFemale, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 80, 20));

        rOthers.setActionCommand("Others");
        rOthers.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rOthers);
        rOthers.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rOthers.setText("Others");
        rOthers.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        admitPatientTab.add(rOthers, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 160, 80, 20));

        txtPatAge.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPatAge.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        admitPatientTab.add(txtPatAge, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, 292, 20));

        txtPContact.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPContact.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        admitPatientTab.add(txtPContact, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 292, 20));

        txtPName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        admitPatientTab.add(txtPName, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 292, 20));

        sampleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", "", "", "", ""
            }
        ));
        jScrollPane2.setViewportView(sampleTable);

        admitPatientTab.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 357, 1020, 180));

        tab.addTab("tab1", admitPatientTab);

        addDiagoPanel.setBackground(new java.awt.Color(231, 235, 239));
        addDiagoPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Rockwell", 1, 48)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(36, 67, 82));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Add Diagonosis");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addDiagoPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 490, 60));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Patient ID :");
        addDiagoPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 80, 30));

        txtPatientIDField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPatientIDField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addDiagoPanel.add(txtPatientIDField, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 140, 30));

        searChID.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        searChID.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cure_hospital/asset/search_icon.png"))); // NOI18N
        searChID.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searChID.setPreferredSize(new java.awt.Dimension(97, 21));
        searChID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searChIDActionPerformed(evt);
            }
        });
        addDiagoPanel.add(searChID, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 70, 40, 30));

        labelIDCheck.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelIDCheck.setForeground(new java.awt.Color(255, 51, 51));
        labelIDCheck.setText("Patient ID Doesn't Exist !!");
        labelIDCheck.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addDiagoPanel.add(labelIDCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 180, 20));

        tableModel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tableModel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tableModel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "", "", "", "", "", "", ""
            }
        ));
        jScrollPane3.setViewportView(tableModel);

        addDiagoPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 1040, 70));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Symptom's");
        addDiagoPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 90, 30));

        txtSymtops.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSymtops.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addDiagoPanel.add(txtSymtops, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, 240, 30));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Refer a Doctor");
        addDiagoPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 110, 30));

        comboDoctor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboDoctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dr. Abdullah Al Mamun (Medicine)", "Dr. Kaniz Fatema (Child & Mother)", "Dr. Abul Kalam Azad (Orthopaedic)", "Dr. Jobaida Abid (Dental)" }));
        comboDoctor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addDiagoPanel.add(comboDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 260, 240, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Word requird?");
        addDiagoPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, 110, 30));

        checkWord.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        checkWord.setText("Yes");
        checkWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkWordActionPerformed(evt);
            }
        });
        addDiagoPanel.add(checkWord, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 340, 50, 30));

        choiceWord.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        choiceWord.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Emargency", "General", "Single", "Double" }));
        choiceWord.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addDiagoPanel.add(choiceWord, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 340, 180, 30));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Test");
        addDiagoPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 100, 30));

        test.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        test.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HB%", "ESR", "PCV", "TC", "DC" }));
        test.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addDiagoPanel.add(test, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, 120, 30));

        searChTest.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        searChTest.setText("Add Test");
        searChTest.setBorder(null);
        searChTest.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searChTest.setPreferredSize(new java.awt.Dimension(97, 21));
        searChTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searChTestActionPerformed(evt);
            }
        });
        addDiagoPanel.add(searChTest, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 420, 90, 30));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Admit Fee");
        addDiagoPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 220, 100, 30));

        txtAdmitFee.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtAdmitFee.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addDiagoPanel.add(txtAdmitFee, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 220, 240, 30));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Word Fee");
        addDiagoPanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 380, 100, 30));

        txtWordFee.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtWordFee.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addDiagoPanel.add(txtWordFee, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 380, 240, 30));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Doctor Fee");
        addDiagoPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, 100, 30));

        txtDoctorFee.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDoctorFee.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addDiagoPanel.add(txtDoctorFee, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 300, 240, 30));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Test Fee");
        addDiagoPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 460, 110, 30));

        txtTestFee.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTestFee.setText("0");
        txtTestFee.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addDiagoPanel.add(txtTestFee, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 460, 240, 30));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Medicine Fee");
        addDiagoPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 260, 100, 30));

        txtMedicineFee.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMedicineFee.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addDiagoPanel.add(txtMedicineFee, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 260, 240, 30));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Transport Fee");
        addDiagoPanel.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 300, 100, 30));

        txtTransportFee.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTransportFee.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addDiagoPanel.add(txtTransportFee, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 300, 240, 30));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Service Fee");
        addDiagoPanel.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 340, 100, 30));

        txtSerrviceFee.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSerrviceFee.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addDiagoPanel.add(txtSerrviceFee, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 340, 240, 30));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Total Payable");
        addDiagoPanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 410, 100, 30));

        jTextField6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addDiagoPanel.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 410, 240, 30));

        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        addDiagoPanel.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 520, 100, 40));

        tab.addTab("tab2", addDiagoPanel);

        patuentHistorytTab.setBackground(new java.awt.Color(231, 235, 239));
        patuentHistorytTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 51, 51));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Patient History");
        jLabel24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        patuentHistorytTab.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 470, 70));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("Search Patient :");
        patuentHistorytTab.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 110, 30));

        searchPatient.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        searchPatient.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        searchPatient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchPatientKeyReleased(evt);
            }
        });
        patuentHistorytTab.add(searchPatient, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, 190, 30));

        labelPatientExistChack.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelPatientExistChack.setForeground(new java.awt.Color(255, 51, 51));
        labelPatientExistChack.setText("Patient Doesn't Exist !!");
        labelPatientExistChack.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        patuentHistorytTab.add(labelPatientExistChack, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 280, 40));

        patientHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "", "", "", "", "", "", "", "", "", "", "", "", ""
            }
        ));
        jScrollPane4.setViewportView(patientHistoryTable);

        patuentHistorytTab.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 1030, 300));

        updateBTN.setText("Update");
        patuentHistorytTab.add(updateBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 510, 90, 30));

        resetBTN.setText("Reset");
        patuentHistorytTab.add(resetBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 510, 90, 30));

        jButton4.setText("jButton2");
        patuentHistorytTab.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 510, 90, 30));

        jButton5.setText("jButton2");
        patuentHistorytTab.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 510, 90, 30));

        tab.addTab("tab3", patuentHistorytTab);

        empInfoTab.setBackground(new java.awt.Color(153, 255, 204));
        empInfoTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAdmin.setFont(new java.awt.Font("MV Boli", 1, 36)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(231, 235, 239));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        btnAdmin.addTab("Doctors", jPanel4);

        jPanel10.setBackground(new java.awt.Color(231, 235, 239));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        btnAdmin.addTab("Nurses", jPanel10);

        jPanel11.setBackground(new java.awt.Color(231, 235, 239));

        adminsTableModel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Admin ID", "First Name", "Last Name", "Designation", "Address", "Contact"
            }
        ));
        jScrollPane8.setViewportView(adminsTableModel);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        btnAdmin.addTab("Admins", jPanel11);

        jPanel12.setBackground(new java.awt.Color(231, 235, 239));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        btnAdmin.addTab("Stuff", jPanel12);

        jPanel13.setBackground(new java.awt.Color(231, 235, 239));

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane10.setViewportView(jTable5);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        btnAdmin.addTab("tab5", jPanel13);

        jPanel14.setBackground(new java.awt.Color(231, 235, 239));

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane11.setViewportView(jTable6);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        btnAdmin.addTab("tab6", jPanel14);

        empInfoTab.add(btnAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, -1));

        tab.addTab("tab5", empInfoTab);

        homePanel.setBackground(new java.awt.Color(231, 235, 239));

        loginPopup.setBackground(new java.awt.Color(29, 153, 164));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel27.setText("Username :");

        userNameFeild.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel28.setText("Password :");

        passField.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        userLoginBTN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        userLoginBTN.setText("Login");
        userLoginBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userLoginBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loginPopupLayout = new javax.swing.GroupLayout(loginPopup);
        loginPopup.setLayout(loginPopupLayout);
        loginPopupLayout.setHorizontalGroup(
            loginPopupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPopupLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(loginPopupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(userLoginBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(loginPopupLayout.createSequentialGroup()
                        .addGroup(loginPopupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                        .addGap(69, 69, 69)
                        .addGroup(loginPopupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(userNameFeild)
                            .addComponent(passField, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        loginPopupLayout.setVerticalGroup(
            loginPopupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPopupLayout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(loginPopupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(userNameFeild)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(loginPopupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(passField))
                .addGap(27, 27, 27)
                .addComponent(userLoginBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(216, 216, 216)
                .addComponent(loginPopup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(242, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(loginPopup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        tab.addTab("tab4", homePanel);

        jPanel9.setBackground(new java.awt.Color(231, 235, 239));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1045, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 602, Short.MAX_VALUE)
        );

        tab.addTab("tab6", jPanel9);

        jPanel3.add(tab, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 1060, 640));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cure_hospital/asset/finalLogo.png"))); // NOI18N
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 190, 140));

        userNameShowLab.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        userNameShowLab.setText("User :");
        jPanel3.add(userNameShowLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 580, 60, 30));

        userNameShow.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        userNameShow.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel3.add(userNameShow, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 580, 110, 30));

        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 190, 140));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1310, 700));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //Admin login button feature in Home
    private void admitPatientBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_admitPatientBTNActionPerformed
        tab.setSelectedIndex(0);
        

    }//GEN-LAST:event_admitPatientBTNActionPerformed

    //Add patient button
    private void addDiagonosisBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDiagonosisBTNActionPerformed
        tab.setSelectedIndex(1);
        showAllData();

    }//GEN-LAST:event_addDiagonosisBTNActionPerformed

    private void patientHistoryBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientHistoryBTNActionPerformed
        tab.setSelectedIndex(2);
        showAllData();
    }//GEN-LAST:event_patientHistoryBTNActionPerformed

    private void employeeInfoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeInfoBtnActionPerformed
        tab.setSelectedIndex(3);
        userDetails();
    }//GEN-LAST:event_employeeInfoBtnActionPerformed

    private void dashBoardBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashBoardBTNActionPerformed
        tab.setSelectedIndex(4);
    }//GEN-LAST:event_dashBoardBTNActionPerformed

    private void logOutBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutBTNActionPerformed
        logout();
    }//GEN-LAST:event_logOutBTNActionPerformed

    private void rFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rFemaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rFemaleActionPerformed

    private void btnAdmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdmitActionPerformed
        addPatient();
        try {
            showAdmitedPatient();
//get local date**************
//        Date date = Date.valueOf(LocalDate.now());
//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        String text = df.format(date);
//
//        //get local time**************
//        LocalTime localtime = LocalTime.now();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
//        String time = localtime.format(dateTimeFormatter);
//
//        String sqlPatient = "insert into patient(name,contact,age,gender,blood_group,address,major_disease,admit_date,admit_time) values(?,?,?,?,?,?,?,?,?)";
//        try {
//            pst = db.getCon().prepareStatement(sqlPatient);
//
//            pst.setString(1, txtPName.getText().trim());
//            pst.setString(2, txtPContact.getText());
//            pst.setInt(3, Integer.parseInt(txtPatAge.getText()));
//            pst.setString(4, buttonGroup1.getSelection().getActionCommand());
//            pst.setString(5, bloodCombo.getSelectedItem().toString());
//            pst.setString(6, txtPAddress.getText());
//            pst.setString(7, txtPMajorDisaese.getText());
//            pst.setString(8, text);
//            pst.setString(9, time);
//            pst.executeUpdate();
//            pst.close();
//            db.getCon().close();
//
//            JOptionPane.showMessageDialog(this, "Patient Successfully Admited");
//        } catch (SQLException ex) {
//            Logger.getLogger(CureHospital.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//                   String patientIDsql =" select max(patient_ID) from patient";
//                    pst = db.getCon().prepareStatement(patientIDsql);
//        
//                    rs = pst.executeQuery();
//        
//                    if (rs.next()) {
//                    patientID =  rs.getInt("patient_ID");
//        
//                    }
//        
//                    pst.close();
//                    db.getCon().close();
//                    rs.close();
//        
//                    System.out.println(patientID);
//        JOptionPane.showMessageDialog(this, "Patient Successfully Admited");
//
//        clear();
        } catch (SQLException ex) {
            Logger.getLogger(ViewHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAdmitActionPerformed

    private void searChIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searChIDActionPerformed
        
        String columnsTwo[] = {"Patient ID ", "Name", "Contact", "Age", "Gender", "Blood Group", "Major Disease", "Address"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsTwo);
        tableModel.setModel(model);

        // for searching ID
        String sql = "select patient_ID,name, contact, age, gender, blood_group,major_disease,address from patient where patient_ID=?";
        
        try {
            pst = db.getCon().prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtPatientIDField.getText().trim()));
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                int patient_ID = rs.getInt("patient_ID");
                String name = rs.getString("name");
                String contact = rs.getString("contact");
                String age = rs.getString("age");
                String gender = rs.getString("gender");
                String blood = rs.getString("blood_group");
                String major_disease = rs.getString("major_disease");
                String address = rs.getString("address");
                
                model.addRow(new Object[]{patient_ID, name, contact, age, gender, blood, major_disease, address});
                labelIDCheck.setVisible(false);
                txtSymtops.setEditable(true);
                
                comboDoctor.setEditable(true);
                checkWord.setEnabled(true);
                btnAdd.setEnabled(true);
                
            } else {
                labelIDCheck.setVisible(true);
                txtSymtops.setEditable(false);
                
                comboDoctor.setEditable(false);
                choiceWord.setEditable(false);
                checkWord.setEnabled(false);
                btnAdd.setEnabled(false);
                
            }
            
            pst.close();
            db.getCon().close();
            
        } catch (SQLException ex) {
            Logger.getLogger(ViewHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_searChIDActionPerformed

    private void checkWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkWordActionPerformed
        if (checkWord.isSelected()) {
            
            choiceWord.setVisible(true);
        } else {
            
            choiceWord.setVisible(false);
        }
    }//GEN-LAST:event_checkWordActionPerformed

    private void searChTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searChTestActionPerformed
        try {
            String testName = test.getSelectedItem().toString();
            String sqlTest = "select price from test where test_name='" + testName + "';";
            double testFees = Double.parseDouble(txtTestFee.getText());
            
            pst = db.getCon().prepareStatement(sqlTest);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                String fees = rs.getString("price");
                testFees += Double.parseDouble(fees);
            }
            txtTestFee.setText(Double.toString(testFees));
            pst.close();
            db.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(ViewHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }//GEN-LAST:event_searChTestActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        
        JOptionPane.showMessageDialog(this, "Diagonossis Info Successfully Added");
    }//GEN-LAST:event_btnAddActionPerformed

    private void searchPatientKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchPatientKeyReleased
        String searchP = searchPatient.getText().toLowerCase();
        searchPatient(searchP);
    }//GEN-LAST:event_searchPatientKeyReleased

    private void userLoginBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userLoginBTNActionPerformed
        login();
        

    }//GEN-LAST:event_userLoginBTNActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewHospital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewHospital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewHospital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewHospital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewHospital().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addDiagoPanel;
    private javax.swing.JButton addDiagonosisBTN;
    private javax.swing.JTable adminsTableModel;
    private javax.swing.JButton admitPatientBTN;
    private javax.swing.JPanel admitPatientTab;
    public javax.swing.JComboBox<String> bloodCombo;
    private javax.swing.JButton btnAdd;
    private javax.swing.JTabbedPane btnAdmin;
    private javax.swing.JButton btnAdmit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox checkWord;
    private javax.swing.JComboBox<String> choiceWord;
    private javax.swing.JComboBox<String> comboDoctor;
    private javax.swing.JButton dashBoardBTN;
    private javax.swing.JPanel empInfoTab;
    private javax.swing.JButton employeeInfoBtn;
    private javax.swing.JPanel homePanel;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JLabel labelIDCheck;
    private javax.swing.JLabel labelPatientExistChack;
    private javax.swing.JButton logOutBTN;
    private javax.swing.JPanel loginPopup;
    private javax.swing.JPasswordField passField;
    private javax.swing.JButton patientHistoryBTN;
    private javax.swing.JTable patientHistoryTable;
    private javax.swing.JPanel patuentHistorytTab;
    public javax.swing.JRadioButton rFemale;
    private javax.swing.JRadioButton rMale;
    public javax.swing.JRadioButton rOthers;
    private javax.swing.JButton resetBTN;
    private javax.swing.JTable sampleTable;
    private javax.swing.JButton searChID;
    private javax.swing.JButton searChTest;
    private javax.swing.JTextField searchPatient;
    public javax.swing.JLabel showDate;
    public javax.swing.JLabel showTime;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTable tableModel;
    private javax.swing.JComboBox<String> test;
    public javax.swing.JTextField txtAdmitFee;
    private javax.swing.JTextField txtDoctorFee;
    private javax.swing.JTextField txtMedicineFee;
    public javax.swing.JTextArea txtPAddress;
    public javax.swing.JTextField txtPContact;
    public javax.swing.JTextField txtPMajorDisaese;
    public javax.swing.JTextField txtPName;
    public javax.swing.JTextField txtPatAge;
    private javax.swing.JTextField txtPatientIDField;
    private javax.swing.JTextField txtSerrviceFee;
    public javax.swing.JTextField txtSymtops;
    private javax.swing.JTextField txtTestFee;
    private javax.swing.JTextField txtTransportFee;
    private javax.swing.JTextField txtWordFee;
    private javax.swing.JButton updateBTN;
    private javax.swing.JButton userLoginBTN;
    private javax.swing.JTextField userNameFeild;
    private javax.swing.JLabel userNameShow;
    private javax.swing.JLabel userNameShowLab;
    // End of variables declaration//GEN-END:variables
}
