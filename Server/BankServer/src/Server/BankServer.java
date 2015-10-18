package Server;

import Contracts.*;
import Data.*;
import Services.BankService;
import Services.SessionService;
import Transport.UDP.UDPServer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.security.auth.login.FailedLoginException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

public class BankServer implements ICustomerServer, IManagerServer {

    private static long CREDIT_LIMIT = 1500;

    private static int _serverPort;
    private static ICustomerService _customerService;
    private static IManagerService _managerService;

    private static UDPServer udp;

    public BankServer(int serverPort) {
        _serverPort = serverPort;
    }

    public static void main(String[] args) {

        String serverArg = args[0];
        initialize(serverArg);

        //Starting bank server
        startRMIServer();
        startUDPServer();

        //Server tests
        testInitial();
        testOpeningMultipleAccounts();
        testUDPGetLoan();
    }

    private static void initialize(String arg) {
        Bank serverName = Bank.fromInt(Integer.parseInt(arg));
        SessionService.getInstance().setBank(serverName);
        _customerService = new BankService();
        udp = new UDPServer(_customerService);
    }

    private static void startRMIServer() {

        Bank bank = SessionService.getInstance().getBank();
        int serverPort = ServerPorts.fromBankName(bank);

        try {
            (new BankServer(serverPort)).exportServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SessionService.getInstance().log().info(
                String.format("%s Server is up and running on port %d!",
                        bank,
                        serverPort)
        );
    }

    private static void startUDPServer() {
        Thread startUdpServer = new Thread(() ->
        {
            udp.startServer();
        });
        startUdpServer.start();
    }

    public void exportServer() throws Exception {
        Remote obj = UnicastRemoteObject.exportObject(this, _serverPort);
        Registry r = LocateRegistry.createRegistry(_serverPort);
        r.bind("customer", obj);
    }


    @Override
    public int openAccount(Bank bank, String firstName, String lastName, String emailAddress, String phoneNumber, String password)
            throws RemoteException {

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
            throws RemoteException, FailedLoginException {

        Loan newLoan = _customerService.getLoan(bank, accountNumber, password, loanAmount);
        return newLoan;
    }

    @Override
    public void delayPayment(Bank bank, int loanID, Date currentDueDate, Date newDueDate) {
        //TODO: Real implementation!
        throw new NotImplementedException();
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

        List<Loan> alexLoans = _customerService.getLoans(alex.getAccountNumber());
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

        Thread openAccountCreatedByTask1 = new Thread(() ->
        {
            System.out.println(String.format("thread #%d STARTING an account for %s1", Thread.currentThread().getId(), firstName));
            _customerService.openAccount(bank, firstName + "1", lastName, email + "1", phone, password);
            System.out.println(String.format("thread #%d OPENED an account for %s1", Thread.currentThread().getId(), firstName));
        });

//        Thread getAccount = new Thread(() ->
//        {
//            _customerService.getCustomer(email + "1");
//            System.out.println(String.format("thread #%d OPENED an account for %s1", Thread.currentThread().getId(), firstName));
//        });

        openAccountTask1.start();
        openAccountTask2.start();
        openAccountTask3.start();
        openAccountTask4.start();
        openAccountTask5.start();
        openAccountTask6.start();
        openAccountTask7.start();
        openAccountTask8.start();
        openAccountCreatedByTask1.start();

        System.out.println(String.format("End of concurrent account creation"));
    }

    private static void testUDPGetLoan() {

        Bank bank = SessionService.getInstance().getBank();

        if (bank == Bank.Royal) {

            int accountNumber = 2;
            String password = "aa";
            long loanAmount = 200;


            //Wait for all servers to start before sending a message.
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                _customerService.getLoan(bank, accountNumber, password, loanAmount);
            } catch (FailedLoginException e) {
                e.printStackTrace();
            }
        }
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