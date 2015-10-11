package Data;

public enum Bank {
    Royal,
    National,
    Dominion,
    None;

    public static Bank fromInt(int bankId){
        switch(bankId)
        {
            case 1:
                return Royal;

            case 2:
                return National;

            case 3:
                return Dominion;

            default:
                return None;
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

}
