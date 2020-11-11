package main;

public class CommandInfo
{
  private String commandName;
  private String commandDescription;
  private String commandImagePath;

  public CommandInfo(String name, String description, String path)
  {
    this.commandName = name;
    this.commandDescription = description;
    this.commandImagePath = path;
  }

  public String getCommandName()
  {
    return commandName;
  }

  public String getCommandDescription()
  {
    return commandDescription;
  }

  public String getCommandImagePath()
  {
    return commandImagePath;
  }
}
