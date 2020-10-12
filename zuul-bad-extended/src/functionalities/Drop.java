package functionalities;

import main.Command;
import misc.Item;
import player.Player;

public class Drop implements Functionality
{
    @Override
    public void run(Player player, Command command)
    {
        String item;

        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }
        item = command.getSecondWord();
        if (player.getInventory().hasItem(item) == false) {
            System.out.println("You don't have the " + item);
            return;
        }
        player.getInventory().transferTo(player.getCurrentRoom().getInventory(), item);
    }
}
