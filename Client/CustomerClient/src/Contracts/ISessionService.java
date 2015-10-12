package Contracts;

import Data.Customer;

/**
 * A simple interface for a Session Service
 * holding a current session info and giving access
 * to a logger.
 * Designed to be used as a Singleton.
 */
public interface ISessionService {

    void setCurrentCustomer(Customer currentCustomer);
    Customer getCurrentCustomer();
    IFileLogger log();
}
