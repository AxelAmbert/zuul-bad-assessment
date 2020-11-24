package RoomInfos.model;

import RoomInfos.controller.ListOfClickableObjectsController;
import RoomInfos.controller.RoomInfoController;
import RoomInfos.view.ListOfClickableObjectsView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.CommandWords;
import main.Room;
import misc.CommandInterpreter;
import misc.Observable;
import misc.Observer;
import player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class RoomInfoModel implements Observable
{

  private final CommandInterpreter commandInterpreter;
  private final HashMap<String, ListOfClickableObjectsView> views;

  private ListOfClickableObjectsView actionList;
  private ListOfClickableObjectsView playerList;
  private ListOfClickableObjectsView playerInventoryList;
  private ListOfClickableObjectsView roomInventoryList;
  private ListOfClickableObjectsView exitList;


  ArrayList<Observer> observers;
  private Observer updatedValueObserver;


  public RoomInfoModel(CommandInterpreter interpreter)
  {
    this.observers = new ArrayList<>();
    this.views = new HashMap<>();
    this.commandInterpreter = interpreter;
    this.updatedValueObserver = new Observer(object -> {
      String value = (String)object;

      this.commandInterpreter.addValue(value);
      System.out.println("GO " + value);
    });
  }

  public void updateModel(Room bindedRoom, Player bindedPlayer)
  {
    Predicate<Player> filter = player -> !player.getName().equals(bindedPlayer.getName());

    this.actionList = this.configureAList(VBox.class, CommandWords.getAllCommandInfo().stream(), null);
    this.playerList = this.configureAList(VBox.class, bindedRoom.getPlayerList().stream(), filter);
    this.playerInventoryList = this.configureAList(HBox.class, bindedPlayer.getInventory().getItems().stream(), null);
    this.roomInventoryList = this.configureAList(HBox.class, bindedRoom.getInventory().getItems().stream(), null);
    this.exitList = this.configureAList(HBox.class, bindedRoom.getExits().values().stream(), null);
    this.setupViewMap();
    this.update();
  }

  private <Box, Iter> ListOfClickableObjectsView<Box, Iter> configureAList(Class<? extends Box> constructor,
                                                                          Stream<Iter> stream,
                                                                          Predicate<Iter> filter)
  {
    ListOfClickableObjectsModel<Iter> model = new ListOfClickableObjectsModel<>();
    ListOfClickableObjectsController<Iter> controller = new ListOfClickableObjectsController<>(model);
    ListOfClickableObjectsView<Box, Iter> view = new ListOfClickableObjectsView<>(constructor, controller, model);


    model.updateModel(stream.filter(Optional.ofNullable(filter).orElse(iter -> true)));
    controller.addObserver(this.updatedValueObserver);
    return (view);
  }

  private void setupViewMap()
  {
    views.clear();
    views.put("actionList", this.actionList);
    views.put("playerList", this.playerList);
    views.put("playerInventoryList", this.playerInventoryList);
    views.put("roomInventoryList", this.roomInventoryList);
    views.put("exitList", this.exitList);
  }

  public HashMap<String, ListOfClickableObjectsView> getViews()
  {
    return (this.views);
  }

  @Override
  public void addObserver(Observer observerToAdd)
  {
    this.observers.add(observerToAdd);
  }

  @Override
  public void removeObserver(Observer observerToRemove)
  {
    this.observers.remove(observerToRemove);
  }

  @Override
  public void update()
  {
    this.observers.stream().forEach(observer -> observer.onUpdate(views));
  }
}
