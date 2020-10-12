package functionalities;
import main.Command;
import main.Room;
import player.Player;

public class Give implements Functionality {

    @Override
    public void run(Player player, Command command) {
        Room currentRoom = player.getCurrentRoom();

        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to give...
            System.out.println("Give what?");
            return;
        }
        if (!command.hasThirdWord()) {
            // if there is no third word, we don't to whom to give it...
            System.out.println("Give it to who?");
            return;
        }

        String item = command.getSecondWord();
        String whom = command.getThirdWord();

        if (!currentRoom.character.equals(whom)) {
            // cannot give it if the chacter is not here
            System.out.println(whom + " is not in the room");
            return;
        }
        //TODO change this
       /* int i = items.indexOf(item);
        if (i == -1) {
            System.out.println("You don't have the " + item);
            return;
        }
        items.remove(i);
        int w = (Integer) weights.remove(i);
        totalWeight -= w;*/
    }
}
