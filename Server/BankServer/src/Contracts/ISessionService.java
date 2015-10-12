package Contracts;

import Data.Bank;
import Data.Customer;

/**
 * A simple interface for a Session Service
 * holding a current session info and giving access
 * to a logger.
 * Designed to be used as a Singleton.
 */
public interface ISessionService {

    void signIn(Customer currentCustomer);
    Customer getCurrentCustomer();
    Bank getBank();
    void setBank(Bank bank);
    IFileLogger log();
}
