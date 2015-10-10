package Transport.RMI;

import Contracts.ICustomerServer;
import Data.BankName;
import Data.Loan;
import Transport.ServerPorts;
import com.sun.corba.se.spi.activation.Server;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class CustomerRMIClient implements ICustomerServer {

    private final String policyPath = "./LoanManagerSecurity.policy";
    private ICustomerServer _server;

    public CustomerRMIClient(BankName bankName)
    {
        System.setProperty("java.security.policy",policyPath);

        int serverPort = ServerPorts.fromBankName(bankName);
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

