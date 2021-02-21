/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.Parish;

/**
 *
 * @author jean pierre
 */
public interface ParishDAO {

    public boolean saveOrUpdateParish(Parish parish);

    public Parish findById(int id);

    public Parish findByName(String parishName);

    public List<Parish> findAll();
}
