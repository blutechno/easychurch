/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.UserLog;

/**
 *
 * @author jean pierre
 */
public interface UserLogDAO {

    public void saveOrUpdateLog(UserLog userLog);

    public UserLog findUnClosedByUser(String username);

    public List<UserLog> findByUser(String username);

    public UserLog findLastLog(String username);

    public List<UserLog> findAll();
}
