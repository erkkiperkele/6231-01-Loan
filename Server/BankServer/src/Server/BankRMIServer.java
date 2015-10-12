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
import java.util.List;

public class BankRMIServer implements ICustomerServer, IManagerServer {

    private static int _serverPort;
    private static ICustomerService _customerService;
    private static IManagerService _managerService;

    public static void main(String[] args) {

        //TODO: Logging!

        Bank serverName = Bank.fromInt(Integer.parseInt(args[0]));
        int serverPort = ServerPorts.fromBankName(serverName);
        SessionService.getInstance().setBank(serverName);

        try {
            (new BankRMIServer(serverPort)).exportServer();

            SessionService.getInstance().log().info(String.format("%s Server is up and running on port %d!", serverName, serverPort));

            initialTesting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initialTesting() {
        String unknownUsername = "dummy@dummy.com";
        Customer unknown = _customerService.getCustomer(unknownUsername);
        printCustomer(unknown, unknownUsername);

        String mariaUsername = "maria.etinger@gmail.com";
        Customer maria = _customerService.getCustomer(mariaUsername);
        printCustomer(maria, mariaUsername);

        String justinUsername = "justin.paquette@gmail.com";
        Customer justin = _customerService.getCustomer(justinUsername);
        printCustomer(justin, justinUsername);

        String alexUserName = "alex.emond@gmail.com";
        Customer alex = _customerService.getCustomer(alexUserName);
        printCustomer(alex, alexUserName);

        List<Loan> alexLoans = _customerService.getLoan(alex.getAccountNumber());
        printLoans(alexLoans, alex.getFirstName());
    }

    private static void printCustomer(Customer customer, String username) {
        if (customer != null)
        {
            SessionService.getInstance().log().info(customer.toString());
        }
        else
        {
            SessionService.getInstance().log().info(String.format("No customer found for this username %s", username));
        }
    }

    private static void printLoans(List<Loan> loans, String customerName)
    {
        if (loans != null && loans.size() >0) {
            for (Loan loan : loans) {
                SessionService.getInstance().log().info(loan.toString());
            }
        }
        else{
            SessionService.getInstance().log().info(String.format("%1$s has no loans currently", customerName));
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


//        Thread openAccountTask = new Thread(
//                new OpenAccountThread(bank, firstName, lastName, emailAddress, phoneNumber,password));
//        openAccountTask.start();

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


        _customerService.openAccount(bank, firstName, lastName, emailAddress, phoneNumber, password);

        return 433333;     //Temp value to be able to implement client.
    }

    @Override
    public Customer getCustomer(Bank bank, String email, String password) throws RemoteException {

        //TODO: Real Implementation!
        return new Customer(42, 4242, "TestFirstName", "TestLastName", "zaza", Bank.Royal, "test.littletest@gmail.com");
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
