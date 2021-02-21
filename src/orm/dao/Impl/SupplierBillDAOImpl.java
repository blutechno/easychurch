/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Supplier;
import orm.SupplyBill;
import orm.dao.SupplierBillDAO;

/**
 *
 * @author jean pierre
 */
public class SupplierBillDAOImpl implements SupplierBillDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateSupplierBill(SupplyBill supplierBill) {
        return dao.saveObject(supplierBill);
    }

    @Override
    public SupplyBill findByBill(String billNumber) {
        return (SupplyBill) dao.getSession().createCriteria(SupplyBill.class).add(Restrictions.eq("billNumber", billNumber)).uniqueResult();
    }

    @Override
    public List<SupplyBill> findBySupplier(String designation) {
        Supplier supplier = (Supplier) dao.getSession().createCriteria(Supplier.class).add(Restrictions.eq("designation", designation)).uniqueResult();
        return dao.getSession().createCriteria(SupplyBill.class).add(Restrictions.eq("supplier", supplier)).list();
    }

    @Override
    public List<SupplyBill> findAll() {
        return dao.getSession().createCriteria(SupplyBill.class).list();
    }
}
