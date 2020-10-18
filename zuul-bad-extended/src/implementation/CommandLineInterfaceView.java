package implementation;

import communication.CommandLineInterface;
import communication.Controller;
import main.*;
import misc.LocalizedText;
import player.Player;

public class CommandLineInterfaceView implements GameView
{

  private CommandParser parser;
  private CommandInvoker invoker;
  private int nbOfPlayer;
  private CommandWords commandWords;

  public CommandLineInterfaceView()
  {
    this.parser = new CommandParser();
    this.invoker = new CommandInvoker();
    this.nbOfPlayer = 0;
    this.commandWords = new CommandWords(System.getProperty("user.dir") + "\\availableCommands");
  }

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
    if (this.commandWords.isCommand(userCommand.getCommandName())) {
      this.invoker.invoke(userCommand);
    } else {
      Controller.showMessage(LocalizedText.getText("unknown_command"));
    }
    if (game.getNumberOfPlayers() == this.nbOfPlayer) {
      game.onPlayerTurnEnd();
    }
  }
}
