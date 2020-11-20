package main;

import java.lang.reflect.Constructor;
import java.util.*;

import RoomParser.RoomParser;
import RoomParser.RoomParserJSON;
import RoomParser.RoomParserTXT;
import communication.Controller;
import functionalities.Functionality;
import misc.LocalizedText;
import misc.Observer;
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

  private enum GameEvent {RoomChanged, PlayerChanged}

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

  public void reset(String worldPath)
  {
    int playerNb = this.playerList.size();

    this.playerList = new ArrayList<>();
    this.onPlayerChangeList = new ArrayList<>();
    this.onRoomChangeList = new ArrayList<>();
    this.createRooms(worldPath);
    this.addPlayers(playerNb);
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
  public void createRooms(String path)
  {
    Class<?> cls = null;
    RoomParser roomParser;

    try {
      cls = Class.forName("RoomParser.RoomParser" + this.getFileExtension(path).toUpperCase());
      Constructor ct = cls.getConstructor();
      roomParser = (RoomParser) ct.newInstance();
    } catch (Exception e) {
      roomParser = new RoomParserTXT();
      e.printStackTrace();
    }
    this.startRoom = roomParser.update(path);
  }

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
    if (this.getNumberOfPlayers() == 0)
      return;
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
