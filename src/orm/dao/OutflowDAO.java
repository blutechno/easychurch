/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.Date;
import java.util.List;
import orm.Outflow;

/**
 *
 * @author jean pierre
 */
public interface OutflowDAO {

    public boolean saveOrUpdateOutflow(Outflow outflow);

    public List<Outflow> findByType(String type);

    public Outflow findByCheckNumber(String checkNumber);

    public List<Outflow> findByDates(Date dateFrom, Date dateTo);

    public List<Outflow> findByDatesAndType(Date dateFrom, Date dateTo, String type);

    public List<Outflow> findAll();
}
