package functionalities;

import communication.Controller;
import main.Command;
import main.Game;
import misc.LocalizedText;

/**
 * This class is called when the user execute the "quit"
 * command.
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class Quit extends Functionality
{

  /**
   * Quit the game.
   * Remove the player from the player list.
   * Call the onPlayerQuit event from the Game class.
   * If the user don't specify what does it want to quit, the operation is aborted.
   * @param command the command the user sent to the program.
   */
  @Override
  public void run(Command command)
  {
    final Game game = Game.getGameInstance();

    if (command.getNumberOfArgs() > 1) {
      Controller.showMessageAndLog(LocalizedText.getText("quit_what"));
    } else {
      game.onPlayerQuit();
    }
  }
}
