package player;

import main.Room;
import misc.Inventory;
import misc.Item;
import misc.Observable;

import java.util.Random;

/**
 * Class Player - a body without a soul.
 * <p>
 * The Player abstract class is the skeleton of
 * your own Player class.
 * Inherit from this class and feel free to modify every
 * function to meet your Player implementation.
 * <p>
 * It has basic functionalities, but it will act like a
 * non-played player.
 *
 * @author Axel Ambert
 * @version 1.0
 */

public abstract class Player extends Observable
{
  private final int MAX_WEIGHT = 5;
  protected final Inventory inventory;
  protected Room currentRoom;
  protected String playerName;


  /**
   * Create an Player instance.
   * Generate a random name for the player.
   * @param currentRoom the room where the Player starts its adventure
   */
  public Player(Room currentRoom)
  {
    this.inventory = new Inventory(this.MAX_WEIGHT);
    this.setCurrentRoom(currentRoom);
    this.generateRandomPlayerName();
  }

  /**
   * Does nothing.
   *
   * Feel free to override this function to add your implementation.
   * This function is called when the player has to do something.
   *
   * @return an empty string
   */
  public String play()
  {
    return ("");
  }

  /**
   * Does nothing.
   *
   * Feel free to override this function to add your implementation.
   * This function is called after every Player's action, so it can
   * read and understand what the game told him.
   * Very useful for an AI...
   */
  public void interpretGameAnswer()
  {
  }

  /**
   * Get the Player's current room.
   *
   * @return the Player's current room
   */
  public Room getCurrentRoom()
  {
    return currentRoom;
  }

  /**
   * Set the Player's current room.
   * Remove him from the players list of its actual room
   * and add him into the new room players list.
   *
   * @param currentRoom the room to set as the Player's current room
   */
  public void setCurrentRoom(Room currentRoom)
  {
    if (this.currentRoom != null) {
      this.currentRoom.removeAPlayer(this);
    }
    this.currentRoom = currentRoom;
    this.currentRoom.addAPlayer(this);
    this.update();
  }

  /**
   * Get the Player's inventory.
   *
   * @return the Player's inventory
   */
  public Inventory getInventory()
  {
    return inventory;
  }

  /**
   * Tell if the player's inventory is able to take a new object.
   *
   * @param item the item to test
   * @return the Player capacity to take the new item
   */
  public boolean canTake(Item item)
  {
    return (this.inventory.getInventoryWeight() + item.getItemWeight()
               >= this.inventory.getMaxWeight());
  }

  /**
   * Generate a random name for the Player.
   */
  private void generateRandomPlayerName()
  {
    StringBuilder authorizedChars = new StringBuilder();
    StringBuilder name = new StringBuilder();
    Random rand = new Random();

    for (int i = 65; i <= 90; i++) {
      authorizedChars.append((char) i);
    }
    for (int i = 0; i < 10; i++) {
      name.append(authorizedChars.charAt(rand.nextInt(authorizedChars.length() - 1)));
    }
    this.playerName = name.toString();
  }

  /**
   * Get the Player's name.
   *
   * @return the Player's name
   */
  public String getPlayerName()
  {
    return playerName;
  }

  /**
   * Set the Player's name.
   *
   * @param playerName the player new name
   */
  public void setPlayerName(String playerName)
  {
    this.playerName = playerName;
  }

  /**
   * Remove a player from its own room.
   * Use this function when the Player is quitting the game.
   *
   */
  public void exitFromRoom()
  {
    this.currentRoom.removeAPlayer(this);
  }
}
