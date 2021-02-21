package orm;
// Generated Jun 18, 2012 3:59:06 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * LoanRefunding generated by hbm2java
 */
public class LoanRefunding  implements java.io.Serializable {


     private Integer id;
     private User user;
     private Loan loan;
     private int paidAmount;
     private int remainAmount;
     private Date refundDate;
     private String deposer;

    public LoanRefunding() {
    }

    public LoanRefunding(User user, Loan loan, int paidAmount, int remainAmount, Date refundDate, String deposer) {
       this.user = user;
       this.loan = loan;
       this.paidAmount = paidAmount;
       this.remainAmount = remainAmount;
       this.refundDate = refundDate;
       this.deposer = deposer;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public Loan getLoan() {
        return this.loan;
    }
    
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
    public int getPaidAmount() {
        return this.paidAmount;
    }
    
    public void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
    }
    public int getRemainAmount() {
        return this.remainAmount;
    }
    
    public void setRemainAmount(int remainAmount) {
        this.remainAmount = remainAmount;
    }
    public Date getRefundDate() {
        return this.refundDate;
    }
    
    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }
    public String getDeposer() {
        return this.deposer;
    }
    
    public void setDeposer(String deposer) {
        this.deposer = deposer;
    }




}


