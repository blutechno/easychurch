/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Ministry;
import orm.MinistryDetail;
import orm.dao.MinistryDetailDAO;

/**
 *
 * @author jean pierre
 */
public class MinistryDetailDAOImpl implements MinistryDetailDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateMinistiryDetail(MinistryDetail ministryDetail) {
        return dao.saveObject(ministryDetail);
    }

    @Override
    public List<MinistryDetail> findByDate(Date heldOn) {
        return dao.getSession().createCriteria(MinistryDetail.class).add(Restrictions.eq("heldOn", heldOn)).list();
    }

    @Override
    public List<MinistryDetail> findByMinistry(String designation) {
        Ministry ministry = (Ministry) dao.getSession().createCriteria(Ministry.class).add(Restrictions.eq("designation", designation)).uniqueResult();
        return dao.getSession().createCriteria(MinistryDetail.class).add(Restrictions.eq("designation", designation)).list();
    }

    @Override
    public List<MinistryDetail> findByMainSpeaker(String mainSpeaker) {
        return dao.getSession().createCriteria(MinistryDetail.class).add(Restrictions.eq("mainSpeaker", mainSpeaker)).list();
    }

    @Override
    public List<MinistryDetail> findAll() {
        return dao.getSession().createCriteria(MinistryDetail.class).list();
    }
}
