/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Supplier;
import orm.dao.SupplierDAO;

/**
 *
 * @author jean pierre
 */
public class SupplierDAOImpl implements SupplierDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateSupplier(Supplier supplier) {
        return dao.saveObject(supplier);
    }

    @Override
    public Supplier findByDesignation(String designation) {
        return (Supplier) dao.getSession().createCriteria(Supplier.class).add(Restrictions.eq("designation", designation)).uniqueResult();
    }

    @Override
    public List<Supplier> findAll() {
        return dao.getSession().createCriteria(Supplier.class).list();
    }
}
