/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.IncomeType;

/**
 *
 * @author jean pierre
 */
public interface IncomeTypeDAO {

    public boolean saveOrUpdateIncomeType(IncomeType incomeType);

    public IncomeType findById(int id);

    public IncomeType findByName(String incomeTypeName);

    public List<IncomeType> findAll();
}
