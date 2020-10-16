package functionalities;
import communication.Controller;
import main.Command;
import main.Game;
import main.Room;
import misc.LocalizedText;
import player.Player;

public class Give implements Functionality {

    @Override
    public void run(Command command) {
        Game game = Game.getGameInstance();
        Player actualPlayer = game.getActualPlayer();
        Room currentRoom = actualPlayer.getCurrentRoom();

        if (command.getNumberOfArgs() < 1) {
            // if there is no second word, we don't know what to give...
            Controller.getInstance().showMessage(LocalizedText.getText("give_what"));
            return;
        }
        if (command.getNumberOfArgs() < 2) {
            // if there is no third word, we don't to whom to give it...
            Controller.getInstance().showMessage(LocalizedText.getText("give_who"));
            return;
        }

        String item = command.getArgAt(1);
        String whom = command.getArgAt(2);
        Player playerToGive = currentRoom.searchForPlayer(whom);

        if (playerToGive == null) {
            // cannot give it if the chacter is not here
            Controller.getInstance().showMessage(LocalizedText.getText("not_in_room", whom));
            return;
        }
        else if (actualPlayer.getInventory().hasItem(item) == false) {
            Controller.getInstance().showMessage(LocalizedText.getText("give_no_item", item));
            return;
        }
        actualPlayer.getInventory().transferTo(playerToGive.getInventory(), item);
    }
}
