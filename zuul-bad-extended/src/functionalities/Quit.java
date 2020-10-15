package functionalities;

import main.Command;
import main.Game;
import player.Player;

public class Quit implements Functionality
{
    @Override
    public void run(Command command)
    {
        Game game = Game.getGameInstance();
        Player player = game.getActualPlayer();

        if (command.getNumberOfArgs() < 1) {
            System.out.println("Quit what?");
        } else {
            game.onPlayerQuit();
        }
    }
}
