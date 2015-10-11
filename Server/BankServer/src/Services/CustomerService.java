package Services;

import Contracts.ICustomerService;
import Data.Bank;
import Data.Customer;
import Data.Loan;

public class CustomerService implements ICustomerService {

    public CustomerService()
    {

    }

    @Override
    public int openAccount(Bank bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {

        //void openAccount threaded, need to sync creation for this (emailaddress + bank).
        //int get accountNumber: read operation, no need to protect access.

        return 0;
    }

    @Override
    public Customer getCustomer(Bank bank, String email, String password) {
        return null;
    }

    @Override
    public Loan getLoan(Bank bank, int accountNumber, String password, long loanAmount) {
        return null;
    }
}
