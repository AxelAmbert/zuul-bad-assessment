package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import communication.Controller;
import misc.LocalizedText;
import misc.Observable;
import player.HumanPlayer;
import player.Player;


/**
 * This class is the main class of the "World of Zuul" application. "World of
 * Zuul" is a very simple, text based adventure game. Users can walk around some
 * scenery. That's all. It should really be extended to make it more
 * interesting!
 * <p>
 * To play this game, create an instance of this class and call the "play"
 * method.
 * <p>
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Game extends Observable
{

  private Player actualPlayer;
  private Room startRoom;
  private ArrayList<Player> playerList;
  private ArrayList<String> availableCommands;
  private ListIterator<Player> actualPlayerIterator;
  static Game gameInstance;

  /**
   * Create the game and initialise its internal map.
   */
  private Game()
  {
    this.getAvailableCommands();
    this.createRooms();
    this.addPlayers();
    this.actualPlayerIterator = this.playerList.listIterator();
    this.actualPlayer = this.actualPlayerIterator.next();
  }

  private void addPlayers()
  {
    this.playerList = new ArrayList<>();
    this.playerList.add(new HumanPlayer(this.startRoom, "1"));
  }

  private void getAvailableCommands()
  {
    this.availableCommands = new ArrayList<>();
    try {
      //TODO change this
      Scanner scanner = new Scanner(new File("C:\\Users\\ImPar\\OneDrive\\Documents\\Kent\\Java\\assesement1\\zuul-bad-assessment\\zuul-bad-extended\\availableCommands"));

      while (scanner.hasNextLine()) {
        this.availableCommands.add(scanner.nextLine());
      }
      scanner.close();
    } catch (FileNotFoundException ex) {
      System.err.println("Something went wrong while reading the command file");
    }
  }


  /**
   * Create all the rooms and link their exits together.
   */
  private void createRooms()
  {
    RoomParser roomParser = new RoomParser();

    this.startRoom = roomParser.update(System.getProperty("user.dir") + "\\rooms.json");
  }


  /**
   * Print out the opening message for the player.
   */
  public void printWelcome()
  {
    StringBuilder welcomeString = new StringBuilder();

    welcomeString.append(System.lineSeparator());
    welcomeString.append(LocalizedText.getText("welcome_zuul"));
    welcomeString.append(this.actualPlayer.getCurrentRoom().getFullDescription());
    Controller.showMessage(welcomeString);
  }


  public static Game getGameInstance()
  {
    if (gameInstance == null)
      gameInstance = new Game();
    return (gameInstance);
  }

  public void onPlayerQuit()
  {
    this.actualPlayer.exitFromRoom();
    this.actualPlayerIterator.remove();
    this.onPlayerTurnEnd();
  }

  public void onPlayerTurnEnd()
  {
    if (this.getNumberOfPlayers() == 0)
      return;
    if (this.actualPlayerIterator.hasNext() == false) {
      this.actualPlayerIterator = this.playerList.listIterator();
    }
    this.actualPlayer = this.actualPlayerIterator.next();
    this.update();
  }

  public int getNumberOfPlayers()
  {
    return (this.playerList.size());
  }

  public Player getActualPlayer()
  {
    return (this.actualPlayer);
  }
}
