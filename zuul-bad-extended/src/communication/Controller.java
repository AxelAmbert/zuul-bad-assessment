package communication;

import javax.naming.ldap.Control;

public class Controller
{
    public static Communication communication;

    private Controller()
    { }

    public static Communication getCommunication()
    {
        return (Controller.communication);
    }

    public static void setCommunication(Communication communication)
    {
        Controller.communication = communication;
    }

    public static <T> void showMessage(T toShow)
    {
        Controller.communication.showMessage(toShow.toString());
    }

    public static <T> void showError(T toShow)
    {
        Controller.communication.showError(toShow.toString());
    }

    public static String askUser() { return (Controller.communication.askUser()); }

}
