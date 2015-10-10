package Contracts;

import Data.CustomerInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;


/**
 * Defines the contract for the manager services.
 */
public interface IManagerService {

    void delayPayment(int bankId, int loanID, Date currentDueDate, Date newDueDate);
    CustomerInfo[] getCustomersInfo(int bankId);
}
