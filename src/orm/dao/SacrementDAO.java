/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.Sacrement;

/**
 *
 * @author jean pierre
 */
public interface SacrementDAO {

    public boolean saveOrUpdateSacrement(Sacrement sacrement);

    public Sacrement findById(int id);

    public Sacrement findByName(String sacrementName);

    public boolean delete(String sacrementName);

    public List<Sacrement> findAll();
}
