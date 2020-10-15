package functionalities;
import main.Command;
import main.Game;
import main.Room;
import player.Player;

public class Give implements Functionality {

    @Override
    public void run(Command command) {
        Game game = Game.getGameInstance();
        Player actualPlayer = game.getActualPlayer();
        Room currentRoom = actualPlayer.getCurrentRoom();

        if (command.getNumberOfArgs() < 1) {
            // if there is no second word, we don't know what to give...
            System.out.println("Give what?");
            return;
        }
        if (command.getNumberOfArgs() < 2) {
            // if there is no third word, we don't to whom to give it...
            System.out.println("Give it to who?");
            return;
        }

        String item = command.getArgAt(1);
        String whom = command.getArgAt(2);
        Player playerToGive = currentRoom.searchForPlayer(whom);

        if (playerToGive == null) {
            // cannot give it if the chacter is not here
            System.out.println(whom + " is not in the room");
            return;
        }
        else if (actualPlayer.getInventory().hasItem(item) == false) {
            System.out.println("You don't have the " + item);
            return;
        }
        actualPlayer.getInventory().transferTo(playerToGive.getInventory(), item);
    }
}
