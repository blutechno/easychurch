/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Christian;
import orm.Loan;
import orm.dao.LoanDAO;

/**
 *
 * @author jean pierre
 */
public class LoanDAOImpl implements LoanDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateLoan(Loan loan) {
        return dao.saveObject(loan);
    }

    @Override
    public List<Loan> findByBeneficiary(String firstName, String lastName) {
        Christian christian = (Christian) dao.getSession().createCriteria(Christian.class).add(Restrictions.and(Restrictions.eq("firstName", firstName), Restrictions.eq("lastName", lastName))).uniqueResult();
        return dao.getSession().createCriteria(Loan.class).add(Restrictions.eq("christian", christian)).list();
    }

    @Override
    public Loan findByLoanCode(String loanCode) {
        return (Loan) dao.getSession().createCriteria(Loan.class).add(Restrictions.eq("loanCode", loanCode)).uniqueResult();
    }

    @Override
    public List<Loan> findByDates(Date dateFrom, Date dateTo) {
        return dao.getSession().createCriteria(Loan.class).add(Restrictions.between("loanDate", dateFrom, dateTo)).list();
    }

    @Override
    public List<Loan> findByDatesAndBeneficiary(Date dateFrom, Date dateTo, String beneficiary) {
        List<Loan> result = new ArrayList<Loan>();
        for (Loan l : findByDates(dateFrom, dateTo)) {
            String bene = l.getChristian().getFirstName() + " " + l.getChristian().getLastName();
            if (bene.equalsIgnoreCase(beneficiary)) {
                result.add(l);
            }
        }
        return result;
    }

    @Override
    public List<Loan> findAll() {
        return dao.getSession().createCriteria(Loan.class).list();
    }

    @Override
    public List<Loan> findByChristianCode(String christianCode) {
        Christian christian = (Christian) dao.getSession().createCriteria(Christian.class).add(Restrictions.eq("christianPin", christianCode)).uniqueResult();
        return dao.getSession().createCriteria(Loan.class).add(Restrictions.eq("christian", christian)).list();
    }

    @Override
    public Loan findById(int id) {
        return (Loan) dao.getSession().get(Loan.class, id);
    }
}
