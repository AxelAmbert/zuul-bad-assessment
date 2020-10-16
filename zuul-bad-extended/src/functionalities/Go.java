package functionalities;
import communication.Controller;
import main.Command;
import main.Game;
import main.Room;
import misc.LocalizedText;
import player.Player;

public class Go extends Functionality {
    public Go() {
        super();
    }

    public void run(Command command) {
        Game game = Game.getGameInstance();
        Player actualPlayer = game.getActualPlayer();


        if (this.evaluateArgs(command, 1, "go_where") == false) {
            return;
        }

        String direction = command.getArgAt(1);

        // Try to leave current room.
        Room nextRoom = actualPlayer.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            Controller.getCommunication().showMessage(LocalizedText.getText("no_door"));
        } else {
            actualPlayer.setCurrentRoom(nextRoom);
            Controller.getCommunication().showMessage(actualPlayer.getCurrentRoom().getFullDescription());
        }
    }
}
