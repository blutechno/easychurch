/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.SupplyBill;
import orm.SupplyBillDetail;
import orm.dao.SupplyBillDetailDAO;

/**
 *
 * @author jean pierre
 */
public class SupplierBillDetailDAOImpl implements SupplyBillDetailDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateSupplyBillDetail(SupplyBillDetail supplyBillDetail) {
        return dao.saveObject(supplyBillDetail);
    }

    @Override
    public boolean saveOrUpdateSupplyBillDetail(List<SupplyBillDetail> details) {
        boolean done = false;
        for (SupplyBillDetail detail : details) {
            done = saveOrUpdateSupplyBillDetail(detail);
        }
        return done;
    }

    @Override
    public List<SupplyBillDetail> findByBill(String billNumber) {
        SupplyBill bill = (SupplyBill) dao.getSession().createCriteria(SupplyBill.class).add(Restrictions.eq("billNumber", billNumber)).uniqueResult();
        return dao.getSession().createCriteria(SupplyBillDetail.class).add(Restrictions.eq("supplyBill", bill)).list();
    }

    @Override
    public List<SupplyBillDetail> findAll() {
        return dao.getSession().createCriteria(SupplyBillDetail.class).list();
    }
}
