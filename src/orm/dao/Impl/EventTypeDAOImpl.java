/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.EventType;
import orm.dao.EventTypeDAO;

/**
 *
 * @author jean pierre
 */
public class EventTypeDAOImpl implements EventTypeDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateEventType(EventType eventType) {
        return dao.saveObject(eventType);
    }

    @Override
    public EventType findByDesignation(String designation) {
        return (EventType) dao.getSession().createCriteria(EventType.class).add(Restrictions.eq("designation", designation)).uniqueResult();
    }

    @Override
    public List<EventType> findAll() {
        return dao.getSession().createCriteria(EventType.class).list();
    }

    @Override
    public EventType findById(int id) {
        return (EventType) dao.getSession().get(EventType.class, id);
    }
}
