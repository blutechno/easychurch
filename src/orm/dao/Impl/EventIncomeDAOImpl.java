/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.EventDetail;
import orm.EventIncome;
import orm.dao.EventIncomeDAO;

/**
 *
 * @author jean pierre
 */
public class EventIncomeDAOImpl implements EventIncomeDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateEventIncome(EventIncome eventIncome) {
        return dao.saveObject(eventIncome);
    }

    @Override
    public EventIncome findByEventCode(String eventCode) {
        EventDetail eventDetail = (EventDetail) dao.getSession().createCriteria(EventDetail.class).add(Restrictions.eq("eventCode", eventCode)).uniqueResult();
        return (EventIncome) dao.getSession().createCriteria(EventIncome.class).add(Restrictions.eq("eventDetail", eventDetail)).uniqueResult();
    }

    @Override
    public List<EventIncome> findDates(Date dateFrom, Date dateTo) {
        return dao.getSession().createCriteria(EventIncome.class).add(Restrictions.between("createdOn", dateFrom, dateTo)).list();
    }

    @Override
    public List<EventIncome> findAll() {
        return dao.getSession().createCriteria(EventIncome.class).list();
    }
}
