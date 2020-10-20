package functionalities;

import communication.Controller;
import main.Command;
import main.Game;
import main.Room;
import player.Player;

/**
 * This class is called when the user execute the "look"
 * command.
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class Look extends Functionality
{

  /**
   * Look what's inside the room.
   * It will tell the full description of its current room,
   * which include :
   * The room description;
   * The room inventory;
   * The room exits.
   * @param command the command the user sent to the program.
   */

  @Override
  public void run(Command command)
  {
    final Game game = Game.getGameInstance();
    final Player actualPlayer = game.getActualPlayer();
    final Room currentRoom = actualPlayer.getCurrentRoom();

    Controller.showMessageAndLog(currentRoom.getFullDescription());
  }
}
