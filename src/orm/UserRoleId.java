package orm;
// Generated Jun 18, 2012 3:59:06 PM by Hibernate Tools 3.2.1.GA



/**
 * UserRoleId generated by hbm2java
 */
public class UserRoleId  implements java.io.Serializable {


     private int user;
     private int role;

    public UserRoleId() {
    }

    public UserRoleId(int user, int role) {
       this.user = user;
       this.role = role;
    }
   
    public int getUser() {
        return this.user;
    }
    
    public void setUser(int user) {
        this.user = user;
    }
    public int getRole() {
        return this.role;
    }
    
    public void setRole(int role) {
        this.role = role;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof UserRoleId) ) return false;
		 UserRoleId castOther = ( UserRoleId ) other; 
         
		 return (this.getUser()==castOther.getUser())
 && (this.getRole()==castOther.getRole());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getUser();
         result = 37 * result + this.getRole();
         return result;
   }   


}


