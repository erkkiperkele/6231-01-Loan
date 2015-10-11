package Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * The accounts and loans are placed in several lists
 * that are stored in a hash map according to the
 * first character of the username indicated in the account.
 *
 * For example, all the accounts with the username starting with an “A”
 * will belong to the same list and will be stored in a hash table
 * (acting as the database) using the key “A”
 */

public class DataRepository {


    private HashMap<String, List<Account>> accounts;
    private HashMap<String, List<Loan>> loans;

    //Cheap trick to leave the first 100 values for data population intents
    private int accountNumber = 100;
    private int loanNumber = 100;


    public DataRepository() {

        this.accounts = new HashMap<>();
        this.loans = new HashMap<>();

        //TODO: populate!
    }

    public Account getAccount(Customer owner)
    {
        String index = getIndex(owner);

        //TODO: verify it works
        return this.accounts.get(index)
                .stream()
                .filter(a -> a.getOwner().getUserName() == owner.getUserName())
                .findFirst()
                .orElse(null);

//        List<Account> accounts = this.accounts.get(index);
//        for (Account account : accounts)
//        {
//            if(account.getOwner().getUserName() == owner.getUserName())
//            {
//                return account;
//            }
//        }
//        return null;
    }

    public void createAccount(Customer owner)
    {
        String index = getIndex(owner);
        Account newAccount = new Account(getNewAccountNumber(), owner);

        //TODO: Check if value is being set in hashmap or if I need to put() the whole list again!!!
        this.accounts.get(index).add(newAccount);
    }

    public List<Loan> getLoans(Customer customer)
    {
        String index = getIndex(customer);
        List<Loan> loans = this.loans.get(index);

        List<Loan> customerLoans = new ArrayList<Loan>();

        for (Loan loan : loans)
        {
            if(loan.getCustomerAccountNumber() == customer.getAccountNumber())
            {
                customerLoans.add(loan);
            }
        }
        return customerLoans;
    }

    public void createLoan(Customer owner, long amount, Date dueDate)
    {
        String index = getIndex(owner);
        Loan newLoan= new Loan(getNewLoanNumber(), owner.getAccountNumber(), amount, dueDate);

        //TODO: Check if value is being set in hashmap or if I need to put() the whole list again!!!
        this.loans.get(index).add(newLoan);
    }


    public void updateLoan(Customer customer, int loanNumber, Date newDueDate)
    {
        String index = getIndex(customer);

        //TODO: check if stream modifies entity inside the collection....
        this.loans.get(index)
                .stream()
                .filter(l -> l.getLoanNumber() == loanNumber)
                .findFirst()
                .orElse(null)      //TODO: dangerous, if loan doesn't exist, you end up with a null ref.
                .setDueDate(newDueDate);

//        List<Loan> loans = this.loans.get(index);
//
//        loans
//                .stream()
//                .filter(l -> l.getLoanNumber() == loanNumber)
//                .findFirst()
//                .orElse(null)
//                .setDueDate(newDueDate);
//
//        loan.setDueDate(newDueDate);
//
//        this.loans.get(index).add

//        loans.stream().filter(
//                loan -> loan.getCustomerAccountNumber() == customer.getAccountNumber())
//                .forEach(loan -> {
//            customerLoans.add(loan);
//        });
    }

    private String getIndex(Customer customer)
    {
        return customer.getUserName().substring(0,0);
    }

    private int getNewAccountNumber() {
        return ++ accountNumber;
    }

    private int getNewLoanNumber() {
        return ++ loanNumber;
    }
}
