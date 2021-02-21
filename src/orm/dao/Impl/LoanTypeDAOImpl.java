/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.LoanType;
import orm.dao.LoanTypeDAO;

/**
 *
 * @author jean pierre
 */
public class LoanTypeDAOImpl implements LoanTypeDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateLoanType(LoanType loanType) {
        return dao.saveObject(loanType);
    }

    @Override
    public LoanType findByName(String loanTypeName) {
        return (LoanType) dao.getSession().createCriteria(LoanType.class).add(Restrictions.eq("loanTypeName", loanTypeName)).uniqueResult();
    }

    @Override
    public List<LoanType> findAll() {
        return dao.getSession().createCriteria(LoanType.class).list();
    }

    @Override
    public LoanType findById(int id) {
        return (LoanType) dao.getSession().get(LoanType.class, id);
    }
}
