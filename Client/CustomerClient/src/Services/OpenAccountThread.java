//package Services;
//
//import Data.Bank;
//import Transport.RMI.CustomerRMIClient;
//
//import java.rmi.RemoteException;
//
//public class OpenAccountThread implements Runnable {
//
//    public int get_accountNumber() {
//        return _accountNumber;
//    }
//
//    private int _accountNumber;
//
//    private CustomerRMIClient _client;
//    private Bank _bank;
//    private String _firstName;
//    private String _lastName;
//    private String _emailAddress;
//    private String _phoneNumber;
//    private String _password;
//
//    public OpenAccountThread(CustomerRMIClient client, Bank bank, String firstName, String lastName, String email, String phone, String password) {
//        this._client = client;
//        this._bank = bank;
//        this._firstName = firstName;
//        this._lastName = lastName;
//        this._emailAddress = email;
//        this._phoneNumber = phone;
//        this._password = password;
//    }
//
//    @Override
//    public void run() {
//
//        try {
//            _accountNumber = _client.openAccount(_bank, _firstName, _lastName, _emailAddress, _phoneNumber, _password);
//        } catch (RemoteException e) {
//            //TODO: Better exception handling!
//            e.printStackTrace();
//        }
//    }
//}
