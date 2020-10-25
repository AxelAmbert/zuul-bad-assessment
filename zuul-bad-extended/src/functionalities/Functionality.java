package functionalities;

import communication.Controller;
import main.Command;
import misc.LocalizedText;

/**
 * The main class that handle functionalities.
 * Inherit from this class if you want to implement
 * new functionalities to the Zuul Game.
 *
 * Once its created, the run method is always called.
 * To correctly inherits from this class you have to create
 * your own run command.
 *
 * @author Axel Ambert
 * @version 1.0
 */

public abstract class Functionality
{

  public Functionality()
  {
  }

  /**
   * This function is supposed to be called after a functionality
   * object is created.
   *
   * @param command the command the user sent to the program
   */
  public void run(Command command)
  {

  }

  /**
   * Check if the command has a certain number of arguments.
   * If the number of argument is less than wantedArgs, print
   * an LocalizedText error message thanks to key parameter.
   *
   * @param command the command the user sent to the program
   * @param wantedArgs number of desired arguments
   * @param key LocalizedText key to print if error
   */

  public boolean evaluateArgs(Command command, int wantedArgs, String key)
  {
    if (command.getNumberOfArgs() < wantedArgs) {
      Controller.showMessageAndLog(LocalizedText.getText(key));
      return (false);
    }
    return (true);
  }
}
