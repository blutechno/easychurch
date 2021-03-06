package orm;
// Generated Jun 18, 2012 3:59:06 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * IncomeType generated by hbm2java
 */
public class IncomeType  implements java.io.Serializable {


     private Integer id;
     private String incomeType;
     private Set incomes = new HashSet(0);

    public IncomeType() {
    }

	
    public IncomeType(String incomeType) {
        this.incomeType = incomeType;
    }
    public IncomeType(String incomeType, Set incomes) {
       this.incomeType = incomeType;
       this.incomes = incomes;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getIncomeType() {
        return this.incomeType;
    }
    
    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }
    public Set getIncomes() {
        return this.incomes;
    }
    
    public void setIncomes(Set incomes) {
        this.incomes = incomes;
    }




}


