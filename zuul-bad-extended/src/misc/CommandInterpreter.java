package misc;

import main.CommandInfo;
import main.CommandWords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class CommandInterpreter implements Observable
{
  private final ArrayList<String> actionWords;
  private final ArrayList<Observer> observersList;
  private final String[] listOfCommand;

  public CommandInterpreter()
  {
    this.listOfCommand = CommandWords.getValidCommands();
    this.actionWords = new ArrayList<>();
    this.observersList = new ArrayList<>();
  }

  public void reset()
  {
    this.actionWords.clear();
  }

  public void addValue(String value)
  {
    boolean isACommand = Arrays.stream(this.listOfCommand)
                               .anyMatch(command -> command.equals(value));

    if (isACommand)
      this.reset();
    this.actionWords.add(value);
    this.onUpdate();
  }

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

  @Override
  public void update()
  {
    this.observersList.stream().forEach(observer -> observer.onUpdate(this.actionWords.toArray(new String[0])));
  }
}
