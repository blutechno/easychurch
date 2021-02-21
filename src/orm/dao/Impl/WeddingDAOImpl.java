/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Wedding;
import orm.dao.WeddingDAO;

/**
 *
 * @author jean pierre
 */
public class WeddingDAOImpl implements WeddingDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateWedding(Wedding wedding) {
        return dao.saveObject(wedding);
    }

    @Override
    public Wedding findByPartners(String husband, String wife) {
        List<Wedding> weds = dao.getSession().createCriteria(Wedding.class).add(Restrictions.eq("husband", husband)).list();
        Wedding result = new Wedding();
        for (Wedding w : weds) {
            if (w.getWife().equals(wife)) {
                result = w;
                break;
            }
        }
        return result;
    }

    @Override
    public List<Wedding> findAll() {
        return dao.getSession().createCriteria(Wedding.class).list();
    }

    @Override
    public Wedding findByWeddingCode(String weddingCode) {
        return (Wedding) dao.getSession().createCriteria(Wedding.class).add(Restrictions.eq("weddingCode", weddingCode)).uniqueResult();
    }
}
