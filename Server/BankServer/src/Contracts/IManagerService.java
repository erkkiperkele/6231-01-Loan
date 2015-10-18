package Contracts;

import Data.Bank;
import Data.CustomerInfo;
import Transport.RMI.RecordNotFoundException;

import javax.security.auth.login.FailedLoginException;
import java.util.Date;


/**
 * Defines the contract for the client's manager services.
 */
public interface IManagerService {

    void delayPayment(Bank bank, int loanID, Date currentDueDate, Date newDueDate) throws RecordNotFoundException;
    CustomerInfo[] getCustomersInfo(Bank bank) throws FailedLoginException;
}
