/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.Date;
import java.util.List;
import orm.Loan;

/**
 *
 * @author jean pierre
 */
public interface LoanDAO {

    public boolean saveOrUpdateLoan(Loan loan);

    public List<Loan> findByBeneficiary(String firstName, String lastName);

    public List<Loan> findByChristianCode(String christianCode);

    public Loan findByLoanCode(String loanCode);

    public Loan findById(int id);

    public List<Loan> findByDates(Date dateFrom, Date dateTo);

    public List<Loan> findByDatesAndBeneficiary(Date dateFrom, Date dateTo, String beneficiary);

    public List<Loan> findAll();
}
