/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.OutflowType;

/**
 *
 * @author jean pierre
 */
public interface OutflowTypeDAO {

    public boolean saveOrUpdateOutflowType(OutflowType outflowType);

    public OutflowType findById(int id);

    public OutflowType findByName(String name);

    public List<OutflowType> findAll();
}
