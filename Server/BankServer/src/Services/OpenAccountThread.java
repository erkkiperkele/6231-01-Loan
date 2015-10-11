package Services;

import Data.Bank;

public class OpenAccountThread implements Runnable {

    private Bank bankId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String password;

    public OpenAccountThread(Bank bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {
        this.bankId = bankId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Override
    public void run() {
        openAccount(bankId, firstName, lastName, emailAddress, phoneNumber, password);
    }

    private void openAccount(Bank bankId, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {



        //void openAccount threaded, need to sync creation for this (emailaddress + bank).
        //int get accountNumber: read operation, no need to protect access.

    }
}
