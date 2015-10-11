package Contracts;

import Data.Customer;

public interface IFileLogger {
    void info(String message);
    void warning(String message);
    void error(String message);

    void setCurrentCustomer(Customer currentCustomer);
}
