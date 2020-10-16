package functionalities;

import communication.Controller;
import main.Command;
import main.Game;
import misc.LocalizedText;

public class Quit extends Functionality
{
    @Override
    public void run(Command command)
    {
        Game game = Game.getGameInstance();

        if (command.getNumberOfArgs() < 1) {
            Controller.showMessage(LocalizedText.getText("quit_what"));
        } else {
            game.onPlayerQuit();
        }
    }
}
