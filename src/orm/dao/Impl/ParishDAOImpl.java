/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Parish;
import orm.dao.ParishDAO;

/**
 *
 * @author jean pierre
 */
public class ParishDAOImpl implements ParishDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateParish(Parish parish) {
        return dao.saveObject(parish);
    }

    @Override
    public Parish findByName(String parishName) {
        return (Parish) dao.getSession().createCriteria(Parish.class).add(Restrictions.eq("parishName", parishName)).uniqueResult();
    }

    @Override
    public List<Parish> findAll() {
        return dao.getSession().createCriteria(Parish.class).list();
    }

    @Override
    public Parish findById(int id) {
        return (Parish) dao.getSession().get(Parish.class, id);
    }
}
