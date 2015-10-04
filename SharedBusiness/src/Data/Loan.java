package Data;

import java.util.Date;

public class Loan {

    private int id;
    private int customerAccountNumber;
    private long amount;
    private Date dueDate;

    public Loan(int id, int customerAccountNumber, long amount, Date dueDate) {
        this.id = id;
        this.customerAccountNumber = customerAccountNumber;
        this.amount = amount;
        this.dueDate = dueDate;
    }
}
