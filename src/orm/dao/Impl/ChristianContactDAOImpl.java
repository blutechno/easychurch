/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Christian;
import orm.ChristianContact;
import orm.dao.ChristianContactDAO;

/**
 *
 * @author jean pierre
 */
public class ChristianContactDAOImpl implements ChristianContactDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateChristianContact(ChristianContact contact) {
        return dao.saveObject(contact);
    }

    @Override
    public ChristianContact findByChristian(String firstName, String lastName) {
        Christian christian = (Christian) dao.getSession().createCriteria(Christian.class).add(Restrictions.and(Restrictions.eq("firstName", firstName), Restrictions.eq("lastName", lastName))).uniqueResult();
        return (ChristianContact) dao.getSession().createCriteria(ChristianContact.class).add(Restrictions.eq("christian", christian)).uniqueResult();
    }

    @Override
    public List<ChristianContact> findAll() {
        return dao.getSession().createCriteria(ChristianContact.class).list();
    }

    @Override
    public ChristianContact findById(int id) {
        return (ChristianContact) dao.getSession().get(ChristianContact.class, id);
    }
}
