package Transport.RMI;

import Contracts.ICustomerServer;
import Contracts.IManagerServer;
import Data.Bank;
import Data.Customer;
import Data.CustomerInfo;
import Data.Loan;
import Data.ServerPorts;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class BankRMIServer implements ICustomerServer, IManagerServer {

    private static int _serverPort;

    public static void main(String[] args) {

        //TODO: Logging!

        Bank serverName = Bank.fromInt(Integer.parseInt(args[0]));
        int serverPort = ServerPorts.fromBankName(serverName);

        try {
            (new BankRMIServer(serverPort)).exportServer();
            System.out.println(String.format("%s Server is up and running on port %d!", serverName, serverPort));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BankRMIServer(int serverPort)
    {
        _serverPort = serverPort;
    }

    public void exportServer() throws Exception {
        Remote obj = UnicastRemoteObject.exportObject(this, _serverPort);
        Registry r = LocateRegistry.createRegistry(_serverPort);
        r.bind("customer", obj);
    }


    @Override
    public int openAccount(Bank bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password)
            throws RemoteException {

        //TODO: Real Implementation! (Create an actual account in the "database").

        return 433333;     //Temp value to be able to implement client.
    }

    @Override
    public Customer getCustomer(Bank bank, String email, String password) throws RemoteException {

        //TODO: Real Implementation!
        return new Customer(42, "TestFirstName", "TestLastName", Bank.Royal);
    }

    @Override
    public Loan getLoan(int bankId, int accountNumber, String password, long loanAmount)
            throws RemoteException {
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
