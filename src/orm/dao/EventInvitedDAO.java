/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.EventInvited;

/**
 *
 * @author jean pierre
 */
public interface EventInvitedDAO {

    public boolean saveOrUpdateEventInvited(EventInvited eventInvited);

    public boolean saveOrUpdateEventInvited(List<EventInvited> list);

    public List<EventInvited> findByEventCode(String eventCode);

    public List<EventInvited> findAll();
}
