package Data;

import Services.SessionService;

import java.util.*;

/**
 * The accounts and loans are placed in several lists
 * that are stored in a hash map according to the
 * first character of the username indicated in the account.
 * <p>
 * For example, all the accounts with the username starting with an “A”
 * will belong to the same list and will be stored in a hash table
 * (acting as the database) using the key “A”
 */

public class DataRepository {


    private Hashtable<String, List<Account>> accounts;
    private Hashtable<String, List<Loan>> loans;

    private int accountNumber = 1;
    private int loanNumber = 1;
    private int customerNumber = 1;


    public DataRepository() {

        this.accounts = new Hashtable<>();
        this.loans = new Hashtable<>();

        generateInitialData();
        //TODO: populate!
    }

    public Account getAccount(Customer owner) {
        String index = getIndex(owner);

        //TODO: verify it works
        return this.accounts.get(index)
                .stream()
                .filter(a -> a.getOwner().getUserName() == owner.getUserName())
                .findFirst()
                .orElse(null);
    }

    public void createAccount(Customer owner) {
        if (owner.getId() == 0) {
            owner.setId(++customerNumber);
        }

        String index = getIndex(owner);
        Account newAccount = new Account(getNewAccountNumber(), owner);

        //TODO: Check if value is being set in hashmap or if I need to put() the whole list again!!!
        this.accounts.get(index).add(newAccount);
    }

    public List<Loan> getLoans(Customer customer) {
        String index = getIndex(customer);
        List<Loan> loans = this.loans.get(index);

        List<Loan> customerLoans = new ArrayList<>();

        for (Loan loan : loans) {
            if (loan.getCustomerAccountNumber() == customer.getAccountNumber()) {
                customerLoans.add(loan);
            }
        }
        return customerLoans;
    }

    public void createLoan(Customer owner, long amount, Date dueDate) {
        String index = getIndex(owner);
        Loan newLoan = new Loan(getNewLoanNumber(), owner.getAccountNumber(), amount, dueDate);

        //TODO: Check if value is being set in hashmap or if I need to put() the whole list again!!!
        this.loans.get(index).add(newLoan);
    }


    public void updateLoan(Customer customer, int loanNumber, Date newDueDate) {
        String index = getIndex(customer);

        //TODO: check if stream modifies entity inside the collection....
        this.loans.get(index)
                .stream()
                .filter(l -> l.getLoanNumber() == loanNumber)
                .findFirst()
                .orElse(null)      //TODO: dangerous, if loan doesn't exist, you end up with a null ref.
                .setDueDate(newDueDate);
    }

    private String getIndex(Customer customer) {
        return customer.getUserName().substring(0, 0);
    }

    private int getNewAccountNumber() {
        return ++accountNumber;
    }

    private int getNewLoanNumber() {
        return ++loanNumber;
    }


    /**
     * Some dummy data initialization
     */
    private void generateInitialData() {

        Bank bank = SessionService.getInstance().getBank();
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.MONTH, 1);

        Date dueDate = calendar.getTime();

        Customer alex = new Customer(0, 0, "Alex", "Emond", "at", bank);
        Customer justin = new Customer(0, 0, "Justin", "Paquette", "jp", bank);
        Customer maria = new Customer(0, 0, "Maria", "Etinger", "me", bank);

        //Those 3 have an account on each bank
        createAccount(alex);
        createAccount(justin);
        createAccount(maria);

        if (bank == Bank.Royal) {
            Customer sylvain = new Customer(0, 0, "Sylvain", "Poudrier", "sp", bank);
            createAccount(sylvain);

            createLoan(alex, 200, dueDate);
        }

        if (bank == Bank.National) {
            Customer pascal = new Customer(0, 0, "Pascal", "Groulx", "pg", bank);
            createAccount(pascal);

            createLoan(alex, 300, dueDate);
        }

        if (bank == Bank.Dominion) {
            Customer max =new Customer(0, 0, "Max", "Tanquerel", "mt", bank);
            createAccount(max);

            createLoan(justin, 1000, dueDate);
            createLoan(maria, 500, dueDate);
        }
    }
}
