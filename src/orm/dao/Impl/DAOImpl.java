/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author jean pierre
 */
public class DAOImpl {

    private SessionFactory sessionFactory;
    private Session session;
    private static DAOImpl instance;

    private DAOImpl() {
        //sessionFactory = new Configuration().configure().buildSessionFactory();
        //session = sessionFactory.openSession();
        setConnection();
    }

    private void setConnection() {
//        PathFinder finder = new PathFinder();
        Properties prop = new Properties();
        Configuration cfg = new Configuration();
        String url = "";
        String pwd = "";
        try {
            //load a properties file
            prop.load(new FileInputStream("C:/e_church_reports/dbConfig.properties"));
            url = prop.getProperty("database");
            if (!prop.getProperty("dbpassword").equals("")) {
                pwd = prop.getProperty("dbpassword");
            }
            cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            cfg.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            cfg.setProperty("hibernate.connection.url", "jdbc:mysql://" + url + ":3306/e_church");
            cfg.setProperty("hibernate.connection.username", "root");
            if (!pwd.isEmpty()) {
                cfg.setProperty("hibernate.connection.password", pwd);
            }
            cfg = addResources(cfg);
            sessionFactory = cfg.buildSessionFactory();
            session = sessionFactory.openSession();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Configuration addResources(Configuration cfg) {
        cfg.addResource("orm/User.hbm.xml");
        cfg.addResource("orm/IncomeType.hbm.xml");
        cfg.addResource("orm/SupplierItem.hbm.xml");
        cfg.addResource("orm/SupplyBill.hbm.xml");
        cfg.addResource("orm/Zone.hbm.xml");
        cfg.addResource("orm/Sacrement.hbm.xml");
        cfg.addResource("orm/Wedding.hbm.xml");
        cfg.addResource("orm/LoanType.hbm.xml");
        cfg.addResource("orm/SupplyBillDetail.hbm.xml");
        cfg.addResource("orm/Pastor.hbm.xml");
        cfg.addResource("orm/ChristianSacrement.hbm.xml");
        cfg.addResource("orm/EventInvited.hbm.xml");
        cfg.addResource("orm/Supplier.hbm.xml");
        cfg.addResource("orm/Role.hbm.xml");
        cfg.addResource("orm/UserLog.hbm.xml");
        cfg.addResource("orm/OffertoryType.hbm.xml");
        cfg.addResource("orm/LoanRefunding.hbm.xml");
        cfg.addResource("orm/Loan.hbm.xml");
        cfg.addResource("orm/UserRole.hbm.xml");
        cfg.addResource("orm/PrayerRequest.hbm.xml");
        cfg.addResource("orm/MinistryDetail.hbm.xml");
        cfg.addResource("orm/EventIncome.hbm.xml");
        cfg.addResource("orm/ParishPastor.hbm.xml");
        cfg.addResource("orm/Outflow.hbm.xml");
        cfg.addResource("orm/EventType.hbm.xml");
        cfg.addResource("orm/ChristianContact.hbm.xml");
        cfg.addResource("orm/Parish.hbm.xml");
        cfg.addResource("orm/EventDetail.hbm.xml");
        cfg.addResource("orm/Income.hbm.xml");
        cfg.addResource("orm/ChristianZone.hbm.xml");
        cfg.addResource("orm/Offertory.hbm.xml");
        cfg.addResource("orm/Ministry.hbm.xml");
        cfg.addResource("orm/Christian.hbm.xml");
        cfg.addResource("orm/OutflowType.hbm.xml");
        return cfg;
    }

    public static DAOImpl getInstance() {
        if (instance == null) {
            instance = new DAOImpl();
        }
        return instance;
    }

    public Session getSession() {
        return session;
    }

    private void begin() {
        session.beginTransaction();
    }

    private void commit() {
        session.getTransaction().commit();
    }

    private void rollback() {
        session.getTransaction().rollback();
    }

    public boolean saveObject(Object object) {
        try {
            begin();
            session.saveOrUpdate(object);
            commit();
            return true;
        } catch (Exception ex) {
            rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean deleteObject(Object object) {
        try {
            begin();
            session.delete(object);
            commit();
            return true;
        } catch (Exception ex) {
            rollback();
            return false;
        }
    }
}
