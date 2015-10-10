package Contracts;

import Data.*;

import java.rmi.Remote;
import java.util.Date;


/**
 * Defines the contract for the manager services.
 * Must be implemented by both the manager client and the bank server.
 */
public interface IManagerService extends Remote {

    void delayPayment(int bankId, int loanID, Date currentDueDate, Date newDueDate);
    CustomerInfo[] getCustomersInfo(int bankId);
}
