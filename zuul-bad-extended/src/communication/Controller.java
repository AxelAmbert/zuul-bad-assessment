package communication;

import javax.naming.ldap.Control;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * This class is the main class of the "World of Zuul" application. "World of
 * Zuul" is a very simple, text based adventure game. Users can walk around some
 * scenery. That's all. It should really be extended to make it more
 * interesting!
 * <p>
 * To play this game, create an instance of this class and call the "play"
 * method.
 * <p>
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class Controller
{
  public static Communication communication;
  private static final LinkedList<String> logs = new LinkedList<>();

  private Controller()
  {
  }

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

  public static <T> void showMessageAndLog(T toShow)
  {
    showMessage(toShow);
    addLogInfo(toShow);
  }

  public static <T> void showError(T toShow)
  {
    Controller.communication.showError(toShow.toString());
  }

  public static <T> void addLogInfo(T toLog)
  {
    Controller.logs.add(toLog.toString());
  }

  public static String getLastLog()
  {
    return (getNthLog(logs.size()));
  }

  public static String getNthLog(int nb)
  {
    if (nb > logs.size()) {
      nb = logs.size();
    } else if (nb < 1) {
      nb = 1;
    }
    return (logs.get(nb - 1));
  }

  public static String askUser()
  {
    return (Controller.communication.askUser());
  }

}
