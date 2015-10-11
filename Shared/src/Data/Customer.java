package Data;

import java.io.Serializable;

public class Customer implements Serializable {

    private int id;

    private String firstName;
    private String lastName;
    private Bank bank;

    //accountNumber
    //creditLimit
    //email
    //password

    public int getId() {
        return id;
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

    public Customer(int id, String firstName, String lastName, Bank bank) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bank = bank;
    }
}
