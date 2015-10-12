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

    public Customer(int id, int accountNumber, String firstName, String lastName, String password, Bank bank) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.password = password;
    }
}
