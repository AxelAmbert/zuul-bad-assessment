package player;

import communication.Controller;
import main.Room;

import java.util.logging.Logger;

public class NPC extends Player
{
  /**
   * Create an NPC instance.
   * Generate a random name for the NPC.
   *
   * @param currentRoom the room where the NPC starts its adventure
   */
  public NPC(Room currentRoom)
  {
    super(currentRoom);
  }

  /**
   * Do its next move.
   * The AI is not yet implemented, feel free to do it.
   * Until then, it will just look at the its current position.
   *
   * @return the AI input
   */
  @Override
  public String play()
  {
    return ("look");
  }

  /**
   * Interpret the game answer to your last action.
   * You can use the Controller logs handling to achieve that.
   */
  @Override
  public void interpretGameAnswer()
  {
    String lastLog = Controller.getLastLog();
    // Interpret the game answer to your last action.
  }
}
