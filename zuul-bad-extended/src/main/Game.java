package main;

import functionalities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
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

    private Parser parser;
    private Room startRoom;
    private ArrayList<Player> playerList;
    private ArrayList<String> availableCommands;

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        this.getAvailableCommands();
        this.createRooms();
        this.addPlayers();
        parser = new Parser();
    }

    private void addPlayers()
    {
        this.playerList = new ArrayList<>();
        this.playerList.add(new HumanPlayer(this.startRoom));
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

    public void interpretFunctionality(Player player, Command command)
    {
        try {
            Class<?> cls = Class.forName("functionalities." + command.getCommandWord());
            Constructor ct = cls.getConstructor();
            Functionality fun = (Functionality) ct.newInstance();

            fun.run(player, command);
        } catch (Throwable e) {
            System.err.println(command.getCommandWord() + " is not a valid command " + e.toString());
        }
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        RoomParser roomParser = new RoomParser();

        this.startRoom = roomParser.update("C:\\Users\\ImPar\\OneDrive\\Documents\\Kent\\Java\\assesement1\\zuul-bad-assessment\\zuul-bad-extended\\rooms.json");
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        //TODO: put the right condition
        //while (!finished) {
        while (true) {
            this.playerList.forEach(this::handlePlayerTurn);
        }
        // System.out.println("Thank you for playing.  Good bye.");
    }

    public void handlePlayerTurn(Player player)
    {

        Command command = player.play();

        this.processCommand(player, command);
        player.interpretGameAnswer();
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You are " + startRoom.getDescription());
        System.out.print("Exits: ");
        this.showARoomExits(this.startRoom);
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Player player, Command command)
    {
        boolean wantToQuit = false;

        System.out.println(command.getCommandWord());
        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        this.interpretFunctionality(player, command);
        /*
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("look")) {
            look();
        } else if (commandWord.equals("take")) {
            take(command);
        } else if (commandWord.equals("drop")) {
            drop(command);
        } else if (commandWord.equals("give")) {
            give(command);
        }*/
        return wantToQuit;
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

    /**
     * Try to go to one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     */
    private void goRoom(Command command)
    {

    }

    /**
     * "Look" was entered. Report what the player can see in the room
     */
    private void look()
    {

    }

    /**
     * Try to take an item from the current room, otherwise print an error
     * message.
     */
    private void take(Command command)
    {

    }

    /**
     * Try to drop an item, otherwise print an error message.
     */
    private void drop(Command command)
    {

    }

    /**
     * Try to drop an item, otherwise print an error message.
     */
    private void give(Command command)
    {

    }

    /**
     * "Quit" was entered. Check the rest of the command to see whether we
     * really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;  // signal that we want to quit
        }
    }

    public void showARoomExits(Room room)
    {
        var exits = room.getExits();

        exits.forEach(System.out::print);
    }
}
