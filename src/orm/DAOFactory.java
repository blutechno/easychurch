/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import orm.dao.ChristianContactDAO;
import orm.dao.ChristianDAO;
import orm.dao.ChristianSacrementDAO;
import orm.dao.ChristianZoneDAO;
import orm.dao.EventDetailDAO;
import orm.dao.EventIncomeDAO;
import orm.dao.EventInvitedDAO;
import orm.dao.EventTypeDAO;
import orm.dao.Impl.ChristianContactDAOImpl;
import orm.dao.Impl.ChristianDAOImpl;
import orm.dao.Impl.ChristianSacrementDAOImpl;
import orm.dao.Impl.ChristianZoneDAOImpl;
import orm.dao.Impl.EventDetailDAOImpl;
import orm.dao.Impl.EventIncomeDAOImpl;
import orm.dao.Impl.EventInvitedDAOImpl;
import orm.dao.Impl.EventTypeDAOImpl;
import orm.dao.Impl.IncomeDAOImpl;
import orm.dao.Impl.IncomeTypeDAOImpl;
import orm.dao.Impl.LoanDAOImpl;
import orm.dao.Impl.LoanRefundingDAOImpl;
import orm.dao.Impl.LoanTypeDAOImpl;
import orm.dao.Impl.MinistryDAOImpl;
import orm.dao.Impl.MinistryDetailDAOImpl;
import orm.dao.Impl.OffertoryDAOImpl;
import orm.dao.Impl.OffertoryTypeDAOImpl;
import orm.dao.Impl.OutflowDAOImpl;
import orm.dao.Impl.OutflowTypeDAOImpl;
import orm.dao.Impl.ParishDAOImpl;
import orm.dao.Impl.ParishPastorDAOImpl;
import orm.dao.Impl.PastorDAOImpl;
import orm.dao.Impl.PrayerRequestDAOImpl;
import orm.dao.Impl.RoleDAOImpl;
import orm.dao.Impl.SacrementDAOImpl;
import orm.dao.Impl.SupplierBillDAOImpl;
import orm.dao.Impl.SupplierBillDetailDAOImpl;
import orm.dao.Impl.SupplierDAOImpl;
import orm.dao.Impl.SupplierItemDAOImpl;
import orm.dao.Impl.UserDAOImpl;
import orm.dao.Impl.UserLogDAOImpl;
import orm.dao.Impl.UserRoleDAOImpl;
import orm.dao.Impl.WeddingDAOImpl;
import orm.dao.Impl.ZoneDAOImpl;
import orm.dao.IncomeDAO;
import orm.dao.IncomeTypeDAO;
import orm.dao.LoanDAO;
import orm.dao.LoanRefundingDAO;
import orm.dao.LoanTypeDAO;
import orm.dao.MinistryDAO;
import orm.dao.MinistryDetailDAO;
import orm.dao.OffertoryDAO;
import orm.dao.OffertoryTypeDAO;
import orm.dao.OutflowDAO;
import orm.dao.OutflowTypeDAO;
import orm.dao.ParishDAO;
import orm.dao.ParishPastorDAO;
import orm.dao.PastorDAO;
import orm.dao.PrayerRequestDAO;
import orm.dao.RoleDAO;
import orm.dao.SacrementDAO;
import orm.dao.SupplierBillDAO;
import orm.dao.SupplierDAO;
import orm.dao.SupplierItemDAO;
import orm.dao.SupplyBillDetailDAO;
import orm.dao.UserDAO;
import orm.dao.UserLogDAO;
import orm.dao.UserRoleDAO;
import orm.dao.WeddingDAO;
import orm.dao.ZoneDAO;

/**
 *
 * @author jean pierre
 */
public class DAOFactory {

    private ChristianContactDAO christianContactDAO;
    private ChristianDAO christianDAO;
    private ChristianSacrementDAO christianSacrementDAO;
    private ChristianZoneDAO christianZoneDAO;
    private EventDetailDAO eventDetailDAO;
    private EventIncomeDAO eventIncomeDAO;
    private EventInvitedDAO eventInvitedDAO;
    private EventTypeDAO eventTypeDAO;
    private IncomeDAO incomeDAO;
    private IncomeTypeDAO incomeTypeDAO;
    private LoanDAO loanDAO;
    private LoanRefundingDAO loanRefundinDAO;
    private LoanTypeDAO loanTypeDAO;
    private MinistryDAO ministryDAO;
    private MinistryDetailDAO ministryDetailDAO;
    private OffertoryDAO offertoryDAO;
    private OffertoryTypeDAO offertoryTypeDAO;
    private OutflowDAO outflowDAO;
    private OutflowTypeDAO outflowTypeDAO;
    private ParishDAO parishDAO;
    private ParishPastorDAO parishPastorDAO;
    private PastorDAO pastorDAO;
    private PrayerRequestDAO prayerRequestDAO;
    private RoleDAO roleDAO;
    private SacrementDAO sacrementDAO;
    private SupplierBillDAO supplierBillDAO;
    private SupplierDAO supplierDAO;
    private SupplierItemDAO supplierItemDAO;
    private SupplyBillDetailDAO supplyBillDetailDAO;
    private UserDAO userDAO;
    private UserLogDAO userLogDAO;
    private UserRoleDAO userRoleDAO;
    private WeddingDAO weddingDAO;
    private ZoneDAO zoneDAO;
    private static DAOFactory instance;
    private static String machineUser = System.getProperty("user.name");
    private  String DRIVER = "com.mysql.jdbc.Driver";
    private  String url = "jdbc:mysql://localhost:3306/e_church";
     String pwd = "";
    // private final String BILL_REPORT = "C:\\Users\\" + machineUser + "\\Documents\\NetBeansProjects\\CMS\\src\\Entities\\DAO\\Impl\\GUI\\reports\\bill.jasper";
    private Connection connection;

