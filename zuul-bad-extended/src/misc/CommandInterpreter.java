package misc;

import main.CommandInfo;
import main.CommandWords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


/**
 * Handle the command in a new way.
 * Send it the user input via String
 * And it will detect which are commands or parameters.
 * And execute the command when it's fully formatted.
 * @author Axel Ambert
 * @version 1.0
 */
public class CommandInterpreter implements Observable
{
  private final ArrayList<String> actionWords;
  private final ArrayList<Observer> observersList;
  private final String[] listOfCommand;

  /**
   * Constructor of the CommandInterpreter class
   */
  public CommandInterpreter()
  {
    this.listOfCommand = CommandWords.getValidCommands();
    this.actionWords = new ArrayList<>();
    this.observersList = new ArrayList<>();
  }

  /**
   * Reset the command list
   */
  public void reset()
  {
    this.actionWords.clear();
  }

  /**
   * Add a value to the command interpreter and analyze it.
   * @param value to add
   */
  public void addValue(String value)
  {
    boolean isACommand = Arrays.stream(this.listOfCommand)
                               .anyMatch(command -> command.equals(value));

    if (isACommand)
      this.reset();
    this.actionWords.add(value);
    this.onUpdate();
  }


  /**
   * Function called everytime an input is done.
   */
  public void onUpdate()
  {
    ArrayList<CommandInfo> commandInfos;
    CommandInfo infos;
    Optional<CommandInfo> optionalInfos;

    if (this.actionWords.size() < 1)
      return;
    commandInfos = CommandWords.getAllCommandInfo();
    optionalInfos = commandInfos.stream().filter(info -> this.actionWords.get(0).equals(info.getCommandName())).findFirst();
    if (optionalInfos.isEmpty())
      return;
    infos = optionalInfos.get();
    if (infos.getNumberOfArguments() == this.actionWords.size() - 1) {
      this.update();
    }
  }

  @Override
  public void addObserver(Observer observerToAdd)
  {
    this.observersList.add(observerToAdd);
  }

  @Override
  public void removeObserver(Observer observerToRemove)
  {
    this.observersList.remove(observerToRemove);
  }

  /**
   * Function called every time a command is ready.
   */
  @Override
  public void update()
  {
    this.observersList.stream().forEach(observer -> observer.onUpdate(this.actionWords.toArray(new String[0])));
  }
}
