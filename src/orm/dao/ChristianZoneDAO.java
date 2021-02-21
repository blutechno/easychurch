/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.ChristianZone;

/**
 *
 * @author jean pierre
 */
public interface ChristianZoneDAO {

    public boolean saveOrUpdateChristianZone(ChristianZone christianZone);

    public boolean saveOrUpdateChristianZone(List<ChristianZone> christianZone);

    public List<ChristianZone> findByZone(String zoneName);

    public ChristianZone findById(int id);

    public List<ChristianZone> findByChristian(String firstName, String lastName);

    public ChristianZone findActive(String christianPin);

    public List<ChristianZone> findByChristian(String christianPin);

    public List<ChristianZone> findAll();
}
