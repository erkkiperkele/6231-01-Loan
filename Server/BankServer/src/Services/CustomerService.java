package Services;

import Contracts.ICustomerService;
import Data.Bank;
import Data.Customer;
import Data.DataRepository;
import Data.Loan;

import java.util.List;

public class CustomerService implements ICustomerService {

    private DataRepository repository;

    public CustomerService()
    {
        repository = new DataRepository();
    }

    @Override
    public int openAccount(Bank bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {

        //void openAccount threaded, need to sync creation for this (emailaddress + bank).
        //int get accountNumber: read operation, no need to protect access.

        return 0;
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
