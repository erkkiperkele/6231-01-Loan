package Data;

import Services.LockFactory;
import Services.SessionService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A thread safe repository serving as an in memory database
 * For accounts and loans.
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

    public Customer getCustomer(String userName) {
        Account customerAccount = getAccount(userName);

        return customerAccount == null
                ? null
                : customerAccount.getOwner();
    }
    public Account getAccount(int accountNumber) {
        return this.accounts.values()
                .stream()
                .flatMap(a -> a.stream())
                .filter(a -> a.getAccountNumber() == accountNumber)
                .findFirst()
                .orElse(null);
    }

    public Account getAccount(String userName) {
        String index = getIndex(userName);

//        LockFactory.getInstance().readlock(userName);

        Account foundAccount = getAccountsAtIndex(index)
                .stream()
                .filter(a -> a.getOwner().getUserName().equalsIgnoreCase(userName))
                .findFirst()
                .orElse(null);

//        LockFactory.getInstance().readUnlock(userName);

        return foundAccount;
    }

    public Account getAccount(String firstName, String lastName)
    {
        return accounts.values()
                .stream()
                .flatMap(x -> x.stream())
                .filter(a -> a.getOwner().getFirstName().equalsIgnoreCase(firstName))
                .filter(a -> a.getOwner().getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    public void createAccount(Customer owner, long creditLimit) {

        if (getAccount(owner.getUserName()) != null) {
            SessionService.getInstance().log().info(
                    String.format("This Account already exists for %s", owner.getFirstName())
            );
            return;
        }

        setOwnerInfo(owner);    //PATCH: bad pattern.

        Account newAccount = new Account(generateNewAccountNumber(), owner, creditLimit);
        owner.setAccountNumber(newAccount.getAccountNumber());

        createAccountThreadSafe(owner, newAccount);
    }

    public List<Loan> getLoans(int accountNumber) {

        Customer customer = getCustomer(accountNumber);
        String userName = customer.getUserName();

        String index = getIndex(customer.getUserName().toLowerCase());

//        LockFactory.getInstance().readlock(userName);

        List<Loan> loans = getLoansAtIndex(index)
                .stream()
                .filter(l -> l.getCustomerAccountNumber() == customer.getAccountNumber())
                .collect(Collectors.toList());

//        LockFactory.getInstance().readUnlock(userName);

        return loans;
    }

    public Loan createLoan(String userName, long amount, Date dueDate) {

        String index = getIndex(userName);
        int customerAccountNumber = getAccount(userName).getAccountNumber();

        Loan newLoan = new Loan(generateNewLoanNumber(), customerAccountNumber, amount, dueDate);
        getLoansAtIndex(index).add(newLoan);
        return newLoan;
    }

    public void updateLoan(Customer customer, int loanNumber, Date newDueDate) {
        updateLoanThreadSafe(customer, loanNumber, newDueDate);
    }

    private Customer getCustomer(int accountNumber) {
        return this.accounts.values()
                .stream()
                .flatMap(accounts -> accounts
                        .stream()
                        .filter(x -> x.getAccountNumber() == accountNumber)
                        .map(a -> a.getOwner()))
                .findFirst()
                .orElse(null);
    }

    private void updateLoanThreadSafe(Customer customer, int loanNumber, Date newDueDate) {
        String index = getIndex(customer.getUserName());
        String userName = customer.getUserName();

        LockFactory.getInstance().writeLock(userName);

        //TODO: check if stream modifies entity inside the collection....
        getLoansAtIndex(index)
                .stream()
                .filter(l -> l.getLoanNumber() == loanNumber)
                .findFirst()
                .orElse(null)      //TODO: dangerous, if loan doesn't exist, you end up with a null ref.
                .setDueDate(newDueDate);

        LockFactory.getInstance().writeUnlock(userName);
    }

    private void createAccountThreadSafe(Customer owner, Account newAccount) {
        String index = getIndex(owner.getUserName());
        try {
            List<Account> accounts = getAccountsAtIndex(index);
            LockFactory.getInstance().writeLock(owner.getUserName());

            //UNCOMMENT FOR CONCURRENT CREATION TESTING
            testConcurrentAccess(owner, "Concurrent1");

            accounts.add(newAccount);

            SessionService.getInstance().log().info(
                    String.format("Thread #%d CREATED account for %s", Thread.currentThread().getId(), owner.getFirstName())
            );
        } finally {
            LockFactory.getInstance().writeUnlock(owner.getUserName());
        }
    }

    private String getIndex(String userName) {
        return userName.toLowerCase().substring(0, 1);
    }

    private List<Loan> getLoansAtIndex(String index) {
        if (this.loans.get(index) == null) {
            this.loans.put(index, new ArrayList<>());
        }

        return this.loans.get(index);
    }

    private List<Account> getAccountsAtIndex(String index) {
        if (this.accounts.get(index) == null) {
            this.accounts.put(index, new ArrayList<>());
        }

        return this.accounts.get(index);
    }

    private void setOwnerInfo(Customer owner) {
        if (owner.getId() == 0) {
            owner.setId(++customerNumber);
        }
        owner.setBank(SessionService.getInstance().getBank());
    }

    private int generateNewAccountNumber() {
        return ++accountNumber;
    }

    private int generateNewLoanNumber() {
        return ++loanNumber;
    }

    /**
     * Some dummy data initialization
     */
    private void generateInitialData() {

        Bank bank = SessionService.getInstance().getBank();
        long defaultCreditLimit = 1500;

        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.MONTH, 1);

        Date dueDate = calendar.getTime();

        Customer alex = new Customer(0, 0, "Alex", "Emond", "aa", bank, "alex.emond@gmail.com", "514.111.2222");
        Customer justin = new Customer(0, 0, "Justin", "Paquette", "aa", bank, "justin.paquette@gmail.com", "514.111.2222");
        Customer maria = new Customer(0, 0, "Maria", "Etinger", "aa", bank, "maria.etinger@gmail.com", "514.111.2222");

        //Those 3 have an account on each bank
        createAccount(alex, defaultCreditLimit);
        createAccount(justin, defaultCreditLimit);
        createAccount(maria, defaultCreditLimit);

        if (bank == Bank.Royal) {
            Customer sylvain = new Customer(0, 0, "Sylvain", "Poudrier", "aa", bank, "sylvain.poudrier@gmail.com", "514.111.2222");
            createAccount(sylvain, defaultCreditLimit);

            createLoan(alex.getUserName(), 200, dueDate);
        }

        if (bank == Bank.National) {
            Customer pascal = new Customer(0, 0, "Pascal", "Groulx", "aa", bank, "pascal.groulx@gmail.com", "514.111.2222");
            createAccount(pascal, defaultCreditLimit);

            createLoan(alex.getUserName(), 300, dueDate);
        }

        if (bank == Bank.Dominion) {
            Customer max = new Customer(0, 0, "Max", "Tanquerel", "aa", bank, "max.tanquerel@gmail.com", "514.111.2222");
            createAccount(max, defaultCreditLimit);

            createLoan(justin.getUserName(), 1000, dueDate);
            createLoan(maria.getUserName(), 500, dueDate);
        }
    }

    /**
     * Use this test to test concurrent access
     *
     * @param owner
     */
    private void testConcurrentAccess(Customer owner, String ownerNameToProtect) {

        try {
            if (owner.getFirstName().equalsIgnoreCase(ownerNameToProtect)
//                    && Thread.currentThread().getId() == 14
                    ) {
                System.err.println(
                        String.format(
                                "THREAD #%d : NO OTHER THREAD CAN CREATE ACCOUNT WHILE I SLEEP FOR %s"
                                , Thread.currentThread().getId(), owner.getFirstName()
                        ));
                Thread.currentThread().sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
