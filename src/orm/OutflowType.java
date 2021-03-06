package orm;
// Generated Jun 18, 2012 3:59:06 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * OutflowType generated by hbm2java
 */
public class OutflowType  implements java.io.Serializable {


     private Integer id;
     private String outflowTypeName;
     private Set outflows = new HashSet(0);

    public OutflowType() {
    }

	
    public OutflowType(String outflowTypeName) {
        this.outflowTypeName = outflowTypeName;
    }
    public OutflowType(String outflowTypeName, Set outflows) {
       this.outflowTypeName = outflowTypeName;
       this.outflows = outflows;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getOutflowTypeName() {
        return this.outflowTypeName;
    }
    
    public void setOutflowTypeName(String outflowTypeName) {
        this.outflowTypeName = outflowTypeName;
    }
    public Set getOutflows() {
        return this.outflows;
    }
    
    public void setOutflows(Set outflows) {
        this.outflows = outflows;
    }




}


