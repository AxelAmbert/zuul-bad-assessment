package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
public class Game
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
        this.playerList.add(new HumanPlayer(this.startRoom, "2"));
        this.playerList.add(new HumanPlayer(this.startRoom, "3"));
        this.playerList.add(new HumanPlayer(this.startRoom, "4"));
        this.playerList.add(new HumanPlayer(this.startRoom, "5"));

    }

    private void getAvailableCommands()
    {
        this.availableCommands = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\ImPar\\OneDrive\\Documents\\Kent\\Java\\assesement1\\zuul-bad-assessment\\zuul-bad-extended\\availableCommands"));

            while (scanner.hasNextLine()) {
                this.availableCommands.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Something went wrong while reading the command file");
        }
        System.out.println(availableCommands);
    }


    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        RoomParser roomParser = new RoomParser();

        this.startRoom = roomParser.update(System.getProperty("user.dir") +  "\\rooms.json");
    }


    /**
     * Print out the opening message for the player.
     */
    public void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(this.actualPlayer.getCurrentRoom().getFullDescription());
    }


// implementations of user commands:

    /**
     * Print out some help information. Here we print some stupid, cryptic
     * message and a list of the command words.
     */
    private void printHelp()
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    public void printOK()
    {
        for (Player ok : this.playerList) {
            System.out.println(ok.getCurrentRoom().getFullDescription());
        }
    }

    public static Game getGameInstance()
    {
        if (gameInstance == null)
            gameInstance = new Game();
        return (gameInstance);
    }

    public ArrayList<Player> getPlayerList()
    {
        return playerList;
    }

    public void onPlayerQuit()
    {
        System.out.println("md");
            this.actualPlayer.exitFromRoom();
            this.actualPlayerIterator.remove();
            /*if (this.actualPlayerIterator.hasPrevious()) {
                this.actualPlayerIterator.previous();
                this.actualPlayerIterator.remove();
            } else {
                this.actualPlayerIterator = this.playerList.listIterator(this.playerList.size() - 1);
                this.actualPlayerIterator.remove();
                if (this.playerList.isEmpty() == false) {
                    this.actualPlayerIterator = this.playerList.listIterator(0);
                }
            }*/
            this.onPlayerTurnEnd();
    }

    public void onPlayerTurnEnd()
    {
        if (this.actualPlayerIterator.hasNext() == false) {
            this.actualPlayerIterator = this.playerList.listIterator();
        }
        this.actualPlayer = this.actualPlayerIterator.next();
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
