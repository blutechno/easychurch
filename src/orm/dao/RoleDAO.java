/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.Role;

/**
 *
 * @author jean pierre
 */
public interface RoleDAO {

    public boolean saveOrUpdateRoleDAO(Role role);

    public Role findByRoleName(String roleName);

    public Role findRoleAcronym(String roleAcronym);

    public List<Role> findAll();
}
