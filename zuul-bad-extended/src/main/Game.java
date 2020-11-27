package main;

import java.lang.reflect.Constructor;
import java.util.*;

import RoomParser.RoomParser;
import RoomParser.RoomParserTXT;
import communication.Communication;
import communication.Controller;
import misc.LocalizedText;
import misc.Observer;
import misc.CreationOptions;
import player.HumanPlayer;
import player.NPC;
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
 * @author Axel Ambert
 * @version 1.0
 */
public class Game
{
  private Player actualPlayer;
  private Room startRoom;
  private ArrayList<Player> playerList;
  private ListIterator<Player> actualPlayerIterator;
  private ArrayList<Observer> onRoomChangeList;
  private ArrayList<Observer> onPlayerChangeList;
  static Game gameInstance;
  private final int DEFAULT_NB_PLAYERS = 3;

  public enum GameEvent {RoomChanged, PlayerChanged}

  /**
   * Create the game and initialise its internal map.
   */
  private Game()
  {
    this.playerList = new ArrayList<>();
    this.onPlayerChangeList = new ArrayList<>();
    this.onRoomChangeList = new ArrayList<>();
  }

  /**
   * Add new human players to the game.
   * Every player will get an ordered name like : {1, 2, 3, ..., nb}
   *
   * @param nb number of human players to add.
   */
  public void addPlayers(int nb)
  {
    for (int i = 0; i < nb; i++) {
      this.playerList.add(new HumanPlayer(this.startRoom, Integer.toString(i + 1)));
    }
    this.actualPlayerIterator = this.playerList.listIterator();
    this.actualPlayer = this.actualPlayerIterator.next();
  }

  public void reset(CreationOptions options)
  {
    if (this.createRooms(options) == false) {
      return;
    }
    this.playerList = new ArrayList<>();
    this.addPlayers(this.DEFAULT_NB_PLAYERS);
  }

  /**
   * Add new NPC to the game.
   *
   * @param nb number of NPC to add.
   */
  public void addNPC(int nb)
  {
    for (int i = 0; i < nb; i++) {
      this.playerList.add(new NPC(this.startRoom));
    }
    this.actualPlayerIterator = this.playerList.listIterator();
    this.actualPlayer = this.actualPlayerIterator.next();
  }

  /**
   * Use the RoomParser to create every room in the game.
   */
  public boolean createRooms(CreationOptions options)
  {
    Class<?> cls = null;
    RoomParser roomParser;
    String path = options.getFilePath();
    Room tmpRoom = null;

    try {
      cls = Class.forName("RoomParser.RoomParser" + this.getFileExtension(path).toUpperCase());
      Constructor ct = cls.getConstructor();
      roomParser = (RoomParser) ct.newInstance();
    } catch (Exception e) {
      roomParser = new RoomParserTXT();
    }
    tmpRoom = roomParser.update(path, options);
    return (this.handleStartRoomErrors(tmpRoom));
  }

  /**
   * Handle the error that can happen in the room creation process
   * @param tmpRoom room that was created
   * @return if the room creation worked well
   */
  private boolean handleStartRoomErrors(Room tmpRoom)
  {
    if (tmpRoom == null && this.startRoom == null) {
      Controller.showError("Error while loading configuration file. Quitting");
      System.exit(1);
    } else if (tmpRoom == null){
      Controller.showError("Error while loading configuration file. Game won't change.");
    } else {
      this.startRoom = tmpRoom;
      return (true);
    }
    return (false);
  }

  /**
   * Get the file extension of a file.
   * @param fileName the file name
   * @return the file extension (.json, .txt, etc...)
   */
  private String getFileExtension(String fileName)
  {
    if (fileName.contains(".") == true) {
      String[] array = fileName.split("\\.");

      return (array[array.length - 1]);
    }
    return ("TXT");
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

  /**
   * Since game is a Singleton, get the unique instance of Game.
   *
   * @return the only instance of Game
   */
  public static Game getGameInstance()
  {
    if (gameInstance == null)
      gameInstance = new Game();
    return (gameInstance);
  }

  /**
   * This function is called every time a player quit the game.
   * Feel free to add your own observers.
   */
  public void onPlayerQuit()
  {
    this.actualPlayer.exitFromRoom();
    this.actualPlayerIterator.remove();
    this.onPlayerTurnEnd();
  }

  /**
   * This function is called every time a player end its turn.
   * Since quitting the game end its turn, this function is also called
   * when the player quit.
   * Feel free to add your own observers.
   */
  public void onPlayerTurnEnd()
  {
    if (this.getNumberOfPlayers() == 0) {
      System.exit(1);
    }
    if (this.actualPlayerIterator.hasNext() == false) {
      this.actualPlayerIterator = this.playerList.listIterator();
    }
    this.actualPlayer = this.actualPlayerIterator.next();
    this.onPlayerChangeList.stream().forEach(observer -> observer.onUpdate(this.actualPlayer));
  }

  /**
   * Get the current number of players in the game.
   *
   * @return the current number of players.
   */
  public int getNumberOfPlayers()
  {
    return (this.playerList.size());
  }

  /**
   * Get the player who is currently playing.
   *
   * @return the actual player.
   */
  public Player getActualPlayer()
  {
    return (this.actualPlayer);
  }

  public Room getStartRoom()
  {
    return (startRoom);
  }

  /**
   * Called when the current room is changed
   */
  public void onRoomChanged()
  {
    this.onRoomChangeList.stream().forEach(observer -> observer.onUpdate(this.actualPlayer.getCurrentRoom()));
  }

  public void addObserver(Observer observerToAdd, GameEvent event)
  {
    if (event == GameEvent.PlayerChanged) {
      this.onPlayerChangeList.add(observerToAdd);
    } else {
      this.onRoomChangeList.add(observerToAdd);
    }
  }

  public void removeObserver(Observer observerToRemove)
  {
    this.onRoomChangeList.remove(observerToRemove);
    this.onPlayerChangeList.remove(observerToRemove);
  }

}
