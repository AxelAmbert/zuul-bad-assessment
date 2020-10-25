package implementation;

import communication.CommandLineInterface;
import communication.Controller;
import main.*;
import misc.LocalizedText;
import player.Player;

/**
 * This class is the implementation of the GameView class, it
 * use a command line interface view for the project.
 * Use it if you want to play the Zuul Game with CLI.
 * @author Axel Ambert
 * @version 1.0
 */

public class CommandLineInterfaceView implements GameView
{

  private CommandParser parser;
  private CommandInvoker invoker;
  private int nbOfPlayer;
  private CommandWords commandWords;

  /**
   * Constructor of the CommandLineInterfaceView class
   */
  public CommandLineInterfaceView()
  {
    this.parser = new CommandParser();
    this.invoker = new CommandInvoker();
    this.nbOfPlayer = 0;
    this.commandWords = new CommandWords(System.getProperty("user.dir") + "\\availableCommands");
  }

  /**
   * Main while of the program.
   * Every action of the game happens here.
   * @param game The game instance that we want to use.
   */
  @Override
  public void runGame(Game game)
  {
    LocalizedText.setLocaleTexts(System.getProperty("user.dir") + "\\texts.json", "en");
    Controller.setCommunication(new CommandLineInterface());
    game.addPlayers(1);
    game.printWelcome();
    do {
      Controller.showMessage("> ");
      this.playerTurn(game);
    } while (game.getNumberOfPlayers() > 0);
  }

  /**
   * Handle the actual player turn.
   * It will ask in the prompt for a command and then, if valid,
   * execute the command.
   * If at the end the number of player is the same (nobody quitted)
   * inform the game about the end of turn.
   * @param game The game instance that we want to use.
   */
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
      actualPlayer.interpretGameAnswer();
      game.onPlayerTurnEnd();
    }
  }
}
