package main;

import java.util.Arrays;

/**
 * Handle command from the user.
 * Use this class to handle user's input in an easier way.
 * It will separate the user's input into three variable :
 * commandName: the command name only;
 * args: the arguments after the command name;
 * fullCommand: the full command, without modifications
 * @author Axel Ambert
 * @version 1.0
 */

public class Command
{
  private String commandName;
  private String[] args;
  private String[] fullCommand;

  /**
   * Create a command object
   *
   * @param command The user input, it must be separated into an array,
   *                for an example, the command "give 100% Axel"
   *                must be in this format : String[]{"give", "100%", "Axel"}
   */
  public Command(String[] command)
  {
    if (command == null) {
      return;
    }
    this.fullCommand = Arrays.copyOf(command, command.length);
    if (command.length == 0) {
      return;
    }
    this.commandName = command[0];
    if (command.length < 2) {
      return;
    }
    this.args = Arrays.copyOfRange(command, 1, command.length);

  }

  /**
   * Return the command name
   *
   * @return the command name
   */

  public String getCommandName()
  {
    return this.commandName;
  }

  /**
   * Get the command name
   *
   * @return the command name
   */

  public String[] getArgs()
  {
    return Arrays.copyOf(this.args, this.args.length);
  }

  /**
   * Get the full command
   *
   * @return the full command
   */

  public String[] getFullCommand()
  {
    return this.fullCommand;
  }

  /**
   * Get the number of arguments
   *
   * @return the number of arguments
   */

  public int getNumberOfArgs()
  {
    if (this.args == null)
      return (0);
    return (this.args.length);
  }

  /**
   * Get a specific argument
   * Return an empty String if out of bound
   * or if there is no arguments.
   *
   * @param argNb the nth argument to return
   * @return the nth argument
   */

  public String getArgAt(int argNb)
  {
    if (this.args == null || this.args.length < argNb)
      return ("");
    return (this.args[argNb - 1]);
  }
}

