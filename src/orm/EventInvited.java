package orm;
// Generated Jun 18, 2012 3:59:06 PM by Hibernate Tools 3.2.1.GA



/**
 * EventInvited generated by hbm2java
 */
public class EventInvited  implements java.io.Serializable {


     private Integer invitedId;
     private EventDetail eventDetail;
     private Christian christian;
     private char attended;

    public EventInvited() {
    }

    public EventInvited(EventDetail eventDetail, Christian christian, char attended) {
       this.eventDetail = eventDetail;
       this.christian = christian;
       this.attended = attended;
    }
   
    public Integer getInvitedId() {
        return this.invitedId;
    }
    
    public void setInvitedId(Integer invitedId) {
        this.invitedId = invitedId;
    }
    public EventDetail getEventDetail() {
        return this.eventDetail;
    }
    
    public void setEventDetail(EventDetail eventDetail) {
        this.eventDetail = eventDetail;
    }
    public Christian getChristian() {
        return this.christian;
    }
    
    public void setChristian(Christian christian) {
        this.christian = christian;
    }
    public char getAttended() {
        return this.attended;
    }
    
    public void setAttended(char attended) {
        this.attended = attended;
    }




}

