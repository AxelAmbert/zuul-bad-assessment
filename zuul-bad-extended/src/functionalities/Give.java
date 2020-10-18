package functionalities;

import communication.Controller;
import main.Command;
import main.Game;
import main.Room;
import misc.LocalizedText;
import player.Player;

public class Give extends Functionality
{

  @Override
  public void run(Command command)
  {
    final Game game = Game.getGameInstance();
    final Player actualPlayer = game.getActualPlayer();
    final Room currentRoom = actualPlayer.getCurrentRoom();

    if (this.evaluateArgs(command, 1, "give_what") == false) {
      return;
    } else if (this.evaluateArgs(command, 2, "give_who") == false) {
      return;
    }

    final String item = command.getArgAt(1);
    final String whom = command.getArgAt(2);
    final Player playerToGive = currentRoom.searchForPlayer(whom);

    if (playerToGive == null) {
      // cannot give it if the chacter is not here
      Controller.showMessage(LocalizedText.getText("not_in_room", whom));
      return;
    } else if (actualPlayer.getInventory().hasItem(item) == false) {
      Controller.showMessageAndLog(LocalizedText.getText("give_no_item", item));
      return;
    }
    actualPlayer.getInventory().transferTo(playerToGive.getInventory(), item);
  }
}
