package functionalities;
import communication.Controller;
import main.Command;
import main.Game;
import main.Room;
import player.Player;

public class Look extends Functionality {
    @Override
    public void run(Command command) {
        final Game game = Game.getGameInstance();
        final Player actualPlayer = game.getActualPlayer();
        final Room currentRoom = actualPlayer.getCurrentRoom();

        Controller.showMessageAndLog(currentRoom.getFullDescription());
    }
}
