package communication;

import javax.naming.ldap.Control;
import java.io.PrintWriter;
import java.util.LinkedList;

/** This class handle every possible communication with
 *  the users and with potential AIs.
 *  <p>
 *  Use it to the user for an input, show him a message
 *  and log every important information so AI can read it
 *  too.
 *  <p>
 *  Note that before using it, you have to instantiate the
 *  "communication" static variable with your chosen way of
 *  communication.
 * @author Axel Ambert
 * @version 1.0
 */

public class Controller
{
  public static Communication communication;
  private static final LinkedList<String> logs = new LinkedList<>();

  /**
   * private constructor of the Controller class
   * no need to call it since its a static class
   * like System.
   */

  private Controller()
  {
  }

  /**
   * Show an error
   * @return Communication return the communication
   * object of he Controller
   */

  public static Communication getCommunication()
  {
    return (Controller.communication);
  }

  /**
   * Set the communication object with your chosen communication way
   * You must call this function before using the Controller
   * object, otherwise it won't works.
   * @param communication communication object to init
   */

  public static void setCommunication(Communication communication)
  {
    Controller.communication = communication;
  }

  /**
   * This function call your chosen communication
   * way to show a message to the user.
   * @param toShow message to show
   */

  public static <T> void showMessage(T toShow)
  {
    Controller.communication.showMessage(toShow.toString());
  }

  /**
   * This function call your chosen communication
   * way to show a message to the user. It will also log the
   * message in the log list, so a computer can see the
   * messages.
   * @param toShow message to show
   */

  public static <T> void showMessageAndLog(T toShow)
  {
    showMessage(toShow);
    addLogInfo(toShow);
  }

  /**
   * This function call your chosen communication
   * way to show an error to the user.
   * @param toShow error to show
   */

  public static <T> void showError(T toShow)
  {
    Controller.communication.showError(toShow.toString());
  }

  /**
   * This function store a log message that you want to
   * keep in the log list.
   * @param toLog log to store in the logs list
   */
  public static <T> void addLogInfo(T toLog)
  {
    Controller.logs.add(toLog.toString());
  }

  /**
   * This function return the last log stored in the log list.
   * @return the last log of the log list
   */

  public static String getLastLog()
  {
    return (getNthLog(logs.size()));
  }


  /**
   * This function return the nth log stored in the log list.
   * If nb is greater that the list size, it returns the last element.
   * If nb is < 1, it returns the first element.
   * If there is not element, it returns null.
   * @param nb the log number that you want to get
   * @return the nth log in the log list
   */

  public static String getNthLog(int nb)
  {
    if (logs.isEmpty())
      return (null);
    if (nb > logs.size()) {
      nb = logs.size();
    } else if (nb < 1) {
      nb = 1;
    }
    return (logs.get(nb - 1));
  }

  /**
   * This function call your chosen communication
   * way to ask the user for an input.
   * @return the user input as a String, that can be null
   */

  public static String askUser()
  {
    return (Controller.communication.askUser());
  }

}
