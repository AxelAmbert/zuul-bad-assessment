package communication;

public class UserCommunication
{
    private static UserCommunication instance;
    private Communication communication;

    public UserCommunication()
    { }

    public Communication getCommunication()
    {
        return (this.communication);
    }

    public void setCommunication(Communication communication)
    {
        this.communication = communication;
    }

    public static UserCommunication getInstance()
    {
        if (instance == null)
            instance = new UserCommunication();
        return (instance);
    }
}
