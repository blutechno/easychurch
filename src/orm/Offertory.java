package orm;
// Generated Jun 18, 2012 3:59:06 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Offertory generated by hbm2java
 */
public class Offertory  implements java.io.Serializable {


     private Integer offertoryId;
     private OffertoryType offertoryType;
     private User user;
     private String description;
     private Date recordDate;
     private int offertoryAmount;

    public Offertory() {
    }

    public Offertory(OffertoryType offertoryType, User user, String description, Date recordDate, int offertoryAmount) {
       this.offertoryType = offertoryType;
       this.user = user;
       this.description = description;
       this.recordDate = recordDate;
       this.offertoryAmount = offertoryAmount;
    }
   
    public Integer getOffertoryId() {
        return this.offertoryId;
    }
    
    public void setOffertoryId(Integer offertoryId) {
        this.offertoryId = offertoryId;
    }
    public OffertoryType getOffertoryType() {
        return this.offertoryType;
    }
    
    public void setOffertoryType(OffertoryType offertoryType) {
        this.offertoryType = offertoryType;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getRecordDate() {
        return this.recordDate;
    }
    
    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
    public int getOffertoryAmount() {
        return this.offertoryAmount;
    }
    
    public void setOffertoryAmount(int offertoryAmount) {
        this.offertoryAmount = offertoryAmount;
    }




}

