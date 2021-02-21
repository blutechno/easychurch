/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.EventDetail;
import orm.EventType;
import orm.dao.EventDetailDAO;

/**
 *
 * @author jean pierre
 */
public class EventDetailDAOImpl implements EventDetailDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateEventDetail(EventDetail eventDetail) {
        return dao.saveObject(eventDetail);
    }

    @Override
    public EventDetail findByEventCode(String eventCode) {
        return (EventDetail) dao.getSession().createCriteria(EventDetail.class).add(Restrictions.eq("eventCode", eventCode)).uniqueResult();
    }

    @Override
    public List<EventDetail> findByEventType(String designation) {
        EventType eventType = (EventType) dao.getSession().createCriteria(EventType.class).add(Restrictions.eq("designation", designation)).uniqueResult();
        return dao.getSession().createCriteria(EventDetail.class).add(Restrictions.eq("eventType", eventType)).list();
    }

    @Override
    public List<EventDetail> findAll() {
        return dao.getSession().createCriteria(EventDetail.class).list();
    }
}
