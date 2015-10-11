package Contracts;

import Data.Customer;

public interface ILoggerService {

    IFileLogger getLogger(Customer currentCustomer);
}
