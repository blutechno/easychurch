package orm;
// Generated Jun 18, 2012 3:59:06 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Role generated by hbm2java
 */
public class Role  implements java.io.Serializable {


     private Integer roleId;
     private String roleName;
     private String roleCode;
     private Set userRoles = new HashSet(0);

    public Role() {
    }

	
    public Role(String roleName, String roleCode) {
        this.roleName = roleName;
        this.roleCode = roleCode;
    }
    public Role(String roleName, String roleCode, Set userRoles) {
       this.roleName = roleName;
       this.roleCode = roleCode;
       this.userRoles = userRoles;
    }
   
    public Integer getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    public String getRoleName() {
        return this.roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getRoleCode() {
        return this.roleCode;
    }
    
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    public Set getUserRoles() {
        return this.userRoles;
    }
    
    public void setUserRoles(Set userRoles) {
        this.userRoles = userRoles;
    }




}


