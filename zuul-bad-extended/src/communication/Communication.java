package communication;

/** This interface handles communication with the user
 *  you can implement it to show message and ask
 *  the user for input
 * @author Axel Ambert
 * @version 1.0
 */

public interface Communication
{
  /**
   * Show a message
   * @param toShow the message to show
   */

  public void showMessage(String toShow);

  /**
   * Show an error
   * @param toShow the error to show
   */

  public void showError(String toShow);

  /**
   * Ask the user about a value, and return
   * the unprocessed value
   * @return The user answer
   */

  public String askUser();
}
