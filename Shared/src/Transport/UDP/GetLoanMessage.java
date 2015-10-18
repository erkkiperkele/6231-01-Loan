package Transport.UDP;

import java.io.*;

public class GetLoanMessage implements Serializable {


    private String firstName;
    private String lastName;

    public GetLoanMessage(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}


