package main;

/**
 * Parse a string
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class CommandParser
{

  /**
   * Create a CommandParser object.
   */
  public CommandParser()
  {

  }

  /**
   * Return a Command object thanks to a String.
   *
   * @param toParse raw input to parse
   * @param delimiters delimiters to remove, can be a regex
   * @return the Command object filled with the input
   */

  public Command parse(String toParse, String delimiters)
  {
    String[] parsedCommand = toParse.split(delimiters);

    return (new Command(parsedCommand));
  }
}
