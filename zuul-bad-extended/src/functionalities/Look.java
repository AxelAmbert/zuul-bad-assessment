package functionalities;
import main.Command;
import main.Room;

public class Look implements Functionality {
    @Override
    public void run(Command command) {
        Room currentRoom = player.getCurrentRoom();

        System.out.println(currentRoom.getFullDescription());
    }
}
