/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.IncomeType;
import orm.dao.IncomeTypeDAO;

/**
 *
 * @author jean pierre
 */
public class IncomeTypeDAOImpl implements IncomeTypeDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateIncomeType(IncomeType incomeType) {
        return dao.saveObject(incomeType);
    }

    @Override
    public IncomeType findByName(String incomeTypeName) {
        return (IncomeType) dao.getSession().createCriteria(IncomeType.class).add(Restrictions.eq("incomeType", incomeTypeName)).uniqueResult();
    }

    @Override
    public List<IncomeType> findAll() {
        return dao.getSession().createCriteria(IncomeType.class).list();
    }

    @Override
    public IncomeType findById(int id) {
        return (IncomeType) dao.getSession().get(IncomeType.class, id);
    }
}
