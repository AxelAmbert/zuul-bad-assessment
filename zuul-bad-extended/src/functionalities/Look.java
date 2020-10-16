package functionalities;
import main.Command;
import main.Game;
import main.Room;
import player.Player;

public class Look extends Functionality {
    @Override
    public void run(Command command) {
        Game game = Game.getGameInstance();
        Player actualPlayer = game.getActualPlayer();
        Room currentRoom = actualPlayer.getCurrentRoom();

        System.out.println(currentRoom.getFullDescription());
    }
}
