package functionalities;
import communication.Controller;
import main.Command;
import main.Game;
import main.Room;
import player.Player;

public class Go implements Functionality {
    public Go() {
        super();
    }

    public void run(Command command) {
        Game game = Game.getGameInstance();
        Player actualPlayer = game.getActualPlayer();

        if (command.getNumberOfArgs() < 1) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getArgAt(1);

        // Try to leave current room.
        Room nextRoom = actualPlayer.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            Controller.getInstance().getCommunication().showMessage("There is no door!");
        } else {
            actualPlayer.setCurrentRoom(nextRoom);
            System.out.println(actualPlayer.getCurrentRoom().getFullDescription());
        }
    }
}
