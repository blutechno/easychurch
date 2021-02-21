/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.MD5;
import orm.User;
import orm.dao.UserDAO;

/**
 *
 * @author jean pierre
 */
public class UserDAOImpl implements UserDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateUser(User user) {
        return dao.saveObject(user);
    }

    @Override
    public User findByUsername(String username) {
        return (User) dao.getSession().createCriteria(User.class).add(Restrictions.eq("username", username)).uniqueResult();
    }

    @Override
    public boolean isAuthenticated(String username, String password) {
        User user = this.findByUsername(username);
        if (user == null) {
            return false;
        } else {
            if (user.getPassword().equals(MD5.md5(password))) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public List<User> findAll() {
        return dao.getSession().createCriteria(User.class).list();
    }
}
