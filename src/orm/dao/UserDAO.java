/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.User;

/**
 *
 * @author jean pierre
 */
public interface UserDAO {

    public boolean saveOrUpdateUser(User user);

    public User findByUsername(String username);

    public boolean isAuthenticated(String username, String password);

    public List<User> findAll();
}
