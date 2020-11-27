package player;

import communication.Controller;
import main.Room;
import misc.Observer;

/**
 * Class HumanPlayer - an human wandering in the (Zuul) game.
 * <p>
 * The HumanPlayer is an extension of the Player Class.
 * While asked to play, since it not an AI,
 * it will ask the user about what to do.
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class HumanPlayer extends Player
{
  private final int MAX_WEIGHT = 10;

  /**
   * Create an HumanPlayer instance.
   *
   * @param currentRoom the room where the HumanPlayer starts its adventure
   * @param playerName the HumanPlayer name
   */
  public HumanPlayer(Room currentRoom, String playerName)
  {
    super(currentRoom);
    this.inventory.setMaxWeight(this.MAX_WEIGHT);
    this.playerName = playerName;
  }

  /**
   * Do its next move.
   * Since it is not an AI, it asks the user for an input.
   *
   * @return the user input
   */
  @Override
  public String play()
  {
    return (Controller.askUser());
  }

  @Override
  public String getVisualRepresentation()
  {
    return ("images" + System.getProperty("file.separator") + "player.png");
  }
}
