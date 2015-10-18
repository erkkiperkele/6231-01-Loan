package Services;

import Contracts.ICustomerService;
import Contracts.IManagerService;
import Data.*;
import Transport.RMI.RecordNotFoundException;
import Transport.UDP.GetLoanMessage;
import Transport.UDP.UDPClient;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.security.auth.login.FailedLoginException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BankService implements ICustomerService, IManagerService {

    private DataRepository repository;
    private UDPClient udp;
    private final long DEFAULT_CREDIT_LIMIT = 1500;

    public BankService() {
        repository = new DataRepository();
        udp = new UDPClient();
    }

    @Override
    public int openAccount(Bank bank, String firstName, String lastName, String email, String phone, String password) {


        Customer newCustomer = new Customer(firstName, lastName, password, email, phone);

        repository.createAccount(newCustomer, DEFAULT_CREDIT_LIMIT);

        return repository.getAccount(newCustomer.getUserName()).getAccountNumber();
    }

    @Override
    public Customer getCustomer(String email) {

        return repository.getCustomer(email);
    }

    @Override
    public List<Loan> getLoans(int accountNumber) {

        return repository.getLoans(accountNumber);
    }

    @Override
    public Loan getLoan(Bank bank, int accountNumber, String password, long loanAmount) throws FailedLoginException {

        Account account = repository.getAccount(accountNumber);
        Customer customer = account.getOwner();
        String userName = account.getOwner().getUserName();

        if(!customer.getPassword().equals(password))
        {
            throw new FailedLoginException(String.format("Wrong password for account %d", accountNumber));
        }

        LockFactory.getInstance().writeLock(userName);

        long internalLoansAmount = repository.getLoans(accountNumber)
                .stream()
                .mapToLong(l -> l.getAmount())
                .sum();


        long externalLoansAmount = getExternalLoans(customer.getFirstName(), customer.getLastName());

        long currentCredit = externalLoansAmount + internalLoansAmount;

        Loan newLoan = null;
        if (currentCredit + loanAmount < account.getCreditLimit())
        {
            Calendar calendar = Calendar.getInstance();
            calendar.add(calendar.MONTH, 6);
            Date dueDate = calendar.getTime();

            newLoan = repository.createLoan(userName, loanAmount, dueDate);
            SessionService.getInstance().log().info(
                    String.format("new Loan accepted for %s (%d$), current credit: %d$",
                            customer.getFirstName(),
                            newLoan.getAmount(),
                            loanAmount + currentCredit)
            );
        }

        LockFactory.getInstance().writeUnlock(userName);
        return newLoan;
    }

    @Override
    public Account getAccount(String firstName, String lastName) {
        return repository.getAccount(firstName, lastName);
    }

    @Override
    public void delayPayment(Bank bank, int loanId, Date currentDueDate, Date newDueDate) throws RecordNotFoundException {
        //Note: in the context of this assignment, the current due date is not verified.
        repository.updateLoan(loanId, newDueDate);
    }

    @Override
    public CustomerInfo[] getCustomersInfo(int bankId) {
        throw new NotImplementedException();
    }

    //TODO: change for a List of Loan (serialization deserialization)
    private long getExternalLoans(String firstName, String lastName) {
        Bank currentBank = SessionService.getInstance().getBank();

        long externalLoans = 0;

        try {
            if (currentBank != Bank.National) {
                long externalLoan = getLoanAtBank(Bank.National, firstName, lastName);
                externalLoans += externalLoan;
            }
            if (currentBank != Bank.Royal) {
                long externalLoan = getLoanAtBank(Bank.Royal, firstName, lastName);
                externalLoans += externalLoan;
            }
            if (currentBank != Bank.Dominion) {
                long externalLoan = getLoanAtBank(Bank.Dominion, firstName, lastName);
                externalLoans += externalLoan;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return externalLoans;
    }

    private long getLoanAtBank(Bank bank, String firstName, String lastName) throws ClassNotFoundException {

        long externalLoan = 0;
        try {

            Transport.UDP.Serializer getLoanMessageSerializer = new Transport.UDP.Serializer<GetLoanMessage>();
            Transport.UDP.Serializer getLoanSerializer = new Transport.UDP.Serializer<Long>();
            GetLoanMessage message = new GetLoanMessage(firstName, lastName);

            byte[] data = getLoanMessageSerializer.serialize(message);


            externalLoan = (Long)getLoanSerializer.deserialize(udp.sendMessage(data, ServerPorts.getUDPPort(bank)));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return externalLoan;
    }
}
