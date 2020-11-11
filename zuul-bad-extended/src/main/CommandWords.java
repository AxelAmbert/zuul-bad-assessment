package main;

import communication.Controller;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;


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
  private static String[] validCommands;
  private static HashMap<String, CommandInfo> allCommands;



  /**
   * Check whether a given String is a valid command word.
   *
   * @return true if a given string is a valid command,
   * false if it isn't.
   */
  public static boolean isCommand(String aString)
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

  public static String getCommandString()
  {
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < CommandWords.validCommands.length; i++) {
      builder.append(CommandWords.validCommands[i]);
      if (i + 1 < CommandWords.validCommands.length) {
        builder.append(" ");
      }
    }
    return (builder.toString());
  }

  public static void update(String filePath)
  {
    JSONArray roomsArray = CommandWords.getRoomArray(filePath);

    CommandWords.allCommands = new HashMap<>();
    roomsArray.forEach(CommandWords::createACommandInfo);
    CommandWords.validCommands = CommandWords.allCommands.keySet().toArray(new String[0]);
  }

  private static JSONArray getRoomArray(String filePath)
  {
    String file = "";
    JSONArray array = null;
    JSONObject object = null;

    try {
      file = Files.readString(Path.of(filePath));
      object = new JSONObject(file);
      array = object.getJSONArray("commands");
    } catch (IOException exception) {
      System.err.println("Error while parsing commands " + exception.toString());
      System.exit(1);
    }
    return (array);
  }

  private static void createACommandInfo(Object command)
  {
    JSONObject parsedCommand = new JSONObject(command.toString());
    String name = parsedCommand.getString("name");
    String description = parsedCommand.getString("description");
    String imagePath = parsedCommand.getString("imagePath");

    CommandWords.allCommands.put(name, new CommandInfo(name, description, imagePath));
  }

  public static ArrayList<CommandInfo> getAllCommandInfo()
  {
    return (new ArrayList<>(CommandWords.allCommands.values()));
  }
}
