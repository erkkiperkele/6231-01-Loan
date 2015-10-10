package Contracts;

import Data.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the customer's server contract (excludes any manager operation).
 */
public interface ICustomerServer extends Remote {

    int openAccount(BankName bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password)
            throws RemoteException;
    Loan getLoan(int bankId, int accountNumber, String password, long loanAmount)
            throws RemoteException;

}
