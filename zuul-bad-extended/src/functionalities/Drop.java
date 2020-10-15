package functionalities;

import main.Command;
import main.Game;
import player.Player;

public class Drop implements Functionality
{
    @Override
    public void run(Command command)
    {
        String item;
        Game game = Game.getGameInstance();
        Player actualPlayer = game.getActualPlayer();

        if (command.getNumberOfArgs() < 1) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }
        item = command.getArgAt(1);
        if (actualPlayer.getInventory().hasItem(item) == false) {
            System.out.println("You don't have the " + item);
            return;
        }
        actualPlayer.getInventory().transferTo(actualPlayer.getCurrentRoom().getInventory(), item);
    }
}
