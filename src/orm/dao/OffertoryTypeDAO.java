/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.OffertoryType;

/**
 *
 * @author jean pierre
 */
public interface OffertoryTypeDAO {

    public boolean saveOrUpdateOffertoryType(OffertoryType offertoryType);

    public OffertoryType findByName(String offertoryName);

    public List<OffertoryType> findByStatus(char status);

    public List<OffertoryType> findAll();
}
