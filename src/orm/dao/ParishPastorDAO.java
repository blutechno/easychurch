/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.ParishPastor;

/**
 *
 * @author jean pierre
 */
public interface ParishPastorDAO {

    public boolean saveOrUpdateParishPastor(ParishPastor parishPastor);

    public List<ParishPastor> findByParish(String parishName);

    public List<ParishPastor> findAll();
}
