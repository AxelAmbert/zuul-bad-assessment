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

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     *
     * @param newRelatedRooms The new room name.
     */
    @SafeVarargs
    public final void setRelatedRoom(HashMap.SimpleEntry<String, Room>... newRelatedRooms)
    {
        int i = 0;

        for (var pair : newRelatedRooms) {
            try {
                this.linkedRooms.put(pair.getKey(), pair.getValue());
            } catch (NullPointerException exception) {
                System.out.println("Room number " + i + " went wrong.");
            } finally {
                i++;
            }
        }
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

    /**
     * Add an item to the Room
     *
     * @param description The description of the item
     * @param weight      The item's weight
     */
    public void addItem(String description, int weight)
    {
        itemDescription = description;
        itemWeight = weight;
    }

    public void addItem(Item item)
    {

    }

    /**
     * Does the room contain an item
     *
     * @param description the item
     * @ return the item's weight or 0 if none
     */
    public int containsItem(String description)
    {
        if (itemDescription.equals(description))
            return itemWeight;
        else return 0;
    }

    /**
     * Remove an item from the Room
     */
    public String removeItem(String description)
    {
        if (itemDescription.equals(description)) {
            String tmp = itemDescription;
            itemDescription = null;
            return tmp;
        } else {
            System.out.println("This room does not contain" + description);
            return null;
        }
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

    public boolean hasItem(String itemName)
    {
        return (this.inventory.hasItem(itemName));
    }

    public Inventory getInventory()
    {
        return (this.inventory);
    }
}
