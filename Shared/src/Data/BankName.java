package Data;

public enum BankName {
    Royal,
    National,
    Dominion,
    Invalid;

    public static BankName fromInt(int bankId){
        switch(bankId)
        {
            case 1:
                return Royal;

            case 2:
                return National;

            case 3:
                return Dominion;

            default:
                return Invalid;
        }
    }

    public int toInt(){
        switch(this)
        {
            case Royal:
                return 1;

            case National:
                return 2;

            case Dominion:
                return 3;

            default:
                return 0;
        }
    }

    public int getPort(){
        switch(this)
        {
            case Royal:
                return 666;

            case National:
                return 667;

            case Dominion:
                return 668;

            default:
                return 0;
        }

    }

}
