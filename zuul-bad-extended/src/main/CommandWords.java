package main;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private String[] validCommands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords(String path)
    {
        this.loadCommands(path);
    }

    public void loadCommands(String path)
    {
        String commands = "";

        try {
            commands = Files.readString(Path.of(path));
            this.validCommands = commands.split(";");
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {

        for (String validCommand : validCommands) {
            if (validCommand.equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    public String getCommandString()
    {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < this.validCommands.length; i++) {
            builder.append(this.validCommands[i]);
            if (i + 1 < this.validCommands.length) {
                builder.append(" ");
            }
        }
        return (builder.toString());
    }
}
