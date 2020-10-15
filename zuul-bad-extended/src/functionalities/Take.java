package functionalities;
import main.Command;
import main.Game;
import main.Room;
import misc.Item;
import player.Player;

public class Take implements Functionality {

    @Override
    public void run(Command command) {
        Game game = Game.getGameInstance();
        Player actualPlayer = game.getActualPlayer();
        Room currentRoom = actualPlayer.getCurrentRoom();

        if (command.getNumberOfArgs() < 1) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }

        String itemDescription = command.getArgAt(1);
        if (currentRoom.getInventory().hasItem(itemDescription) == false) {
            // The item is not in the room
            System.out.println("No " + itemDescription + " in the room");
            return;
        }
        Item item = currentRoom.getInventory().takeItem(itemDescription);
        if (actualPlayer.getInventory().getInventoryWeight() + item.getItemWeight() >= actualPlayer.getInventory().getMaxWeight()) {
            // The player is carrying too much
            currentRoom.getInventory().insertItem(item);
            System.out.println(item + " is too heavy");
            return;
        }
        // OK we can pick it up
        actualPlayer.getInventory().insertItem(item);
    }
}
