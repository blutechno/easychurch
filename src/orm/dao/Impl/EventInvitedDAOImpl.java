/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.EventDetail;
import orm.EventInvited;
import orm.dao.EventInvitedDAO;

/**
 *
 * @author jean pierre
 */
public class EventInvitedDAOImpl implements EventInvitedDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateEventInvited(EventInvited eventInvited) {
        return dao.saveObject(eventInvited);
    }

    @Override
    public boolean saveOrUpdateEventInvited(List<EventInvited> list) {
        boolean done = false;
        for (EventInvited i : list) {
            done = saveOrUpdateEventInvited(i);
        }
        return done;
    }

    @Override
    public List<EventInvited> findByEventCode(String eventCode) {
        EventDetail eventDetail = (EventDetail) dao.getSession().createCriteria(EventDetail.class).add(Restrictions.eq("eventCode", eventCode)).uniqueResult();
        return dao.getSession().createCriteria(EventInvited.class).add(Restrictions.eq("eventDetail", eventDetail)).list();
    }

    @Override
    public List<EventInvited> findAll() {
        return dao.getSession().createCriteria(EventInvited.class).list();
    }
}
