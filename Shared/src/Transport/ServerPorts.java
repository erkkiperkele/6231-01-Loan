package Transport;

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
}
