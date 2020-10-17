package player;

import main.Room;
import main.Command;
import misc.Inventory;
import misc.Item;

import java.util.ArrayList;
import java.util.Random;

public abstract class Player
{
    private final int MAX_WEIGHT = 5;
    protected final Inventory inventory;
    protected Room currentRoom;
    protected String playerName;

    public Player(Room currentRoom)
    {
        this.inventory = new Inventory(this.MAX_WEIGHT);
        this.setCurrentRoom(currentRoom);
        this.generateRandomPlayerName();
    }

    public String play()
    {
        return ("");
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
        if (this.currentRoom != null)
            this.currentRoom.removeAPlayer(this);
        this.currentRoom = currentRoom;
        this.currentRoom.addAPlayer(this);
    }


    public Inventory getInventory()
    {
        return inventory;
    }

    public boolean canTake(Item item)
    {
        return (this.inventory.getInventoryWeight() +  item.getItemWeight() >= this.inventory.getMaxWeight());
    }

    private void generateRandomPlayerName()
    {
        StringBuilder authorizedChars = new StringBuilder();
        StringBuilder name = new StringBuilder();
        Random rand = new Random();

        for (int i = 65; i <= 90; i++) {
            authorizedChars.append((char)i);
        }
        for (int i = 0; i < 10; i++){
            name.append(authorizedChars.charAt(rand.nextInt(authorizedChars.length() - 1)));
        }
        this.playerName = name.toString();
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public void exitFromRoom()
    {
        this.currentRoom.removeAPlayer(this);
    }
}
