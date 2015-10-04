package Data;

public enum BankName {
    Royal,
    National,
    Dominion;

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

}
