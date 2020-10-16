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

    private Controller controller = Controller.getInstance();
    private CommandParser parser = new CommandParser();
    private CommandInvoker invoker = new CommandInvoker();
    private int nbOfPlayer = 0;

    public CommandLineInterfaceView() { }

    @Override
    public void runGame(Game game)
    {
        LocalizedText.setLocaleTexts(System.getProperty("user.dir") + "\\texts.json", "en");
        System.out.println(LocalizedText.getText("drop_arg_<1", "mdr", "lol", "hihi"));
        Controller.getInstance().setCommunication(new CommandLineInterface());
        game.printWelcome();
        do {
            this.playerTurn(game);
            System.out.println("ALORS ? " + game.getNumberOfPlayers());
        } while (game.getNumberOfPlayers() > 0);
    }

    public void playerTurn(Game game)
    {
        Player actualPlayer = game.getActualPlayer();
        System.out.println("A toi de jouer " + actualPlayer.getPlayerName());
        Command userCommand = parser.parse(actualPlayer.play(), " ");

        this.nbOfPlayer = game.getNumberOfPlayers();
        this.invoker.invoke(userCommand);
        if (game.getNumberOfPlayers() == this.nbOfPlayer) {
            game.onPlayerTurnEnd();
        }
    }
}
