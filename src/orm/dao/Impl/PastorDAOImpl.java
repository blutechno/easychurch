/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Pastor;
import orm.dao.PastorDAO;

/**
 *
 * @author jean pierre
 */
public class PastorDAOImpl implements PastorDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdatePastor(Pastor pastor) {
        return dao.saveObject(pastor);
    }

    @Override
    public Pastor findByCode(String code) {
        return (Pastor) dao.getSession().createCriteria(Pastor.class).add(Restrictions.eq("pastorshipCode", code)).uniqueResult();
    }

    @Override
    public Pastor findByNames(String firstName, String lastName) {
        List<Pastor> temp = dao.getSession().createCriteria(Pastor.class).add(Restrictions.like("firstName", "%" + firstName + "%")).list();
        Pastor pastor = new Pastor();
        for (Pastor p : temp) {
            if (p.getLastName().equalsIgnoreCase(lastName)) {
                pastor = p;
                break;
            }
        }
        return pastor;
    }

    @Override
    public List<Pastor> findByGender(char sex) {
        return dao.getSession().createCriteria(Pastor.class).add(Restrictions.eq("gender", sex)).list();
    }

    @Override
    public List<Pastor> findAll() {
        return dao.getSession().createCriteria(Pastor.class).list();
    }
}
