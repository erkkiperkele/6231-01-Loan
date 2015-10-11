package Data;

public class Account {

    private int accountNumber;
    private Customer owner;

    public Account(int accountNumber, Customer owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
    }

    public Customer getOwner() {
        return owner;
    }

    public int getAccountNumber() {
        return accountNumber;
    }


}
