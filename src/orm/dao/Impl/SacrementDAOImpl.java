/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Sacrement;
import orm.dao.SacrementDAO;

/**
 *
 * @author jean pierre
 */
public class SacrementDAOImpl implements SacrementDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateSacrement(Sacrement sacrement) {
        return dao.saveObject(sacrement);
    }

    @Override
    public Sacrement findByName(String sacrementName) {
        return (Sacrement) dao.getSession().createCriteria(Sacrement.class).add(Restrictions.eq("sacramentName", sacrementName)).uniqueResult();
    }

    @Override
    public List<Sacrement> findAll() {
        return dao.getSession().createCriteria(Sacrement.class).list();
    }

    @Override
    public boolean delete(String sacrementName) {
        return dao.deleteObject(findByName(sacrementName));
    }

    @Override
    public Sacrement findById(int id) {
        return (Sacrement) dao.getSession().get(Sacrement.class, id);
    }
}
