package Services;


import Contracts.ICustomerService;
import Data.BankName;
import Data.Loan;
import Transport.UDPClient;

import java.io.IOException;

public class CustomerService implements ICustomerService {

    private UDPClient _client;

    public CustomerService(UDPClient client)
    {
        _client = client;
    }

    @Override
    public int openAccount(BankName bankId, String FirstName, String LastName, String EmailAddress, String PhoneNumber, String Password)
            throws IOException {

        //TODO: Implement:
        // - Serializer
        // - Deserializer

        String message = "To Be Implemented";
        int bankPort = bankId.getPort();
        String answer = _client.sendMessage(message, bankPort);

        return 0;
    }

    @Override
    public Loan getLoan(int bankId, int AccountNumber, String Password, long LoanAmount) {
        return null;
    }
}
