package Contracts;

import Data.BankName;
import Data.Loan;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the contract for the customer services.
 * Must be implemented by both the customer client and the bank server.
 */
public interface ICustomerService {

    int openAccount(BankName bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password);
    Loan getLoan(int bankId, int accountNumber, String password, long loanAmount);

}
