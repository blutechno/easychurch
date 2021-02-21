/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.ChristianContact;

/**
 *
 * @author jean pierre
 */
public interface ChristianContactDAO {

    public boolean saveOrUpdateChristianContact(ChristianContact contact);

    public ChristianContact findByChristian(String firstName, String lastName);

    public ChristianContact findById(int id);

    public List<ChristianContact> findAll();
}
