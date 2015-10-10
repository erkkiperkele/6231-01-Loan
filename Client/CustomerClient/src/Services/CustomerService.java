//package Services;
//
//
//import Contracts.ICustomerService;
//import Data.BankName;
//import Data.Loan;
//import Transport.UDP.UDPClient;
//
//import java.io.IOException;
//
//public class CustomerService implements ICustomerService {
//
//    private CustomerRMIClient _client;
//
//    @Override
//    public int openAccount(BankName bankId, String FirstName, String LastName, String EmailAddress, String PhoneNumber, String Password)
//            throws IOException {
//
//        //TODO: Implement:
//        // - Make it a thread!
//        // - Serializer
//        // - Deserializer
//
//        String message = "To Be Implemented";
//        String answer = _client.sendMessage(message, bankPort);
//
//        return 0;
//    }
//
//    @Override
//    public Loan getLoan(int bankId, int AccountNumber, String Password, long LoanAmount) {
//        return null;
//    }
//}
