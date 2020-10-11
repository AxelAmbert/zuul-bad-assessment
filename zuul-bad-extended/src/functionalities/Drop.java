package functionalities;
import main.Game;
import main.Command;
import main.Room;

public class Drop implements Functionality {
    @Override
    public void run(Game game, Command command) {
      /*  if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }

        String item = command.getSecondWord();
        int i = items.indexOf(item);
        if (i == -1) {
            System.out.println("You don't have the " + item);
            return;
        }
        items.remove(i);
        int w = (Integer) weights.remove(i);
        currentRoom.addItem(item, w);
        totalWeight -= w;*/
    }
}
