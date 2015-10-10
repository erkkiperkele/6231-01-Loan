package Transport.RMI;

import Contracts.ICustomerServer;
import Data.Bank;
import Data.Customer;
import Data.Loan;
import Transport.ServerPorts;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class CustomerRMIClient implements ICustomerServer {

    private final String policyPath = "./LoanManagerSecurity.policy";
    private ICustomerServer _server;

    public CustomerRMIClient(Bank bank)
    {
        System.setProperty("java.security.policy",policyPath);

        int serverPort = ServerPorts.fromBankName(bank);
        _server = new BankRMIServer(serverPort);

        try {
            String serverUrl = String.format("rmi://localhost:%d/customer", ServerPorts.CustomerRMI.getPort()); // => "001"
            _server = (ICustomerServer) Naming.lookup(serverUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public int openAccount(Bank bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password)
            throws RemoteException {
        int accountNumber = _server.openAccount(bankId, firstName, lastName, emailAddress, phoneNumber, password);
        return accountNumber;
    }

    @Override
    public Customer getCustomer(Bank bank, String email, String password) throws RemoteException {
        return _server.getCustomer(bank, email, password);
    }

    @Override
    public Loan getLoan(int bankId, int accountNumber, String password, long loanAmount)
            throws RemoteException {
        return null;
    }
}

