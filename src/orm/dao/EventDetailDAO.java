/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.EventDetail;

/**
 *
 * @author jean pierre
 */
public interface EventDetailDAO {

    public boolean saveOrUpdateEventDetail(EventDetail eventDetail);

    public EventDetail findByEventCode(String eventCode);

    public List<EventDetail> findByEventType(String designation);

    public List<EventDetail> findAll();
}
