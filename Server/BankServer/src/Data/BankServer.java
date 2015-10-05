package Data;

import Contracts.IBankServer;
import Contracts.ICustomerService;
import Contracts.IManagerService;

import java.io.IOException;
import java.util.Date;

public class BankServer implements IBankServer, ICustomerService, IManagerService {

    private Customer[] customers;


    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public int openAccount(BankName bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password)
            throws IOException {
        return 0;
    }

    @Override
    public Loan getLoan(int bankId, int AccountNumber, String Password, long LoanAmount) {
        return null;
    }

    @Override
    public void delayPayment(int bankId, int loanID, Date currentDueDate, Date newDueDate) {

    }

    @Override
    public CustomerInfo[] getCustomersInfo(int bankId) {
        return new CustomerInfo[0];
    }
}
