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
    private static Customer currentCustomer;
    private static ILoggerService loggerService;
    private Bank bank;

    private SessionService() {
        loggerService = new LoggerService();
        currentCustomer = new Customer(0, 0, "Default", "Customer", "none", Bank.None, "default.none@gmail.com");
    }

    public static SessionService getInstance() {
        return ourInstance;
    }

    @Override
    public void signIn(Customer currentCustomer) {

        //TODO: Implement Validation
        SessionService.currentCustomer = currentCustomer;
    }

    @Override
    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    @Override
    public Bank getBank() {
        return this.bank;
    }

    @Override
    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public IFileLogger log() {

        return loggerService.getLogger();
    }
}
