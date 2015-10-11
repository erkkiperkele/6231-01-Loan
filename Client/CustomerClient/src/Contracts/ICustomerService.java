package Contracts;

import Data.Bank;
import Data.Customer;
import Data.Loan;

/**
 * Defines the contract for the customer services.
 */
public interface ICustomerService {

    int openAccount(Bank bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password);
    Customer getCustomer(Bank bank, String email, String password);
    Loan getLoan(Bank bank, int accountNumber, String password, long loanAmount);

}
