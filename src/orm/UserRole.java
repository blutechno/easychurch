package orm;
// Generated Jun 18, 2012 3:59:06 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * UserRole generated by hbm2java
 */
public class UserRole  implements java.io.Serializable {


     private UserRoleId id;
     private User user;
     private Role role;
     private Date assignedOn;
     private Date expiredOn;

    public UserRole() {
    }

	
    public UserRole(UserRoleId id, User user, Role role, Date assignedOn) {
        this.id = id;
        this.user = user;
        this.role = role;
        this.assignedOn = assignedOn;
    }
    public UserRole(UserRoleId id, User user, Role role, Date assignedOn, Date expiredOn) {
       this.id = id;
       this.user = user;
       this.role = role;
       this.assignedOn = assignedOn;
       this.expiredOn = expiredOn;
    }
   
    public UserRoleId getId() {
        return this.id;
    }
    
    public void setId(UserRoleId id) {
        this.id = id;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public Role getRole() {
        return this.role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    public Date getAssignedOn() {
        return this.assignedOn;
    }
    
    public void setAssignedOn(Date assignedOn) {
        this.assignedOn = assignedOn;
    }
    public Date getExpiredOn() {
        return this.expiredOn;
    }
    
    public void setExpiredOn(Date expiredOn) {
        this.expiredOn = expiredOn;
    }




}

