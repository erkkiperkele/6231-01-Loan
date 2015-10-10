package Contracts;

import Data.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;


/**
 * Defines the contract for the manager services.
 * Must be implemented by both the manager client and the bank server.
 */
public interface IManagerServer extends Remote {

    void delayPayment(int bankId, int loanID, Date currentDueDate, Date newDueDate)
            throws RemoteException;
    CustomerInfo[] getCustomersInfo(int bankId)
            throws RemoteException;
}
