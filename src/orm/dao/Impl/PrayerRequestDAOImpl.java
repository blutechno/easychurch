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
import orm.PrayerRequest;
import orm.dao.PrayerRequestDAO;

/**
 *
 * @author jean pierre
 */
public class PrayerRequestDAOImpl implements PrayerRequestDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdatePrayerRequest(PrayerRequest prayerRequest) {
        return dao.saveObject(prayerRequest);
    }

    @Override
    public List<PrayerRequest> findByRequestor(String firstName, String lastName) {
        Christian christian = (Christian) dao.getSession().createCriteria(Christian.class).add(Restrictions.and(Restrictions.eq("firstName", firstName), Restrictions.eq("lastName", lastName))).uniqueResult();
        return dao.getSession().createCriteria(PrayerRequest.class).add(Restrictions.eq("christian", christian)).list();
    }

    @Override
    public List<PrayerRequest> findByDates(Date dateFrom, Date dateTo) {
        return dao.getSession().createCriteria(PrayerRequest.class).add(Restrictions.between("requestDate", dateFrom, dateTo)).list();
    }

    @Override
    public List<PrayerRequest> findAll() {
        return dao.getSession().createCriteria(PrayerRequest.class).list();
    }

    @Override
    public List<PrayerRequest> findByPreferedDatesAndRequest(Date dateFrom, Date dateTo, String christianPin) {
        List<PrayerRequest> result = new ArrayList<PrayerRequest>();
        for (PrayerRequest request : findByDates(dateFrom, dateTo)) {
            if (request.getChristian().getChristianPin().equals(christianPin)) {
                result.add(request);
            }
        }
        return result;
    }

    @Override
    public List<PrayerRequest> findByRequestor(String christianPin) {     
        Christian christian = (Christian) dao.getSession().createCriteria(Christian.class).add(Restrictions.eq("christianPin", christianPin)).uniqueResult();
        return dao.getSession().createCriteria(PrayerRequest.class).add(Restrictions.eq("christian", christian)).list();
   
    }
}
