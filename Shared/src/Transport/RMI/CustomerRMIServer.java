package Transport.RMI;

import Contracts.ICustomerService;
import Data.BankName;
import Data.Loan;
import Transport.ServerPorts;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CustomerRMIServer implements ICustomerService {

    static final int _serverPort = ServerPorts.CustomerRMI.getPort();


    public static void main(String[] args) {

        try {
            (new CustomerRMIServer()).exportServer();
            System.out.println(String.format("Customer RMI Server is up and running on port %d!", _serverPort));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportServer() throws Exception {
        Remote obj = UnicastRemoteObject.exportObject(this, _serverPort);
        Registry r = LocateRegistry.createRegistry(_serverPort);
        r.bind("customer", obj);
    }


    @Override
    public int openAccount(BankName bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password)
            throws RemoteException {

        //TODO: Real Implementation! (Create an actual account in the "database").

        return 433333;     //Temp value to be able to implement client.
    }

    @Override
    public Loan getLoan(int bankId, int accountNumber, String password, long loanAmount)
            throws RemoteException {
        return null;
    }
}
