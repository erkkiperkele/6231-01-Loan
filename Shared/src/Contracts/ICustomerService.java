package Contracts;

import Data.*;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the contract for the customer services.
 * Must be implemented by both the customer client and the bank server.
 */
public interface ICustomerService extends Remote {

    int openAccount(BankName bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password)
            throws RemoteException;
    Loan getLoan(int bankId, int accountNumber, String password, long loanAmount)
            throws RemoteException;

}
