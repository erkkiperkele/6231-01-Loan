
public interface ICustomerServices {

    int openAccount(int bankId, String FirstName, String LastName, String EmailAddress, String PhoneNumber, String Password);
    Loan getLoan(int bankId, int AccountNumber, String Password, long LoanAmount);

}
