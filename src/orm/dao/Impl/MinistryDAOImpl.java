/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Ministry;
import orm.dao.MinistryDAO;

/**
 *
 * @author jean pierre
 */
public class MinistryDAOImpl implements MinistryDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateMinistry(Ministry ministry) {
        return dao.saveObject(ministry);
    }

    @Override
    public Ministry findByDesignation(String designation) {
        return (Ministry) dao.getSession().createCriteria(Ministry.class).add(Restrictions.eq("designation", designation)).uniqueResult();
    }

    @Override
    public List<Ministry> findByStatus(char status) {
        return dao.getSession().createCriteria(Ministry.class).add(Restrictions.eq("status", status)).list();
    }

    @Override
    public List<Ministry> findAll() {
        return dao.getSession().createCriteria(Ministry.class).list();
    }
}
