/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.Date;
import java.util.List;
import orm.Offertory;

/**
 *
 * @author jean pierre
 */
public interface OffertoryDAO {

    public boolean saveOrUpdateOffertory(Offertory offertory);

    public List<Offertory> findByDate(Date recordDate);

    public Offertory findById(int id);

    public List<Offertory> findByType(String oType);

    public List<Offertory> findAll();
}
