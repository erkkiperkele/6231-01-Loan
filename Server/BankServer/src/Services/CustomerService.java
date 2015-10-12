package Services;

import Contracts.ICustomerService;
import Data.*;

import java.util.List;

public class CustomerService implements ICustomerService {

    private DataRepository repository;

    public CustomerService()
    {
        repository = new DataRepository();
    }

    @Override
    public int openAccount(Bank bank, String firstName, String lastName, String email, String phone, String password) {

        //void openAccount threaded, need to sync creation for this (emailaddress + bank).
        //int get accountNumber: read operation, no need to protect access.

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
}
