/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Zone;
import orm.dao.ZoneDAO;

/**
 *
 * @author jean pierre
 */
public class ZoneDAOImpl implements ZoneDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateZone(Zone zone) {
        return dao.saveObject(zone);
    }

    @Override
    public Zone findByName(String zoneName) {
        return (Zone) dao.getSession().createCriteria(Zone.class).add(Restrictions.eq("zoneName", zoneName)).uniqueResult();
    }

    @Override
    public List<Zone> findAll() {
        return dao.getSession().createCriteria(Zone.class).list();
    }

    @Override
    public boolean delete(String zoneName) {
        return dao.deleteObject(findByName(zoneName));
    }

    @Override
    public Zone findById(int id) {
        return (Zone) dao.getSession().get(Zone.class, id);
    }
}
