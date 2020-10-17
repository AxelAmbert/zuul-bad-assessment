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
        final Game game = Game.getGameInstance();
        final Player actualPlayer = game.getActualPlayer();


        if (this.evaluateArgs(command, 1, "go_where") == false) {
            return;
        }

        final String direction = command.getArgAt(1);

        // Try to leave current room.
        final Room nextRoom = actualPlayer.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            Controller.showMessageAndLog(LocalizedText.getText("no_door"));
        } else {
            actualPlayer.setCurrentRoom(nextRoom);
            Controller.showMessageAndLog(actualPlayer.getCurrentRoom().getFullDescription());
        }
    }
}
