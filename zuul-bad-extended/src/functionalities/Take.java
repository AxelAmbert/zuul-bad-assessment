package functionalities;

import communication.Controller;
import main.Command;
import main.Game;
import main.Room;
import misc.Item;
import misc.LocalizedText;
import player.Player;

/**
 * This class is called when the user execute the "take"
 * command.
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class Take extends Functionality
{

  /**
   * Take an object in the room.*
   * If the user don't specify an object, the operation is aborted.
   * If the user don't specify a valid object, the operation is aborted.
   * If the user don't have enough space in its inventory, the operation is aborted.
   * @param command the command the user sent to the program.
   */
  @Override
  public void run(Command command)
  {
    final Game game = Game.getGameInstance();
    final Player actualPlayer = game.getActualPlayer();
    final Room currentRoom = actualPlayer.getCurrentRoom();

    if (this.evaluateArgs(command, 1, "take_what") == false) {
      return;
    }

    final String itemDescription = command.getArgAt(1);
    if (currentRoom.getInventory().hasItem(itemDescription) == false) {
      // The item is not in the room
      Controller.showMessageAndLog(LocalizedText.getText("no_item_in_room", itemDescription));
      return;
    }
    final Item item = currentRoom.getInventory().takeItem(itemDescription);
    if (actualPlayer.canTake(item) == false) {
      // The player is carrying too much
      currentRoom.getInventory().insertItem(item);
      Controller.showMessageAndLog(LocalizedText.getText("too_heavy", itemDescription));
      return;
    }
    // OK we can pick it up
    actualPlayer.getInventory().insertItem(item);
  }
}
 