/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.LoanType;

/**
 *
 * @author jean pierre
 */
public interface LoanTypeDAO {

    public boolean saveOrUpdateLoanType(LoanType loanType);

    public LoanType findById(int id);

    public LoanType findByName(String loanTypeName);

    public List<LoanType> findAll();
}
