/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Christian;
import orm.ChristianSacrement;
import orm.ChristianSacrementId;
import orm.Sacrement;
import orm.dao.ChristianSacrementDAO;

/**
 *
 * @author jean pierre
 */
public class ChristianSacrementDAOImpl implements ChristianSacrementDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateChristianSacrement(ChristianSacrement christianSacrement) {
        return dao.saveObject(christianSacrement);
    }

    @Override
    public boolean saveOrUpdateChristianSacrement(List<ChristianSacrement> list) {
        boolean done = false;
        for (ChristianSacrement christianSacrement : list) {
            done = saveOrUpdateChristianSacrement(christianSacrement);
        }
        return done;
    }

    @Override
    public ChristianSacrement findBId(ChristianSacrementId id) {
        return (ChristianSacrement) dao.getSession().get(ChristianSacrement.class, id);
    }

    @Override
    public List<ChristianSacrement> findByChristian(String firstName, String lastName) {
        Christian christian = (Christian) dao.getSession().createCriteria(Christian.class).add(Restrictions.and(Restrictions.eq("firstName", firstName), Restrictions.eq("lastName", lastName))).uniqueResult();
        return dao.getSession().createCriteria(ChristianSacrement.class).add(Restrictions.eq("christian", christian)).list();
    }

    @Override
    public List<ChristianSacrement> findBySacrement(String sacramentName) {
        Sacrement sacrement = (Sacrement) dao.getSession().createCriteria(Sacrement.class).add(Restrictions.eq("sacramentName", sacramentName)).uniqueResult();
        return dao.getSession().createCriteria(ChristianSacrement.class).add(Restrictions.eq("sacrement", sacrement)).list();
    }

    @Override
    public List<ChristianSacrement> findByDate(Date date) {
        return dao.getSession().createCriteria(ChristianSacrement.class).add(Restrictions.eq("celebrationDate", date)).list();
    }

    @Override
    public List<ChristianSacrement> findAll() {
        return dao.getSession().createCriteria(ChristianSacrement.class).list();
    }

    @Override
    public List<ChristianSacrement> findByDateAndSacrement(Date date, String sacrementName) {
        List<ChristianSacrement> result = new ArrayList<ChristianSacrement>();
        for (ChristianSacrement cs : findByDate(date)) {
            if (cs.getSacrement().getSacramentName().equals(sacrementName)) {
                result.add(cs);
            }
        }
        return result;
    }

    @Override
    public ChristianSacrement findBySacrementAndChristian(String sacrementName, String christianPin) {
        ChristianSacrement result = new ChristianSacrement();
        for (ChristianSacrement cs : findBySacrement(sacrementName)) {
            if (cs.getChristian().getChristianPin().equals(christianPin)) {
                result = cs;
                break;
            }
        }
        return result;
    }
}
