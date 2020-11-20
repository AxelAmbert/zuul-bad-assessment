package main;

public class CommandInfo
{
  private String commandName;
  private String commandDescription;
  private String commandImagePath;
  private Integer numberOfArguments;

  public CommandInfo(String name, String description, String path, Integer numberOfArguments)
  {
    this.commandName = name;
    this.commandDescription = description;
    this.commandImagePath = path;
    this.numberOfArguments = numberOfArguments;
  }

  public String getCommandName()
  {
    return commandName;
  }

  public String getName() {return (getCommandName());}
  public String getDescription()
  {
    return commandDescription;
  }

  public String getVisualRepresentation()
  {
    return commandImagePath;
  }
  public Integer getNumberOfArguments()
  {
    return numberOfArguments;
  }

}