    private DAOFactory() {
        String url_db = "";
       
        Properties prop = new Properties();
        try {
            //load a properties file
            prop.load(new FileInputStream("C:/e_church_reports/dbConfig.properties"));
            url_db = prop.getProperty("database");
            url = "jdbc:mysql://"+url_db+":3306/e_church";
            if (!prop.getProperty("dbpassword").equals("")) {
                pwd = prop.getProperty("dbpassword");
            }
        } catch (Exception ex) {
        }
        christianContactDAO = new ChristianContactDAOImpl();
        christianDAO = new ChristianDAOImpl();
        christianSacrementDAO = new ChristianSacrementDAOImpl();
        christianZoneDAO = new ChristianZoneDAOImpl();
        eventDetailDAO = new EventDetailDAOImpl();
        eventIncomeDAO = new EventIncomeDAOImpl();
        eventInvitedDAO = new EventInvitedDAOImpl();
        eventTypeDAO = new EventTypeDAOImpl();
        incomeDAO = new IncomeDAOImpl();
        incomeTypeDAO = new IncomeTypeDAOImpl();
        loanDAO = new LoanDAOImpl();
        loanRefundinDAO = new LoanRefundingDAOImpl();
        loanTypeDAO = new LoanTypeDAOImpl();
        ministryDAO = new MinistryDAOImpl();
        ministryDetailDAO = new MinistryDetailDAOImpl();
        offertoryDAO = new OffertoryDAOImpl();
        offertoryTypeDAO = new OffertoryTypeDAOImpl();
        outflowDAO = new OutflowDAOImpl();
        outflowTypeDAO = new OutflowTypeDAOImpl();
        parishDAO = new ParishDAOImpl();
        parishPastorDAO = new ParishPastorDAOImpl();
        pastorDAO = new PastorDAOImpl();
        prayerRequestDAO = new PrayerRequestDAOImpl();
        roleDAO = new RoleDAOImpl();
        sacrementDAO = new SacrementDAOImpl();
        supplierBillDAO = new SupplierBillDAOImpl();
        supplierDAO = new SupplierDAOImpl();
        supplierItemDAO = new SupplierItemDAOImpl();
        supplyBillDetailDAO = new SupplierBillDetailDAOImpl();
        userDAO = new UserDAOImpl();
        userLogDAO = new UserLogDAOImpl();
        userRoleDAO = new UserRoleDAOImpl();
        weddingDAO = new WeddingDAOImpl();
        zoneDAO = new ZoneDAOImpl();
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(url, "root", pwd);
        } catch (Exception ex) {
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public ChristianContactDAO getChristianContactDAO() {
        return christianContactDAO;
    }

    public ChristianDAO getChristianDAO() {
        return christianDAO;
    }

    public ChristianSacrementDAO getChristianSacrementDAO() {
        return christianSacrementDAO;
    }

    public ChristianZoneDAO getChristianZoneDAO() {
        return christianZoneDAO;
    }

    public EventDetailDAO getEventDetailDAO() {
        return eventDetailDAO;
    }

    public EventIncomeDAO getEventIncomeDAO() {
        return eventIncomeDAO;
    }

    public EventInvitedDAO getEventInvitedDAO() {
        return eventInvitedDAO;
    }

    public EventTypeDAO getEventTypeDAO() {
        return eventTypeDAO;
    }

    public IncomeDAO getIncomeDAO() {
        return incomeDAO;
    }

    public IncomeTypeDAO getIncomeTypeDAO() {
        return incomeTypeDAO;
    }

    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public LoanDAO getLoanDAO() {
        return loanDAO;
    }

    public LoanRefundingDAO getLoanRefundinDAO() {
        return loanRefundinDAO;
    }

    public LoanTypeDAO getLoanTypeDAO() {
        return loanTypeDAO;
    }

    public MinistryDAO getMinistryDAO() {
        return ministryDAO;
    }

    public MinistryDetailDAO getMinistryDetailDAO() {
        return ministryDetailDAO;
    }

    public OffertoryDAO getOffertoryDAO() {
        return offertoryDAO;
    }

    public OffertoryTypeDAO getOffertoryTypeDAO() {
        return offertoryTypeDAO;
    }

    public OutflowDAO getOutflowDAO() {
        return outflowDAO;
    }

    public OutflowTypeDAO getOutflowTypeDAO() {
        return outflowTypeDAO;
    }

    public ParishDAO getParishDAO() {
        return parishDAO;
    }

    public ParishPastorDAO getParishPastorDAO() {
        return parishPastorDAO;
    }

    public PastorDAO getPastorDAO() {
        return pastorDAO;
    }

    public PrayerRequestDAO getPrayerRequestDAO() {
        return prayerRequestDAO;
    }

    public RoleDAO getRoleDAO() {
        return roleDAO;
    }

    public SacrementDAO getSacrementDAO() {
        return sacrementDAO;
    }

    public SupplierBillDAO getSupplierBillDAO() {
        return supplierBillDAO;
    }

    public SupplierDAO getSupplierDAO() {
        return supplierDAO;
    }

    public SupplierItemDAO getSupplierItemDAO() {
        return supplierItemDAO;
    }

    public SupplyBillDetailDAO getSupplyBillDetailDAO() {
        return supplyBillDetailDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public UserLogDAO getUserLogDAO() {
        return userLogDAO;
    }

    public UserRoleDAO getUserRoleDAO() {
        return userRoleDAO;
    }

    public WeddingDAO getWeddingDAO() {
        return weddingDAO;
    }

    public ZoneDAO getZoneDAO() {
        return zoneDAO;
    }
}
