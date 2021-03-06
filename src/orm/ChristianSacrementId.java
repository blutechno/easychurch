package orm;
// Generated Jun 18, 2012 3:59:06 PM by Hibernate Tools 3.2.1.GA



/**
 * ChristianSacrementId generated by hbm2java
 */
public class ChristianSacrementId  implements java.io.Serializable {


     private int christian;
     private int sacrement;

    public ChristianSacrementId() {
    }

    public ChristianSacrementId(int christian, int sacrement) {
       this.christian = christian;
       this.sacrement = sacrement;
    }
   
    public int getChristian() {
        return this.christian;
    }
    
    public void setChristian(int christian) {
        this.christian = christian;
    }
    public int getSacrement() {
        return this.sacrement;
    }
    
    public void setSacrement(int sacrement) {
        this.sacrement = sacrement;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ChristianSacrementId) ) return false;
		 ChristianSacrementId castOther = ( ChristianSacrementId ) other; 
         
		 return (this.getChristian()==castOther.getChristian())
 && (this.getSacrement()==castOther.getSacrement());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getChristian();
         result = 37 * result + this.getSacrement();
         return result;
   }   


}


