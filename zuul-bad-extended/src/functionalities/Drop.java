package functionalities;

import communication.Controller;
import main.Command;
import main.Game;
import misc.LocalizedText;
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
            Controller.getInstance().showMessage(LocalizedText.getText("drop_arg_<1"));
            return;
        }
        item = command.getArgAt(1);
        if (actualPlayer.getInventory().hasItem(item) == false) {
            Controller.getInstance().showMessage(LocalizedText.getText("drop_no_item", item));
            return;
        }
        actualPlayer.getInventory().transferTo(actualPlayer.getCurrentRoom().getInventory(), item);
    }
}
