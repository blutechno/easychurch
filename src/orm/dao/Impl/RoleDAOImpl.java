/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Role;
import orm.dao.RoleDAO;

/**
 *
 * @author jean pierre
 */
public class RoleDAOImpl implements RoleDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateRoleDAO(Role role) {
        return dao.saveObject(role);
    }

    @Override
    public Role findByRoleName(String roleName) {
        return (Role) dao.getSession().createCriteria(Role.class).add(Restrictions.eq("roleName", roleName)).uniqueResult();
    }

    @Override
    public Role findRoleAcronym(String roleAcronym) {
        return (Role) dao.getSession().createCriteria(Role.class).add(Restrictions.eq("roleCode", roleAcronym)).uniqueResult();
    }

    @Override
    public List<Role> findAll() {
        return dao.getSession().createCriteria(Role.class).list();
    }
}
