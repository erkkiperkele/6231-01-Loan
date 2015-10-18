package Contracts;

import Data.*;
import Transport.RMI.RecordNotFoundException;

import javax.security.auth.login.FailedLoginException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;


/**
 * Defines the manager's server contract (excludes any manager operation).
 */
public interface IManagerServer extends Remote {

    void delayPayment(Bank bank, int loanID, Date currentDueDate, Date newDueDate)
            throws RemoteException, RecordNotFoundException;

    CustomerInfo[] getCustomersInfo(Bank bank)
            throws RemoteException, FailedLoginException;
}
