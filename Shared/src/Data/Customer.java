package Data;

import java.io.Serializable;

public class Customer implements Serializable {

    private int id;

    private String firstName;
    private String lastName;
    private Bank bank;

    private int accountNumber;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return email;
    }


    private String email;

    public int getAccountNumber() {

        return accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public Bank getBank() {

        return bank;
    }

    public Customer(int id, int accountNumber, String firstName, String lastName, String password, Bank bank, String email) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bank = bank;
        this.password = password;
        this.email = email;
    }

    public String toString()
    {
        String displayInfo = "";

        displayInfo += (this.id + " - ");
        displayInfo += (this.accountNumber + " - ");
        displayInfo += (this.firstName + " - ");
        displayInfo += (this.lastName + " - ");
        displayInfo += (this.bank + " - ");
        displayInfo += (this.email);

        return displayInfo;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
}
