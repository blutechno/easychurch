/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Loan;
import orm.LoanRefunding;
import orm.dao.LoanRefundingDAO;

/**
 *
 * @author jean pierre
 */
public class LoanRefundingDAOImpl implements LoanRefundingDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateLoanRefunding(LoanRefunding loanRefunding) {
        return dao.saveObject(loanRefunding);
    }

    @Override
    public List<LoanRefunding> findByLoan(String loanCode) {
        Loan loan = (Loan) dao.getSession().createCriteria(Loan.class).add(Restrictions.eq("loanCode", loanCode)).uniqueResult();
        return dao.getSession().createCriteria(LoanRefunding.class).add(Restrictions.eq("loan", loan)).list();
    }

    @Override
    public List<LoanRefunding> findByDates(Date dateFrom, Date dateTo) {
        return dao.getSession().createCriteria(LoanRefunding.class).add(Restrictions.between("refundDate", dateFrom, dateTo)).list();
    }

    @Override
    public List<LoanRefunding> findByDatesAndLoan(Date dateFrom, Date dateTo, String loanCode) {
        List<LoanRefunding> result = new ArrayList<LoanRefunding>();
        for (LoanRefunding r : findByDates(dateFrom, dateTo)) {
            if (r.getLoan().getLoanCode().equals(loanCode)) {
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public List<LoanRefunding> findAll() {
        return dao.getSession().createCriteria(LoanRefunding.class).list();
    }

    @Override
    public int findRefundedLoanAmount(String loanCode) {
        int sum = 0;
        if (!findByLoan(loanCode).isEmpty()) {
            for(LoanRefunding refund:findByLoan(loanCode)){
                sum+=refund.getPaidAmount();
            }
        }
        return sum;
    }
}
