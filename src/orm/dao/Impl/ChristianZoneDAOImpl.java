/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Christian;
import orm.ChristianZone;
import orm.Zone;
import orm.dao.ChristianZoneDAO;

/**
 *
 * @author jean pierre
 */
public class ChristianZoneDAOImpl implements ChristianZoneDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateChristianZone(ChristianZone christianZone) {
        return dao.saveObject(christianZone);
    }

    @Override
    public boolean saveOrUpdateChristianZone(List<ChristianZone> christianZones) {
        boolean done = false;
        for (ChristianZone cZone : christianZones) {
            done = saveOrUpdateChristianZone(cZone);
        }
        return done;
    }

    @Override
    public List<ChristianZone> findByZone(String zoneName) {
        Zone zone = (Zone) dao.getSession().createCriteria(Zone.class).add(Restrictions.eq("zoneName", zoneName)).uniqueResult();
        return dao.getSession().createCriteria(ChristianZone.class).add(Restrictions.eq("zone", zone)).list();
    }

    @Override
    public ChristianZone findById(int id) {
        return (ChristianZone) dao.getSession().get(ChristianZone.class, id);
    }

    @Override
    public List<ChristianZone> findByChristian(String firstName, String lastName) {
        Christian christian = (Christian) dao.getSession().createCriteria(Christian.class).add(Restrictions.and(Restrictions.eq("firstName", firstName), Restrictions.eq("lastName", lastName))).uniqueResult();
        return dao.getSession().createCriteria(ChristianZone.class).add(Restrictions.eq("christian", christian)).list();
    }

    @Override
    public List<ChristianZone> findAll() {
        return dao.getSession().createCriteria(ChristianZone.class).list();
    }

    @Override
    public List<ChristianZone> findByChristian(String christianPin) {
        Christian christian = (Christian) dao.getSession().createCriteria(Christian.class).add(Restrictions.eq("christianPin", christianPin)).uniqueResult();
        return dao.getSession().createCriteria(ChristianZone.class).add(Restrictions.eq("christian", christian)).list();
    }

    @Override
    public ChristianZone findActive(String christianPin) {
        ChristianZone result = new ChristianZone();
        for (ChristianZone cZone : findByChristian(christianPin)) {
            if (cZone.getIsActive() == 'Y') {
                result = cZone;
                break;
            }
        }
        return result;
    }
}
