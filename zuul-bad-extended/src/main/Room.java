package main;

import misc.Inventory;
import misc.Item;

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
    // An item in the room

    public Room northExit;
    public Room southExit;
    public Room eastExit;
    public Room westExit;
    public String itemDescription;
    public int itemWeight;

    // Characters in the room
    public String character;

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
        StringBuilder longDescription = new StringBuilder("You are " + getDescription() + System.lineSeparator());

        longDescription.append("Exits: ");
        for (String exit : this.linkedRooms.keySet()) {
            longDescription.append(exit).append(" ");
        }
        for (Item item : this.inventory.getItems()) {
            longDescription.append(item.getItemName()).append("(").append(item.getItemWeight()).append(")");
        }
        longDescription.append(System.lineSeparator());
        return (longDescription.toString());
    }

    public Set<String> getExits()
    {
        return (this.linkedRooms.keySet());
    }

    public Inventory getInventory()
    {
        return (this.inventory);
    }
}
