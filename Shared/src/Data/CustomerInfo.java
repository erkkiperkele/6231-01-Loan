package Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerInfo implements Serializable{

    private String userName;
    private Account account;
    private List<Loan> loans;

    public CustomerInfo(Account account, List<Loan> loans)
    {
        this.userName = account.getOwner().getUserName();
        this.account = account;
        this.loans = loans;
    }

    public String getUserName() {
        return userName;
    }

    public Account getAccount() {
        return account;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public String toString(){

        String newLine = "\n";
        String tab = "\t";

        String loansInfo = loans
                .stream()
                .map(l -> l.toString())
                .collect(Collectors.joining(newLine + tab + " "));

        loansInfo = loansInfo.isEmpty() ? "No Loan" : loansInfo;

        loansInfo += newLine;

        String formattedString = String.format(
            "User: %1$s %2$s %3$s" +
                    "%4$s Account info: %5$s %3$s" +
                    "%4$s Loans info: %3$s %4$s %6$s",
                account.getOwner().getFirstName(),
                account.getOwner().getLastName(),
                newLine,
                tab,
                account.getOwner().toString(),
                loansInfo
        );
        return formattedString;
    }

}
