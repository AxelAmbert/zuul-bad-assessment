package communication;

import java.util.Scanner;

/** This class handle basic communication with the user
 *  via Command Line Interface. It shows a message with
 *  System.out.println and ask for user input with
 *  the java.util.Scanner class
 * @author Axel Ambert
 * @version 1.0
 */

public class CommandLineInterface implements Communication
{
  private final Scanner reader;

  /**
   * Create a CommandLineInterface way of communication
   * use this class to communicate with the user via
   * Command Line Interface
   */
  public CommandLineInterface()
  {
    this.reader = new Scanner(System.in);
  }

  /**
   * Print a message to the standard output to
   * communicate with the user
   * @param toShow the message to print
   */
  public void showMessage(String toShow)
  {
    System.out.print(toShow);
  }

  /**
   * Print a message to the error output to
   * communicate with the user
   * @param toShow the error to print
   */
  public void showError(String toShow)
  {
    System.err.print(toShow);
  }

  /**
   * Ask the user about a value in the terminal
   * the answer is non processed and may be null
   * @return The user answer
   */
  public String askUser()
  {
    return (reader.nextLine());
  }
}
