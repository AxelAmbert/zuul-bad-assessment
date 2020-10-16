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
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  The exits are labelled north,
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Room
{
    public String description;
    public HashMap<String, Room> linkedRooms;
    private Inventory inventory;
    private LinkedList<Player> playersInRoom;

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

    public final void linkARoom(String direction, Room roomToLink)
    {
        this.linkedRooms.put(direction, roomToLink);
    }

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

    public String getFullDescription()
    {
        StringBuilder items = new StringBuilder();

        for (Item item : this.inventory.getItems()) {
            items.append(item.getItemName()).append("(").append(item.getItemWeight()).append(")");
        }
        return (LocalizedText.getText("room_long_desc", getDescription(), this.getExitsDescription(), items));
    }

    public Set<String> getExits()
    {
        return (this.linkedRooms.keySet());
    }

    public Inventory getInventory()
    {
        return (this.inventory);
    }

    public Player searchForPlayer(String name)
    {
        for (Player player : this.playersInRoom) {
            if (player.getPlayerName().equals(name))
                return (player);
        }
        return (null);
    }

    public void removeAPlayer(Player player)
    {
        this.playersInRoom.remove(player);
    }

    public void addAPlayer(Player player)
    {
        this.playersInRoom.add(player);
    }
    public String getExitsDescription()
    {
        StringBuilder exits = new StringBuilder();

        for (String exit : this.linkedRooms.keySet()) {
            exits.append(exit).append(" ");
        }
        return (exits.toString());
    }
}
