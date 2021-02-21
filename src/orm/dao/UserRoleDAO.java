/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.UserRole;
import orm.UserRoleId;

/**
 *
 * @author jean pierre
 */
public interface UserRoleDAO {

    public boolean saveOrUpdateUserRole(UserRole userRole);

    public UserRole findById(UserRoleId id);

    public List<UserRole> findByUser(String username);

    public List<UserRole> findByRole(String roleName);

    public List<UserRole> findExpiredByUser(String username);

    public List<UserRole> findAll();
}
