/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.Date;
import java.util.List;
import orm.EventIncome;

/**
 *
 * @author jean pierre
 */
public interface EventIncomeDAO {

    public boolean saveOrUpdateEventIncome(EventIncome eventIncome);

    public EventIncome findByEventCode(String eventCode);

    public List<EventIncome> findDates(Date dateFrom, Date dateTo);

    public List<EventIncome> findAll();
}
