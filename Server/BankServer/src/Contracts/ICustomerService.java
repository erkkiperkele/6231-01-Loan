package Contracts;

import Data.Bank;
import Data.Customer;
import Data.Loan;

import java.util.List;

/**
 * Defines the contract for the customer services.
 */
public interface ICustomerService {

    int openAccount(Bank bank, String firstName, String lastName, String emailAddress, String phoneNumber, String password);
    Customer getCustomer(String email);
    List<Loan> getLoan(int accountNumber);

}
