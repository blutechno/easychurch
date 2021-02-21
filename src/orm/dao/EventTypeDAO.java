/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.EventType;

/**
 *
 * @author jean pierre
 */
public interface EventTypeDAO {

    public boolean saveOrUpdateEventType(EventType eventType);
    
    public EventType findById(int id);

    public EventType findByDesignation(String designation);

    public List<EventType> findAll();
}
