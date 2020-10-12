package functionalities;
import main.Command;
import main.Room;
import player.Player;

public class Look implements Functionality {
    @Override
    public void run(Player player, Command command) {
        Room currentRoom = player.getCurrentRoom();

        System.out.println(currentRoom.getFullDescription());
    }
}
