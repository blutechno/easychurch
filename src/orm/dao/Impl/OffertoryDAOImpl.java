/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Offertory;
import orm.OffertoryType;
import orm.dao.OffertoryDAO;

/**
 *
 * @author jean pierre
 */
public class OffertoryDAOImpl implements OffertoryDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateOffertory(Offertory offertory) {
        return dao.saveObject(offertory);
    }

    @Override
    public List<Offertory> findByDate(Date recordDate) {
        return dao.getSession().createCriteria(Offertory.class).add(Restrictions.eq("recordDate", recordDate)).list();
    }

    @Override
    public List<Offertory> findByType(String oType) {
        OffertoryType offertoryType = (OffertoryType) dao.getSession().createCriteria(OffertoryType.class).add(Restrictions.eq("offertoryTypeName", oType)).uniqueResult();
        return dao.getSession().createCriteria(Offertory.class).add(Restrictions.eq("offertoryType", offertoryType)).list();
    }

    @Override
    public List<Offertory> findAll() {
        return dao.getSession().createCriteria(Offertory.class).list();
    }

    @Override
    public Offertory findById(int id) {
        return (Offertory) dao.getSession().get(Offertory.class, id);
    }
}
