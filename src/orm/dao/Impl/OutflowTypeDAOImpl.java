/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.OutflowType;
import orm.dao.OutflowTypeDAO;

/**
 *
 * @author jean pierre
 */
public class OutflowTypeDAOImpl implements OutflowTypeDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateOutflowType(OutflowType outflowType) {
        return dao.saveObject(outflowType);
    }

    @Override
    public OutflowType findByName(String name) {
        return (OutflowType) dao.getSession().createCriteria(OutflowType.class).add(Restrictions.eq("outflowTypeName", name)).uniqueResult();
    }

    @Override
    public List<OutflowType> findAll() {
        return dao.getSession().createCriteria(OutflowType.class).list();
    }

    @Override
    public OutflowType findById(int id) {
        return (OutflowType) dao.getSession().get(OutflowType.class, id);
    }
}
