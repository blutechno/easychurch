/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.Date;
import java.util.List;
import orm.ChristianSacrement;
import orm.ChristianSacrementId;

/**
 *
 * @author jean pierre
 */
public interface ChristianSacrementDAO {

    public boolean saveOrUpdateChristianSacrement(ChristianSacrement christianSacrement);

    public boolean saveOrUpdateChristianSacrement(List<ChristianSacrement> list);

    public ChristianSacrement findBId(ChristianSacrementId id);

    public List<ChristianSacrement> findByChristian(String firstName, String lastName);

    public List<ChristianSacrement> findBySacrement(String sacrementName);

    public ChristianSacrement findBySacrementAndChristian(String sacrementName, String christianPin);

    public List<ChristianSacrement> findByDate(Date date);

    public List<ChristianSacrement> findByDateAndSacrement(Date date, String sacrementName);

    public List<ChristianSacrement> findAll();
}
