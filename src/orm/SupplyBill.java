package orm;
// Generated Jun 18, 2012 3:59:06 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SupplyBill generated by hbm2java
 */
public class SupplyBill  implements java.io.Serializable {


     private Integer id;
     private Supplier supplier;
     private String billNumber;
     private Date billDate;
     private int billAmount;
     private Set supplyBillDetails = new HashSet(0);

    public SupplyBill() {
    }

	
    public SupplyBill(Supplier supplier, String billNumber, Date billDate, int billAmount) {
        this.supplier = supplier;
        this.billNumber = billNumber;
        this.billDate = billDate;
        this.billAmount = billAmount;
    }
    public SupplyBill(Supplier supplier, String billNumber, Date billDate, int billAmount, Set supplyBillDetails) {
       this.supplier = supplier;
       this.billNumber = billNumber;
       this.billDate = billDate;
       this.billAmount = billAmount;
       this.supplyBillDetails = supplyBillDetails;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Supplier getSupplier() {
        return this.supplier;
    }
    
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    public String getBillNumber() {
        return this.billNumber;
    }
    
    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }
    public Date getBillDate() {
        return this.billDate;
    }
    
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }
    public int getBillAmount() {
        return this.billAmount;
    }
    
    public void setBillAmount(int billAmount) {
        this.billAmount = billAmount;
    }
    public Set getSupplyBillDetails() {
        return this.supplyBillDetails;
    }
    
    public void setSupplyBillDetails(Set supplyBillDetails) {
        this.supplyBillDetails = supplyBillDetails;
    }




}

