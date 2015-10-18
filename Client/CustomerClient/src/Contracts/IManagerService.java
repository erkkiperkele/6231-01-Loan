package Contracts;

import Data.Bank;
import Data.Customer;
import Data.CustomerInfo;

import javax.security.auth.login.FailedLoginException;
import java.util.Date;


/**
 * Defines the contract for the client's manager services.
 */
public interface IManagerService {

    Customer signIn(Bank bank, String email, String password)
            throws FailedLoginException;

    void delayPayment(Bank bank, int loanID, Date currentDueDate, Date newDueDate);

    CustomerInfo[] getCustomersInfo(Bank bank);
}
