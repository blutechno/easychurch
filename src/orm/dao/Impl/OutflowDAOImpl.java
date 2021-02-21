/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Outflow;
import orm.OutflowType;
import orm.dao.OutflowDAO;

/**
 *
 * @author jean pierre
 */
public class OutflowDAOImpl implements OutflowDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateOutflow(Outflow outflow) {
        return dao.saveObject(outflow);
    }

    @Override
    public List<Outflow> findByType(String type) {
        OutflowType outflowType = (OutflowType) dao.getSession().createCriteria(OutflowType.class).add(Restrictions.eq("outflowTypeName", type)).uniqueResult();
        return dao.getSession().createCriteria(Outflow.class).add(Restrictions.eq("outflowType", outflowType)).list();
    }

    @Override
    public List<Outflow> findByDates(Date dateFrom, Date dateTo) {
        return dao.getSession().createCriteria(Outflow.class).add(Restrictions.between("outflowDate", dateFrom, dateTo)).list();
    }

    @Override
    public List<Outflow> findByDatesAndType(Date dateFrom, Date dateTo, String type) {
        List<Outflow> result = new ArrayList<Outflow>();
        for (Outflow o : findByDates(dateFrom, dateTo)) {
            if (o.getOutflowType().getOutflowTypeName().equals(type)) {
                result.add(o);
            }
        }
        return result;
    }

    @Override
    public List<Outflow> findAll() {
        return dao.getSession().createCriteria(Outflow.class).list();
    }

    @Override
    public Outflow findByCheckNumber(String checkNumber) {
        return (Outflow) dao.getSession().createCriteria(Outflow.class).add(Restrictions.eq("checkNumber", checkNumber)).uniqueResult();
    }
}
