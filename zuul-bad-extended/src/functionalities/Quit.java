package functionalities;

import communication.Controller;
import main.Command;
import main.Game;
import misc.LocalizedText;

public class Quit implements Functionality
{
    @Override
    public void run(Command command)
    {
        Game game = Game.getGameInstance();

        if (command.getNumberOfArgs() < 1) {
            Controller.getInstance().showMessage(LocalizedText.getText("quit_what"));
        } else {
            game.onPlayerQuit();
        }
    }
}
