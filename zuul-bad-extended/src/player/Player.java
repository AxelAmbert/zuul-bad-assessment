package player;

import main.Room;
import main.Command;
import misc.Inventory;
import misc.Item;

import java.util.ArrayList;

public class Player
{
    private final int MAX_WEIGHT = 5;
    protected Inventory inventory;
    protected Room currentRoom;

    public Player(Room currentRoom)
    {
        this.inventory = new Inventory(this.MAX_WEIGHT);
        this.currentRoom = currentRoom;
    }

    public Command play()
    {
        return (new Command(null, null, null));
    }

    public void interpretGameAnswer()
    {

    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom)
    {
        this.currentRoom = currentRoom;
    }


    public Inventory getInventory()
    {
        return inventory;
    }
}
