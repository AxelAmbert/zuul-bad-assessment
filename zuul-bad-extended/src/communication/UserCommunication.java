package communication;

public class UserCommunication
{
    private static UserCommunication instance;
    private Communication communication;

    private UserCommunication()
    { }

    public Communication getCommunication()
    {
        return (this.communication);
    }

    public void setCommunication(Communication communication)
    {
        this.communication = communication;
    }

    public void showMessage(String toShow) { this.communication.showMessage(toShow); }

    public void showError(String toShow)
    {
        this.communication.showError(toShow);
    }

    public String askUser() { return (this.communication.askUser()); }

    public static UserCommunication getInstance()
    {
        if (instance == null)
            instance = new UserCommunication();
        return (instance);
    }
}
