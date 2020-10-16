package implementation;

import communication.CommandLineInterface;
import communication.Controller;
import main.Command;
import main.CommandInvoker;
import main.CommandParser;
import main.Game;
import misc.LocalizedText;
import player.Player;

public class CommandLineInterfaceView implements GameView
{

    private CommandParser parser = new CommandParser();
    private CommandInvoker invoker = new CommandInvoker();
    private int nbOfPlayer = 0;

    public CommandLineInterfaceView() { }

    @Override
    public void runGame(Game game)
    {
        LocalizedText.setLocaleTexts(System.getProperty("user.dir") + "\\texts.json", "en");
        Controller.setCommunication(new CommandLineInterface());
        game.printWelcome();
        do {
            Controller.showMessage("> ");
            this.playerTurn(game);
        } while (game.getNumberOfPlayers() > 0);
    }

    public void playerTurn(Game game)
    {
        Player actualPlayer = game.getActualPlayer();
        Command userCommand = parser.parse(actualPlayer.play(), " ");

        this.nbOfPlayer = game.getNumberOfPlayers();
        this.invoker.invoke(userCommand);
        if (game.getNumberOfPlayers() == this.nbOfPlayer) {
            game.onPlayerTurnEnd();
        }
    }
}
