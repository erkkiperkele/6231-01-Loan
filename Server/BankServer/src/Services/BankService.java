package Services;

import Contracts.ICustomerService;
import Contracts.IManagerService;
import Data.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;
import java.util.List;

public class BankService implements ICustomerService, IManagerService {

    private DataRepository repository;

    public BankService()
    {
        repository = new DataRepository();
    }

    @Override
    public int openAccount(Bank bank, String firstName, String lastName, String email, String phone, String password) {


        Customer newCustomer = new Customer(firstName, lastName, password, email, phone);

        repository.createAccount(newCustomer);

        return repository.getAccount(newCustomer.getUserName()).getAccountNumber();
    }

    @Override
    public Customer getCustomer(String email)
    {

        return repository.getCustomer(email);
    }

    @Override
    public List<Loan> getLoan(int accountNumber) {

        return repository.getLoans(accountNumber);
    }

    @Override
    public void delayPayment(int bankId, int loanID, Date currentDueDate, Date newDueDate) {
        throw new NotImplementedException();
    }

    @Override
    public CustomerInfo[] getCustomersInfo(int bankId) {
        throw new NotImplementedException();
    }
}
