package Transport.RMI;

import Contracts.ICustomerService;
import Data.BankName;
import Data.Loan;
import Transport.ServerPorts;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class CustomerRMIClient implements ICustomerService {

    private final String policyPath = "./LoanManagerSecurity.policy";
    private ICustomerService _server;

    public CustomerRMIClient()
    {
        File test = new File(policyPath);
        boolean exists = test.exists();

        System.setProperty("java.security.policy",policyPath);

        _server = new CustomerRMIServer();

        try {
//            String serverUrl = "rmi://localhost:4242/customer";
            String serverUrl = String.format("rmi://localhost:%d/customer", ServerPorts.CustomerRMI.getPort()); // => "001"
            _server = (ICustomerService) Naming.lookup(serverUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public int openAccount(BankName bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password)
            throws RemoteException {
        int accountNumber = _server.openAccount(bankId, firstName, lastName, emailAddress, phoneNumber, password);
        return accountNumber;
    }

    @Override
    public Loan getLoan(int bankId, int accountNumber, String password, long loanAmount)
            throws RemoteException {
        return null;
    }
}

