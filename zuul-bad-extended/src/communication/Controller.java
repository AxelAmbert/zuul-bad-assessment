package communication;

public class Controller
{
    private static Controller instance;
    private Communication communication;

    private Controller()
    { }

    public Communication getCommunication()
    {
        return (this.communication);
    }

    public void setCommunication(Communication communication)
    {
        this.communication = communication;
    }

    public <T> void showMessage(T toShow)
    {
        this.communication.showMessage(toShow.toString());
    }

    public <T> void showError(T toShow)
    {
        Object ok;
        this.communication.showError(toShow.toString());
    }

    public String askUser() { return (this.communication.askUser()); }

    public static Controller getInstance()
    {
        if (instance == null)
            instance = new Controller();
        return (instance);
    }
}
