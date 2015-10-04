package Contracts;

import Data.*;
import java.util.Date;


/**
 * Defines the contract for the manager services.
 * Must be implemented by both the manager client and the bank server.
 */
public interface IManagerServices {

    void delayPayment(int bankId, int loanID, Date currentDueDate, Date newDueDate);
    CustomerInfo[] getCustomersInfo(int bankId);
}
