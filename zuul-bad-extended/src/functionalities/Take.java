package functionalities;
import communication.Controller;
import main.Command;
import main.Game;
import main.Room;
import misc.Item;
import misc.LocalizedText;
import player.Player;

public class Take implements Functionality {

    @Override
    public void run(Command command) {
        Game game = Game.getGameInstance();
        Player actualPlayer = game.getActualPlayer();
        Room currentRoom = actualPlayer.getCurrentRoom();

        if (command.getNumberOfArgs() < 1) {
            // if there is no second word, we don't know what to take...
            Controller.getInstance().showMessage(LocalizedText.getText("take_what"));
            return;
        }

        String itemDescription = command.getArgAt(1);
        if (currentRoom.getInventory().hasItem(itemDescription) == false) {
            // The item is not in the room
            Controller.getInstance().showMessage(LocalizedText.getText("no_item_in_room", itemDescription));
            return;
        }
        Item item = currentRoom.getInventory().takeItem(itemDescription);
        if (actualPlayer.getInventory().getInventoryWeight() + item.getItemWeight() >= actualPlayer.getInventory().getMaxWeight()) {
            // The player is carrying too much
            currentRoom.getInventory().insertItem(item);
            Controller.getInstance().showMessage(LocalizedText.getText("too_heavy", itemDescription));
            return;
        }
        // OK we can pick it up
        actualPlayer.getInventory().insertItem(item);
    }
}
