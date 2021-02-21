/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Supplier;
import orm.SupplierItem;
import orm.dao.SupplierItemDAO;

/**
 *
 * @author jean pierre
 */
public class SupplierItemDAOImpl implements SupplierItemDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateSupplierItem(SupplierItem supplierItem) {
        return dao.saveObject(supplierItem);
    }

    @Override
    public boolean saveOrUpdateSupplierItem(List<SupplierItem> items) {
        boolean done = false;
        for (SupplierItem item : items) {
            done = saveOrUpdateSupplierItem(item);
        }
        return done;
    }

    @Override
    public List<SupplierItem> findBySupplier(String designation) {
        Supplier supplier = (Supplier) dao.getSession().createCriteria(Supplier.class).add(Restrictions.eq("designation", designation)).uniqueResult();
        return dao.getSession().createCriteria(SupplierItem.class).add(Restrictions.eq("supplier", supplier)).list();
    }

    @Override
    public List<SupplierItem> findBySupplyDate(Date supplyDate) {
        return dao.getSession().createCriteria(SupplierItem.class).add(Restrictions.eq("supplyDate", supplyDate)).list();
    }

    @Override
    public List<SupplierItem> findAll() {
        return dao.getSession().createCriteria(SupplierItem.class).list();
    }
}
