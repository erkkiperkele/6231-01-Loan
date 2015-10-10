package Transport;

import Data.BankName;

public enum ServerPorts {

    CustomerRMI,
    ManagerRMI,
    UDP;

    public int getPort(){
        switch(this)
        {
            case CustomerRMI:
                return 4242;

            case ManagerRMI:
                return 4343;

            case UDP:
                return 4444;

            default:
                return 0;
        }
    }



    public static int fromBankName(BankName bankName)
    {
        switch(bankName)
        {
            case Royal:
                return 4242;

            case National:
                return 4243;

            case Dominion:
                return 4244;

            default:
                return 0;
        }
    }
}
