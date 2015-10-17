package Data;

import Services.LockFactory;
import Services.SessionService;

import java.util.*;
import java.util.stream.Collectors;

/**
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

    public Account getAccount(String userName) {
        String index = getIndex(userName);


//        List<Account> accounts = getAccountsAtIndex(index);
//        Account foundAccount = null;
//        for (Account account : accounts)
//        {
//            if (account.getOwner().getUserName().equalsIgnoreCase(userName))
//            {
//                return account;
//            }
//        }
//
//        return foundAccount;


//        //Iterator is not concurrent safe
//        //in case the list is being modified during iteration
//        //it'll throw a concurrentException error. Therefore we read data from a copy.
//        List<Account> accounts = new ArrayList<>(getAccountsAtIndex(index));
//        return accounts
//                .stream()
//                .filter(a -> a.getOwner().getUserName().equalsIgnoreCase(userName))
//                .findFirst()
//                .orElse(null);

        return getAccountsAtIndex(index)
                .stream()
                .filter(a -> a.getOwner().getUserName().equalsIgnoreCase(userName))
                .findFirst()
                .orElse(null);
//

    }

    public void createAccount(Customer owner) {

        if (getAccount(owner.getUserName()) != null) {
            SessionService.getInstance().log().info(
                    String.format("This Account already exists for %s", owner.getFirstName())
            );
            return;
        }

        setOwnerInfo(owner);    //PATCH: bad pattern.

        Account newAccount = new Account(generateNewAccountNumber(), owner);
        owner.setAccountNumber(newAccount.getAccountNumber());

        createAccountThreadSafe(owner, newAccount);
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

        int customerAccountNumber = getAccount(userName).getAccountNumber();
        Loan newLoan = new Loan(generateNewLoanNumber(), customerAccountNumber, amount, dueDate);

        createLoanThreadSafe(userName, newLoan);

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

    private void createLoanThreadSafe(String userName, Loan newLoan) {

        String index = getIndex(userName);

        LockFactory.getInstance().lock(userName);
        getLoansAtIndex(index).add(newLoan);
        LockFactory.getInstance().unlock(userName);
    }

    private void updateLoanThreadSafe(Customer customer, int loanNumber, Date newDueDate) {
        String index = getIndex(customer.getUserName());

        //TODO: check if stream modifies entity inside the collection....
        getLoansAtIndex(index)
                .stream()
                .filter(l -> l.getLoanNumber() == loanNumber)
                .findFirst()
                .orElse(null)      //TODO: dangerous, if loan doesn't exist, you end up with a null ref.
                .setDueDate(newDueDate);
    }

    private void createAccountThreadSafe(Customer owner, Account newAccount) {
        String index = getIndex(owner.getUserName());
        try {
            LockFactory.getInstance().lock(index);

            //UNCOMMENT FOR CONCURRENT CREATION TESTING
            //testConcurrentAccess(owner, "Concurrent1");

            if (getAccount(owner.getUserName()) != null) {
                SessionService.getInstance().log().info(
                        String.format(
                                "[CONCURRENCY] THREAD #%d : Account already created by another thread on %s",
                                Thread.currentThread().getId(),
                                owner.getFirstName())
                );
            } else {
                getAccountsAtIndex(index).add(newAccount);

                SessionService.getInstance().log().info(
                        String.format("Thread #%d CREATED account for %s", Thread.currentThread().getId(), owner.getFirstName())
                );
            }
        } finally {
            LockFactory.getInstance().unlock(index);
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
            Customer max = new Customer(0, 0, "Max", "Tanquerel", "mt", bank, "max.tanquerel@gmail.com", "514.111.2222");
            createAccount(max);

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
