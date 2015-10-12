package Server;

import Contracts.*;
import Data.Bank;
import Data.Customer;
import Data.CustomerInfo;
import Data.Loan;
import Data.ServerPorts;
import Services.CustomerService;
import Services.OpenAccountThread;
import Services.SessionService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class BankRMIServer implements ICustomerServer, IManagerServer {

    private static int _serverPort;
    private static ICustomerService _customerService;
    private static IManagerService _managerService;

    public static void main(String[] args) {

        //TODO: Logging!

        Bank serverName = Bank.fromInt(Integer.parseInt(args[0]));
        int serverPort = ServerPorts.fromBankName(serverName);

        try {
            (new BankRMIServer(serverPort)).exportServer();
            SessionService.getInstance().setBank(serverName);

            System.out.println(String.format("%s Server is up and running on port %d!", serverName, serverPort));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BankRMIServer(int serverPort)
    {
        _serverPort = serverPort;
        _customerService = new CustomerService();
    }

    public void exportServer() throws Exception {
        Remote obj = UnicastRemoteObject.exportObject(this, _serverPort);
        Registry r = LocateRegistry.createRegistry(_serverPort);
        r.bind("customer", obj);
    }


    @Override
    public int openAccount(Bank bank, String firstName, String lastName, String emailAddress, String phoneNumber, String password)
            throws RemoteException {

        //TODO: Real Implementation! (Create an actual account in the "database").


        Thread openAccountTask = new Thread(
                new OpenAccountThread(bank, firstName, lastName, emailAddress, phoneNumber,password));

        //sync!!

//        // pool of names that are being locked
//        HashSet<String> pool = new HashSet<String>();
//
//        lock(name)
//        synchronized(pool)
//        while(pool.contains(name)) // already being locked
//            pool.wait();           // wait for release
//        pool.add(name);            // I lock it
//
//        unlock(name)
//        synchronized(pool)
//        pool.remove(name);
//        pool.notifyAll();
//

        openAccountTask.start();

        _customerService.openAccount(bank, firstName, lastName, emailAddress, phoneNumber, password);

        return 433333;     //Temp value to be able to implement client.
    }

    @Override
    public Customer getCustomer(Bank bank, String email, String password) throws RemoteException {

        //TODO: Real Implementation!
        return new Customer(42, 4242, "TestFirstName", "TestLastName", "zaza", Bank.Royal);
    }

    @Override
    public Loan getLoan(Bank bank, int accountNumber, String password, long loanAmount)
            throws RemoteException {

        //TODO: Real implementation!
        return new Loan(0, 0, 0, new Date());
    }

    @Override
    public void delayPayment(Bank bank, int loanID, Date currentDueDate, Date newDueDate) {
        //TODO: Real implementation!
    }

    @Override
    public CustomerInfo[] getCustomersInfo(Bank bank) {
        //TODO: Real implementation!
        return new CustomerInfo[0];
    }
}
