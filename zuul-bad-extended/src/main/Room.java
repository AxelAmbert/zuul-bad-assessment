package main;

import misc.Inventory;
import misc.Item;
import misc.LocalizedText;
import player.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 * <p>
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  The room can have multiple exits.
 * For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * <p>
 * Everytime a player enter or leave the room, the Room keep a track on the
 * list of players.
 *
 * @author Axel Ambert
 * @version 1.0
 */
public class Room
{
  public final String description;
  private final HashMap<String, Room> linkedRooms;
  private final Inventory inventory;
  private final LinkedList<Player> playersInRoom;

  /**
   * Create a room described "description". Initially, it has
   * no exits. "description" is something like "a kitchen" or
   * "an open court yard".
   *
   * @param description The room's description.
   */
  public Room(String description)
  {
    this.linkedRooms = new HashMap<String, Room>();
    this.description = description;
    this.inventory = new Inventory(-1);
    this.playersInRoom = new LinkedList<>();
  }

  /**
   * Link the actual room with another Room with its direction.
   * There can only be one room attached to a direction.
   *
   * @param direction The linked room direction
   * @param roomToLink The room to link
   */
  public final void linkARoom(String direction, Room roomToLink)
  {
    this.linkedRooms.put(direction, roomToLink);
  }

  /**
   * Get a specific exit on the current room.
   * Returns null if no room is linked to a direction
   *
   * @param direction The direction to get
   */
  public final Room getExit(String direction)
  {
    return (this.linkedRooms.get(direction));
  }

  /**
   * @return The description of the room.
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * Get a full description of the room which contains:
   * The list of directions;
   * The list of items.
   *
   * @return The full description of the room.
   */
  public String getFullDescription()
  {
    StringBuilder items = new StringBuilder();

    for (Item item : this.inventory.getItems()) {
      items.append(item.getItemName()).append("(").append(item.getItemWeight()).append(")");
    }
    return (LocalizedText.getText(
            "room_long_desc", getDescription(), this.getExitsDescription(), items));
  }

  /**
   * Get the room's inventory
   *
   * @return The room's inventory
   */
  public Inventory getInventory()
  {
    return (this.inventory);
  }

  /**
   * Search for a specific player in the room via its name.
   * If found, return the player, otherwise, return null.
   *
   * @param name The player's name
   * @return The found player or null
   */
  public Player searchForPlayer(String name)
  {
    for (Player player : this.playersInRoom) {
      if (player.getPlayerName().equals(name))
        return (player);
    }
    return (null);
  }

  /**
   * Remove a specific player from the player list in the room.
   *
   * @param player The player to remove
   */
  public void removeAPlayer(Player player)
  {
    this.playersInRoom.remove(player);
  }

  /**
   * Add a specific player from the player list in the room.
   *
   * @param player The player to add
   */
  public void addAPlayer(Player player)
  {
    this.playersInRoom.add(player);
  }

  /**
   * Get every exits linked to the current room.
   *
   * @return The list of exits, separated by a space
   */
  public String getExitsDescription()
  {
    StringBuilder exits = new StringBuilder();

    for (String exit : this.linkedRooms.keySet()) {
      exits.append(exit).append(" ");
    }
    return (exits.toString());
  }
}
