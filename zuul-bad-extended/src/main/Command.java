package main;

import java.util.Arrays;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two strings: a command word and a second
 * word (for example, if the command was "take map", then the two strings
 * obviously are "take" and "map").
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the command word is <null>.
 *
 * If the command had only one word, then the second word is <null>.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class Command
{
    private String commandName;
    private String[] args;
    private String[] fullCommand;

    public Command(String[] command)
    {
        if (command == null)
            return;
        this.fullCommand = Arrays.copyOf(command, command.length);
        if (command.length == 0)
            return;
        this.commandName = command[0];
        if (command.length > 1)
            return;
        this.args = Arrays.copyOfRange(command, 1, command.length);
    }

    public String getCommandName()
    {
        return this.commandName;
    }

    public String[] getArgs()
    {
        return this.args;
    }

    public String[] getFullCommand()
    {
        return this.fullCommand;
    }
}

