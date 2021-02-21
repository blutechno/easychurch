/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.Date;
import java.util.List;
import orm.LoanRefunding;

/**
 *
 * @author jean pierre
 */
public interface LoanRefundingDAO {

    public boolean saveOrUpdateLoanRefunding(LoanRefunding loanRefunding);

    public List<LoanRefunding> findByLoan(String loanCode);

    public int findRefundedLoanAmount(String loanCode);

    public List<LoanRefunding> findByDates(Date dateFrom, Date dateTo);

    public List<LoanRefunding> findByDatesAndLoan(Date dateFrom, Date dateTo, String loanCode);

    public List<LoanRefunding> findAll();
}
