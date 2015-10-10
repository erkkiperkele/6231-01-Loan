package Services;


import Contracts.ICustomerService;
import Data.BankName;
import Data.Loan;
import Transport.RMI.CustomerRMIClient;

import java.rmi.RemoteException;

public class CustomerService implements ICustomerService {

    private CustomerRMIClient[] _clients;

    public CustomerService()
    {
        initializeClients();
    }

    private void initializeClients() {
        _clients = new CustomerRMIClient[3];
        _clients[BankName.Royal.toInt()-1] = new CustomerRMIClient(BankName.Royal);
        _clients[BankName.National.toInt()-1] = new CustomerRMIClient(BankName.National);
        _clients[BankName.Dominion.toInt()-1] = new CustomerRMIClient(BankName.Dominion);
    }

    @Override
    public int openAccount(BankName bankName, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {

        try {
            int accountNumber = _clients[bankName.toInt()-1].openAccount(bankName, firstName, lastName, emailAddress, phoneNumber, password);
            return accountNumber;
        } catch (RemoteException e) {
            //TODO: Better exception handling!
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Loan getLoan(int bankId, int AccountNumber, String Password, long LoanAmount) {
        return null;
    }
}
