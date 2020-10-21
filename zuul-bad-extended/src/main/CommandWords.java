package main;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * This class handles the valid commands.
 * Use it to know whether or not an user input
 * is valid.
 *
 * All the command are stored in a file
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class CommandWords
{
  // a constant array that holds all valid command words
  private String[] validCommands;

  /**
   * Create a CommandWords class, and load the available commands.
   *
   * @param path file to open in order to load the available commands.
   */
  public CommandWords(String path)
  {
    this.loadCommands(path);
  }


  /**
   * Load every command in the file.
   * Every command must be separated by a ";" and must
   * exist as a Class in the functionalities package.
   *
   * @param path file to open in order to load the available commands.
   */

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
   *
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

  /**
   * Return all the commands separated by a space.
   * If the commands file contains : "give;take;go"
   * it will return "give take go"
   *
   * @return the list of available commands as a string
   */

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
