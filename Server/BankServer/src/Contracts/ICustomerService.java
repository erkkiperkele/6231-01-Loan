package Contracts;

import Data.Account;
import Data.Bank;
import Data.Customer;
import Data.Loan;

import javax.security.auth.login.FailedLoginException;
import java.util.List;

/**
 * Defines the contract for the customer services.
 */
public interface ICustomerService {

    int openAccount(Bank bank, String firstName, String lastName, String emailAddress, String phoneNumber, String password);
    Customer getCustomer(String email);
    List<Loan> getLoans(int accountNumber);
    Loan getLoan(Bank bank, int accountNumber, String password, long loanAmount) throws FailedLoginException;
    Account getAccount(String firstName, String LastName);

}
