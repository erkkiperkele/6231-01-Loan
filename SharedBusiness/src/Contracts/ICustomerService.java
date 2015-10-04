package Contracts;

import Data.*;

/**
 * Defines the contract for the customer services.
 * Must be implemented by both the customer client and the bank server.
 */
public interface ICustomerService {

    int openAccount(int bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password);
    Loan getLoan(int bankId, int accountNumber, String password, long loanAmount);

}
