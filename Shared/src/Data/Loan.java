package Data;

import java.io.Serializable;
import java.util.Date;

public class Loan implements Serializable {

    private int loanNumber;
    private int customerAccountNumber;
    private long amount;
    private Date dueDate;

    public Loan(int loanNumber, int customerAccountNumber, long amount, Date dueDate) {
        this.loanNumber = loanNumber;
        this.customerAccountNumber = customerAccountNumber;
        this.amount = amount;
        this.dueDate = dueDate;
    }

    public int getLoanNumber() {
        return loanNumber;
    }

    public int getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    public long getAmount() {
        return amount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
