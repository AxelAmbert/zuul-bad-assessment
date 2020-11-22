package functionalities;

import communication.Controller;
import main.Command;
import main.Game;
import main.Room;
import misc.LocalizedText;
import player.Player;


/**
 * This class is called when the user execute the "go"
 * command.
 *
 * @author Axel Ambert
 * @version 1.0
 */
public class Go extends Functionality
{

  public Go()
  {
    super();
  }

  /**
   * Move the player into another room.
   * Check the exits in the player's current room.
   * If the user don't specify a room, the operation abort.
   * If the user don't specify a valid room, the operation is aborted.
   * @param command the command the user sent to the program.
   */
  @Override
  public void run(Command command)
  {
    final Game game = Game.getGameInstance();
    final Player actualPlayer = game.getActualPlayer();


    if (this.evaluateArgs(command, 1, "go_where") == false) {
      return;
    }

    final String direction = command.getArgAt(1);

    // Try to leave current room.
    final Room nextRoom = actualPlayer.getCurrentRoom().getExit(direction);

    if (nextRoom == null) {
      Controller.showMessageAndLog(LocalizedText.getText("no_door"));
      System.out.println("ARG [" + command.getArgAt(1) + "]");
    } else {
      actualPlayer.setCurrentRoom(nextRoom);
      Controller.showMessageAndLog(actualPlayer.getCurrentRoom().getFullDescription());
    }
  }
}
