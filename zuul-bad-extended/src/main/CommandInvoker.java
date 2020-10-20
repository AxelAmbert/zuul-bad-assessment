package main;

import functionalities.Functionality;

import java.lang.reflect.Constructor;

/**
 * Handle command and invoke it.
 * This class is not responsible for the correctness of
 * the command.
 * @author Axel Ambert
 * @version 1.0
 */

public class CommandInvoker
{
  /**
   * Create a command invoker object
   */
  public CommandInvoker()
  {
  }

  /**
   * Invoke the command object.
   * To invoke the command, the function use reflection to retrieve
   * the constructor of the command's class which mean that the
   * command to execute must:
   * Be in the functionalities package;
   * Inherit from the functionalities.Functionality interface.
   * <p>
   * The function will automatically transform a "go" command into
   * a "Go" command to respect Coding Style in classes.
   * @param command a Command object filled with the user's input
   */

  public void invoke(Command command)
  {
    String commandName = this.adaptCaseSensitive(command.getCommandName());

    try {
      Class<?> cls = Class.forName("functionalities." + commandName);
      Constructor ct = cls.getConstructor();
      Functionality fun = (Functionality) ct.newInstance();

      fun.run(command);
    } catch (Throwable e) {
      var s = e.getStackTrace();

      for (var ss : s) {
        System.out.println(ss);
      }
      System.err.println(commandName + " is not a valid command " + e.toString());
    }
  }

  /**
   * This function will ensure that the command name starts
   * with a capital letter.
   * @param command the command name
   */

  private String adaptCaseSensitive(String command)
  {
    StringBuilder builder = new StringBuilder(command);

    if (builder.charAt(0) >= 97 && builder.charAt(0) <= 122) {
      builder.setCharAt(0, (char) (builder.charAt(0) - 32));
    }
    return (builder.toString());
  }
}

