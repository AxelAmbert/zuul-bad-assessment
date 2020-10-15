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

    public void showMessage(String toShow) { this.communication.showMessage(toShow); }

    public void showError(String toShow)
    {
        this.communication.showError(toShow);
    }

    public String askUser() { return (this.communication.askUser()); }

    public static Controller getInstance()
    {
        if (instance == null)
            instance = new Controller();
        return (instance);
    }
}
