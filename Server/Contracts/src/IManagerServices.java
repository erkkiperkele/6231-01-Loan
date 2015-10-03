import java.util.Date;

public interface IManagerServices {

    void delayPayment(int bankId, int loanID, Date currentDueDate, Date newDueDate);
    CustomerInfo[] getCustomersInfo(int bankId);
}
