/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Income;
import orm.IncomeType;
import orm.dao.IncomeDAO;

/**
 *
 * @author jean pierre
 */
public class IncomeDAOImpl implements IncomeDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateIncome(Income income) {
        return dao.saveObject(income);
    }

    @Override
    public List<Income> findByIncomeType(String typeName) {
        IncomeType incomeType = (IncomeType) dao.getSession().createCriteria(IncomeType.class).add(Restrictions.eq("incomeType", typeName)).uniqueResult();
        return dao.getSession().createCriteria(Income.class).add(Restrictions.eq("incomeType", incomeType)).list();
    }

    @Override
    public List<Income> findByDates(Date dateFrom, Date dateTo) {
        return dao.getSession().createCriteria(Income.class).add(Restrictions.between("incomeDate", dateFrom, dateTo)).list();
    }

    @Override
    public List<Income> findByDatesAndType(Date dateFrom, Date dateTo, String type) {
        List<Income> result = new ArrayList<Income>();
        for (Income i : findByDates(dateFrom, dateTo)) {
            if (i.getIncomeType().getIncomeType().equals(type)) {
                result.add(i);
            }
        }
        return result;
    }

    @Override
    public List<Income> findAll() {
        return dao.getSession().createCriteria(Income.class).list();
    }

    @Override
    public Income findByReceiptNumber(String receiptNumber) {
        return (Income) dao.getSession().createCriteria(Income.class).add(Restrictions.eq("receiptNumber", receiptNumber)).uniqueResult();
    }
}
