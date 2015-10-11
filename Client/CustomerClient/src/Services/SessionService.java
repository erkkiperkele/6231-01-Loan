package Services;

import Contracts.IFileLogger;
import Contracts.ILoggerService;
import Contracts.ISessionService;
import Data.Bank;
import Data.Customer;

/**
 * A singleton class to handle session information and logging services
 * It insures log are written for the current customer.
 */
public class SessionService implements ISessionService {
    private static SessionService ourInstance = new SessionService();
    private static Customer _currentCustomer;
    private static ILoggerService _loggerService;

    private SessionService() {
        _loggerService = new LoggerService();
        _currentCustomer = new Customer(0, "Default", "Customer", Bank.None);
    }

    public static SessionService getInstance() {
        return ourInstance;
    }

    @Override
    public void signIn(Customer currentCustomer) {

        //TODO: Implement Validation
        _currentCustomer = currentCustomer;
    }

    @Override
    public Customer getCurrentCustomer() {
        return _currentCustomer;
    }

    @Override
    public IFileLogger log() {
        return _loggerService.getLogger(_currentCustomer);
    }
}
