package functionalities;

import communication.Controller;
import main.Command;
import main.CommandWords;
import misc.LocalizedText;

/**
 * This class is called when the user execute the "help"
 * command.
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class Help extends Functionality
{

  public Help()
  {
    super();
  }

  /**
   * Help the player by remembering information about:
   * Its current room;
   * Available items in its room;
   * Available exits in its room;
   * Its available commands.
   * @param command the command the user sent to the program.
   */
  @Override
  public void run(Command command)
  {
    final StringBuilder builder = new StringBuilder();
    final CommandWords commandWords = new CommandWords(System.getProperty("user.dir") + "\\availableCommands");

    builder.append(LocalizedText.getText("lost"));
    builder.append(LocalizedText.getText("lost2"));
    builder.append(System.lineSeparator());
    builder.append(LocalizedText.getText("lost3"));
    builder.append(LocalizedText.getText("lost4", commandWords.getCommandString()));
    Controller.showMessageAndLog(builder);
  }
}
