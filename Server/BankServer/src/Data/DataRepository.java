package Data;

import Services.SessionService;

import java.util.*;
import java.util.stream.Collectors;

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
    }

    public Customer getCustomer(String userName)
    {
        Account customerAccount = getAccount(userName);

        return customerAccount == null
                ? null
                : customerAccount.getOwner();
    }

    public Account getAccount(String userName) {
        String index = getIndex(userName);

        return getAccountsAtIndex(index)
                .stream()
                .filter(a -> a.getOwner().getUserName().equalsIgnoreCase(userName))
                .findFirst()
                .orElse(null);
    }

    public void createAccount(Customer owner) {

        if (owner.getId() == 0) {
            owner.setId(++customerNumber);
        }
        owner.setBank(SessionService.getInstance().getBank());

        String index = getIndex(owner.getUserName());
        Account newAccount = new Account(getNewAccountNumber(), owner);
        owner.setAccountNumber(newAccount.getAccountNumber());

        getAccountsAtIndex(index).add(newAccount);
    }

    public List<Loan> getLoans(int accountNumber) {

        Customer customer = getCustomer(accountNumber);

        String index = getIndex(customer.getUserName().toLowerCase());

        return getLoansAtIndex(index)
                .stream()
                .filter(l -> l.getCustomerAccountNumber() == customer.getAccountNumber())
                .collect(Collectors.toList());
    }

    public void createLoan(String userName, long amount, Date dueDate) {
        String index = getIndex(userName);

        int customerAccountNumber = getAccount(userName).getAccountNumber();
        Loan newLoan = new Loan(getNewLoanNumber(), customerAccountNumber, amount, dueDate);

        getLoansAtIndex(index).add(newLoan);
    }


    public void updateLoan(Customer customer, int loanNumber, Date newDueDate) {
        String index = getIndex(customer.getUserName());

        //TODO: check if stream modifies entity inside the collection....
        getLoansAtIndex(index)
                .stream()
                .filter(l -> l.getLoanNumber() == loanNumber)
                .findFirst()
                .orElse(null)      //TODO: dangerous, if loan doesn't exist, you end up with a null ref.
                .setDueDate(newDueDate);
    }

    private Customer getCustomer(int accountNumber)
    {
        return this.accounts.values()
            .stream()
            .flatMap(accounts -> accounts
                    .stream()
                    .filter(x -> x.getAccountNumber() == accountNumber)
                    .map(a -> a.getOwner()))
            .findFirst()
            .orElse(null);
    }

    private String getIndex(String userName){
        return userName.toLowerCase().substring(0, 1);
    }

    private List<Loan> getLoansAtIndex(String index)
    {
        if (this.loans.get(index) == null)
        {
            this.loans.put(index, new ArrayList<>());
        }

        return this.loans.get(index);
    }

    private List<Account> getAccountsAtIndex(String index)
    {
        if (this.accounts.get(index) == null)
        {
            this.accounts.put(index, new ArrayList<>());
        }

        return this.accounts.get(index);
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

        Customer alex = new Customer(0, 0, "Alex", "Emond", "at", bank, "alex.emond@gmail.com", "514.111.2222");
        Customer justin = new Customer(0, 0, "Justin", "Paquette", "jp", bank, "justin.paquette@gmail.com", "514.111.2222");
        Customer maria = new Customer(0, 0, "Maria", "Etinger", "me", bank, "maria.etinger@gmail.com", "514.111.2222");

        //Those 3 have an account on each bank
        createAccount(alex);
        createAccount(justin);
        createAccount(maria);

        if (bank == Bank.Royal) {
            Customer sylvain = new Customer(0, 0, "Sylvain", "Poudrier", "sp", bank, "sylvain.poudrier@gmail.com", "514.111.2222");
            createAccount(sylvain);

            createLoan(alex.getUserName(), 200, dueDate);
        }

        if (bank == Bank.National) {
            Customer pascal = new Customer(0, 0, "Pascal", "Groulx", "pg", bank, "pascal.groulx@gmail.com", "514.111.2222");
            createAccount(pascal);

            createLoan(alex.getUserName(), 300, dueDate);
        }

        if (bank == Bank.Dominion) {
            Customer max =new Customer(0, 0, "Max", "Tanquerel", "mt", bank, "max.tanquerel@gmail.com", "514.111.2222");
            createAccount(max);

            createLoan(justin.getUserName(), 1000, dueDate);
            createLoan(maria.getUserName(), 500, dueDate);
        }
    }
}
