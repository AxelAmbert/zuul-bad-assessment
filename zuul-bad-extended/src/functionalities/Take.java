package functionalities;
import main.Command;
import main.Room;
import misc.Item;

public class Take implements Functionality {

    @Override
    public void run(Command command) {
        Room currentRoom = player.getCurrentRoom();

        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }

        String itemDescription = command.getSecondWord();
        if (currentRoom.getInventory().hasItem(itemDescription) == false) {
            // The item is not in the room
            System.out.println("No " + itemDescription + " in the room");
            return;
        }
        Item item = currentRoom.getInventory().takeItem(itemDescription);
        if (player.getInventory().getInventoryWeight() + item.getItemWeight() >= player.getInventory().getMaxWeight()) {
            // The player is carrying too much
            currentRoom.getInventory().insertItem(item);
            System.out.println(item + " is too heavy");
            return;
        }
        // OK we can pick it up
        player.getInventory().insertItem(item);
    }
}
