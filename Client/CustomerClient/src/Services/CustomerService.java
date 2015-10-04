package Services;


import Contracts.ICustomerService;
import Data.Loan;

public class CustomerService implements ICustomerService {


    @Override
    public int openAccount(int bankId, String FirstName, String LastName, String EmailAddress, String PhoneNumber, String Password) {
        return 0;
    }

    @Override
    public Loan getLoan(int bankId, int AccountNumber, String Password, long LoanAmount) {
        return null;
    }
}
