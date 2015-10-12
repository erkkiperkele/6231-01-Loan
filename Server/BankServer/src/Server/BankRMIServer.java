package Server;

import Contracts.*;
import Data.Bank;
import Data.Customer;
import Data.CustomerInfo;
import Data.Loan;
import Data.ServerPorts;
import Services.BankService;
import Services.SessionService;

import javax.security.auth.login.FailedLoginException;
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

            _customerService = new BankService();

            SessionService.getInstance().log().info(String.format("%s Server is up and running on port %d!", serverName, serverPort));


//            testInitial();
            testOpeningMultipleAccounts();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BankRMIServer(int serverPort) {
        _serverPort = serverPort;
    }

    public void exportServer() throws Exception {
        Remote obj = UnicastRemoteObject.exportObject(this, _serverPort);
        Registry r = LocateRegistry.createRegistry(_serverPort);
        r.bind("customer", obj);
    }


    @Override
    public int openAccount(Bank bank, String firstName, String lastName, String emailAddress, String phoneNumber, String password)
            throws RemoteException {

        //TODO: Synchronize access!


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


        int accountNumber = _customerService.openAccount(bank, firstName, lastName, emailAddress, phoneNumber, password);

        return accountNumber;
    }

    @Override
    public Customer getCustomer(Bank bank, String email, String password)
            throws RemoteException, FailedLoginException {

        Customer foundCustomer = _customerService.getCustomer(email);

        if (!foundCustomer.getPassword().equals(password)) {
            throw new FailedLoginException(String.format("Wrong password for email %s", email));
        }

        return foundCustomer;
    }

    @Override
    public Customer signIn(Bank bank, String email, String password) throws RemoteException, FailedLoginException {
        return getCustomer(bank, email, password);
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

    private static void testInitial() {
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

    private static void testOpeningMultipleAccounts() {
        System.out.println(String.format("Start of concurrent account creation"));

        Bank bank = SessionService.getInstance().getBank();

        String firstName = "Concurrent";
        String lastName = "concu";
        String email = "concurrent@thread.com";
        String phone = "";
        String password = "c";

        int threadNumber = 1;
//        Thread openAccountTask = new Thread(
//                new OpenAccountThread(bank, firstName, lastName, emailAddress, phoneNumber,password));
//        openAccountTask.start();


        Thread openAccountTask1 = new Thread(() ->
        {
            _customerService.openAccount(bank, firstName + "1", lastName, email + "1", phone, password);
            System.out.println(String.format("thread #%d OPENED an account for %s1", Thread.currentThread().getId(), firstName));
        });

        Thread openAccountTask2 = new Thread(() ->
        {
            _customerService.openAccount(bank, firstName + "2", lastName, email + "2", phone, password);
            System.out.println(String.format("thread #%d OPENED an account for %s2", Thread.currentThread().getId(), firstName));
        });

        Thread openAccountTask3 = new Thread(() ->
        {
            _customerService.openAccount(bank, firstName + "3", lastName, email + "3", phone, password);
            System.out.println(String.format("thread #%d OPENED an account for %s3", Thread.currentThread().getId(), firstName));
        });

        Thread openAccountTask4 = new Thread(() ->
        {
            _customerService.openAccount(bank, firstName + "4", lastName, email + "4", phone, password);
            System.out.println(String.format("thread #%d OPENED an account for %s4", Thread.currentThread().getId(), firstName));
        });

        Thread openAccountTask5 = new Thread(() ->
        {
            _customerService.openAccount(bank, firstName + "5", lastName, email + "5", phone, password);
            System.out.println(String.format("thread #%d OPENED an account for %s5", Thread.currentThread().getId(), firstName));
        });

        Thread openAccountTask6 = new Thread(() ->
        {
            _customerService.openAccount(bank, firstName + "6", lastName, email + "6", phone, password);
            System.out.println(String.format("thread #%d OPENED an account for %s6", Thread.currentThread().getId(), firstName));
        });

        Thread openAccountTask7 = new Thread(() ->
        {
            _customerService.openAccount(bank, firstName + "7", lastName, email + "7", phone, password);
            System.out.println(String.format("thread #%d OPENED an account for %s7", Thread.currentThread().getId(), firstName));
        });

        Thread openAccountTask8 = new Thread(() ->
        {
            _customerService.openAccount(bank, firstName + "8", lastName, email + "8", phone, password);
            System.out.println(String.format("thread #%d OPENED an account for %s8", Thread.currentThread().getId(), firstName));
        });

        openAccountTask1.start();
        openAccountTask2.start();
        openAccountTask3.start();
        openAccountTask4.start();
        openAccountTask5.start();
        openAccountTask6.start();
        openAccountTask7.start();
        openAccountTask8.start();

        System.out.println(String.format("End of concurrent account creation"));


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


//        int accountNumber = _customerService.openAccount(bank, firstName, lastName, emailAddress, phoneNumber, password);

    }

    private static void printCustomer(Customer customer, String username) {
        if (customer != null) {
            SessionService.getInstance().log().info(customer.toString());
        } else {
            SessionService.getInstance().log().info(String.format("No customer found for this username %s", username));
        }
    }

    private static void printLoans(List<Loan> loans, String customerName) {
        if (loans != null && loans.size() > 0) {
            for (Loan loan : loans) {
                SessionService.getInstance().log().info(loan.toString());
            }
        } else {
            SessionService.getInstance().log().info(String.format("%1$s has no loans currently", customerName));
        }
    }
}
