package Data;

import java.io.Serializable;

public class Customer implements Serializable{

    private String firstName;
    private String lastName;
    private Bank bank;

    //accountNumber
    //creditLimit
    //email
    //password


    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Bank getBank() {
        return bank;
    }

    public Customer(String firstName, String lastName, Bank bank)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bank = bank;
    }
}
