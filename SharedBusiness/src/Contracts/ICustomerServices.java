package Contracts;

import Data.*;

/**
 * Defines the contract for the customer services.
 * Must be implemented by both the customer client and the bank server.
 */
public interface ICustomerServices {

    int openAccount(int bankId, String FirstName, String LastName, String EmailAddress, String PhoneNumber, String Password);
    Loan getLoan(int bankId, int AccountNumber, String Password, long LoanAmount);

}
