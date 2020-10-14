package functionalities;
import communication.UserCommunication;
import main.Command;
import main.Room;

public class Go implements Functionality {
    public Go() {
        super();
        System.out.print("coucou " );
    }
    public void run(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            UserCommunication.getInstance().getCommunication().showMessage("There is no door!");
        } else {
            player.setCurrentRoom(nextRoom);
            System.out.println(player.getCurrentRoom().getFullDescription());
        }
    }
}
