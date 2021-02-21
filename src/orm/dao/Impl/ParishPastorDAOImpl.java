/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Parish;
import orm.ParishPastor;
import orm.dao.ParishPastorDAO;

/**
 *
 * @author jean pierre
 */
public class ParishPastorDAOImpl implements ParishPastorDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateParishPastor(ParishPastor parishPastor) {
        return dao.saveObject(parishPastor);
    }

    @Override
    public List<ParishPastor> findByParish(String parishName) {
        Parish parish = (Parish) dao.getSession().createCriteria(Parish.class).add(Restrictions.eq("parishName", parishName)).uniqueResult();
        return dao.getSession().createCriteria(ParishPastor.class).add(Restrictions.eq("parish", parish)).list();
    }

    @Override
    public List<ParishPastor> findAll() {
        return dao.getSession().createCriteria(ParishPastor.class).list();
    }
}
