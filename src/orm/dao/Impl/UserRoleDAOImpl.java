/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Role;
import orm.User;
import orm.UserRole;
import orm.UserRoleId;
import orm.dao.UserRoleDAO;

/**
 *
 * @author jean pierre
 */
public class UserRoleDAOImpl implements UserRoleDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateUserRole(UserRole userRole) {
        return dao.saveObject(userRole);
    }
    

    @Override
    public List<UserRole> findByUser(String username) {
        User user = (User) dao.getSession().createCriteria(User.class).add(Restrictions.eq("username", username)).uniqueResult();
        return dao.getSession().createCriteria(UserRole.class).add(Restrictions.eq("user", user)).list();
    }

    @Override
    public List<UserRole> findByRole(String roleName) {
        Role role = (Role) dao.getSession().createCriteria(Role.class).add(Restrictions.eq("roleName", roleName)).uniqueResult();
        return dao.getSession().createCriteria(UserRole.class).add(Restrictions.eq("role", role)).list();
    }

    @Override
    public List<UserRole> findExpiredByUser(String username) {
        List<UserRole> result = new ArrayList<UserRole>();
        for (UserRole r : findByUser(username)) {
            if (r.getExpiredOn() != null) {
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public List<UserRole> findAll() {
        return dao.getSession().createCriteria(UserRole.class).list();
    }

    @Override
    public UserRole findById(UserRoleId id) {
        return (UserRole) dao.getSession().get(UserRole.class, id);
    }
}
