package functionalities;

import communication.Controller;
import main.Command;
import main.Game;
import misc.LocalizedText;
import player.Player;

public class Drop extends Functionality
{
    @Override
    public void run(Command command)
    {
        String item;
        final Game game = Game.getGameInstance();
        final Player actualPlayer = game.getActualPlayer();

        if (this.evaluateArgs(command, 1, "drop_arg_<1") == false) {
            return;
        }
        item = command.getArgAt(1);
        if (actualPlayer.getInventory().hasItem(item) == false) {
            Controller.showMessageAndLog(LocalizedText.getText("drop_no_item", item));
            return;
        }
        actualPlayer.getInventory().transferTo(actualPlayer.getCurrentRoom().getInventory(), item);
    }
}
