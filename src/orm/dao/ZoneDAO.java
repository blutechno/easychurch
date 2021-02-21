/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.Zone;

/**
 *
 * @author jean pierre
 */
public interface ZoneDAO {

    public boolean saveOrUpdateZone(Zone zone);

    public Zone findByName(String zoneName);

    public Zone findById(int id);

    public boolean delete(String zoneName);

    public List<Zone> findAll();
}
