/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.Date;
import java.util.List;
import orm.Income;

/**
 *
 * @author jean pierre
 */
public interface IncomeDAO {

    public boolean saveOrUpdateIncome(Income income);

    public List<Income> findByIncomeType(String typeName);

    public Income findByReceiptNumber(String receiptNumber);

    public List<Income> findByDates(Date dateFrom, Date dateTo);

    public List<Income> findByDatesAndType(Date dateFrom, Date dateTo, String type);

    public List<Income> findAll();
}
