package functionalities;

import communication.Controller;
import main.Command;
import main.Game;
import misc.LocalizedText;
import player.Player;

/**
 * This class is called when the user execute the "drop"
 * command.
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class Drop extends Functionality
{

  /**
   * Drop an object in the player's current room.
   * It will check if the player has the object it wants to drop.
   * If the condition is true, the player will lose its object and
   * give it to its current room.
   * If the user don't specify an object, the operation is aborted.
   * @param command the command the user sent to the program.
   */
  @Override
  public void run(Command command)
  {
    String item;
    final Game game = Game.getGameInstance();
    final Player actualPlayer = game.getActualPlayer();

    if (this.evaluateArgs(command, 1, "drop_arg_<1") == false) {
      return;
    }
    item = command.getArgAt(1);
    if (actualPlayer.getInventory().hasItem(item) == false) {
      Controller.showMessageAndLog(LocalizedText.getText("drop_no_item", item));
      return;
    }
    actualPlayer.getInventory().transferTo(actualPlayer.getCurrentRoom().getInventory(), item);
  }
}
