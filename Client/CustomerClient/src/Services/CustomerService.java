package Services;


import Contracts.ICustomerService;
import Data.Bank;
import Data.Customer;
import Data.Loan;
import Transport.RMI.CustomerRMIClient;

import java.rmi.RemoteException;

public class CustomerService implements ICustomerService {

    private CustomerRMIClient[] _clients;

    public CustomerService() {
        initializeClients();
    }

    private void initializeClients() {
        _clients = new CustomerRMIClient[3];
        _clients[Bank.Royal.toInt() - 1] = new CustomerRMIClient(Bank.Royal);
        _clients[Bank.National.toInt() - 1] = new CustomerRMIClient(Bank.National);
        _clients[Bank.Dominion.toInt() - 1] = new CustomerRMIClient(Bank.Dominion);
    }

    @Override
    public int openAccount(
            Bank bank,
            String firstName,
            String lastName,
            String emailAddress,
            String phoneNumber,
            String password) {

        try {
            CustomerRMIClient client = _clients[bank.toInt() - 1];
            int accountNumber = client.openAccount(bank, firstName, lastName, emailAddress, phoneNumber, password);
            return accountNumber;
        } catch (RemoteException e) {
            //TODO: Better exception handling!
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Customer getCustomer(Bank bank, String email, String password) {

        try {
            return _clients[bank.toInt() - 1].getCustomer(bank, email, password);
        } catch (RemoteException e) {
            //TODO: Better exception handling!
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Loan getLoan(int bankId, int AccountNumber, String Password, long LoanAmount) {
        return null;
    }

}
