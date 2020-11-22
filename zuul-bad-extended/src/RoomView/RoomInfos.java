package RoomView;

import RoomView.controller.ListOfClickableObjectsController;
import RoomView.model.ListOfClickableObjectsModel;
import RoomView.view.ListOfClickableObjectsView;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import main.*;
import misc.*;
import player.Player;

import java.util.function.Predicate;
import java.util.stream.Stream;


public class RoomInfos extends BorderPane
{
  private RoomDescription roomDescription;
  private RoomRepresentation roomRepresentation;
  private CommandInterpreter commandInterpreter;
  private HBox inventoriesView;

  private Player bindedPlayer;
  private Room bindedRoom;

  private Observer playerInventoryObserver;
  private Observer roomInventoryObserver;
  private Observer roomChangeObserver;
  private Observer updatedValueObserver;

  private ListOfClickableObjects<VBox, CommandInfo> actionList;
  private ListOfClickableObjects<VBox, Player> playerList;
  private ListOfClickableObjects<HBox, Item> playerInventoryList;
  private ListOfClickableObjects<HBox, Item> roomInventoryList;
  private ListOfClickableObjects<HBox, Room> availableExitList;

  public RoomInfos(Room room, Player player, CommandInterpreter interpreter)
  {
    this.commandInterpreter = interpreter;
    this.updateView(room, player);
  }

  public void updateView(Room room, Player player)
  {
    this.bindedRoom = room;
    this.bindedPlayer = player;
    this.roomDescription = new RoomDescription(room.getDescription());
    this.roomRepresentation = new RoomRepresentation(room.getVisualRepresentation());
    this.inventoriesView = new HBox();
    this.setUpdatedValueObserver();
    this.configureAllList();
    this.inventoriesView.getChildren().addAll(this.configurePlayerInventoryList(), this.configureRoomInventoryList(), this.configureAvailableExitList());
    /*HBox.setHgrow(this.playerInventoryList, Priority.ALWAYS);
    HBox.setHgrow(this.roomInventoryList, Priority.ALWAYS);
    HBox.setHgrow(this.availableExitList, Priority.ALWAYS);
*/
    //this.configureActionList();
    this.setTop(this.roomDescription);
    this.setCenter(this.roomRepresentation);
    this.setLeft(this.configureActionList());
    this.setBottom(this.inventoriesView);
    this.setRight(this.configurePlayerList());
    /*BorderPane.setAlignment(this.actionList, Pos.CENTER_RIGHT);
    BorderPane.setAlignment(this.playerList, Pos.CENTER_RIGHT);
*/
    this.setObserversToChildren();
    this.addInventoryObservers();
    this.addRoomChangeObserver();
  }

  private ListOfClickableObjectsView test()
  {
    ListOfClickableObjectsModel<CommandInfo> model = new ListOfClickableObjectsModel<>();
    ListOfClickableObjectsController<CommandInfo> controller = new ListOfClickableObjectsController<>(model);
    ListOfClickableObjectsView<VBox, CommandInfo> view = new ListOfClickableObjectsView<>(VBox.class, controller, model);

    model.updateModel(CommandWords.getAllCommandInfo().stream());
    controller.addObserver(this.updatedValueObserver);
    return (view);
  }

  private void setObserversToChildren()
  {
  /*  Observer clickObserver = new Observer(new OneArgObjectInterface()
    {
      @Override
      public void run(Object object)
      {
        String command = (String) object;

        commandInterpreter.addValue(command);
      }
    });

    this.actionList.addObserver(clickObserver);
    this.playerList.addObserver(clickObserver);
    this.playerInventoryList.addObserver(clickObserver);
    this.roomInventoryList.addObserver(clickObserver);
    this.availableExitList.addObserver(clickObserver);*/
  }

  private void addInventoryObservers()
  {
    this.playerInventoryObserver = new Observer(new OneArgObjectInterface()
    {
      @Override
      public void run(Object object)
      {
        Inventory newInventory = (Inventory) object;

        playerInventoryList.updateView(newInventory.getItems().stream());
      }
    });
    bindedPlayer.getInventory().addObserver(this.playerInventoryObserver);

    this.roomInventoryObserver = new Observer(new OneArgObjectInterface()
    {
      @Override
      public void run(Object object)
      {
        Inventory newInventory = (Inventory) object;

        roomInventoryList.updateView(newInventory.getItems().stream());
      }
    });
    bindedRoom.getInventory().addObserver(this.roomInventoryObserver);
  }

  private void addRoomChangeObserver()
  {
    this.roomChangeObserver = new Observer(new OneArgObjectInterface()
    {
      @Override
      public void run(Object object)
      {
        Player player = (Player) object;

        removeChildrenObservers();
        updateView(player.getCurrentRoom(), player);
      }
    });
    this.bindedPlayer.addObserver(this.roomChangeObserver);
  }

  private void removeChildrenObservers()
  {
    this.bindedRoom.getInventory().removeObserver(this.roomInventoryObserver);
    this.bindedPlayer.getInventory().removeObserver(this.playerInventoryObserver);
    this.bindedPlayer.removeObserver(this.roomChangeObserver);
  }

  private void configureAllList()
  {
    this.configureActionList();
    this.configureAvailableExitList();
    this.configurePlayerInventoryList();
    this.configurePlayerList();
    this.configureRoomInventoryList();
  }

  private ListOfClickableObjectsView configureActionList()
  {
    Stream<CommandInfo> stream = CommandWords.getAllCommandInfo().stream();
    ListOfClickableObjectsModel<CommandInfo> model = new ListOfClickableObjectsModel<>();
    ListOfClickableObjectsController<CommandInfo> controller = new ListOfClickableObjectsController<>(model);
    ListOfClickableObjectsView<HBox, CommandInfo> view = new ListOfClickableObjectsView<>(HBox.class, controller, model);

    model.updateModel(stream);
    controller.addObserver(this.updatedValueObserver);
    return (view);
  }



  private ListOfClickableObjectsView configurePlayerList()
  {
    Stream<Player> stream = this.bindedRoom.getPlayerList().stream().filter(player -> !player.getName().equals(this.bindedPlayer.getName()));
    ListOfClickableObjectsModel<Player> model = new ListOfClickableObjectsModel<>();
    ListOfClickableObjectsController<Player> controller = new ListOfClickableObjectsController<>(model);
    ListOfClickableObjectsView<HBox, Player> view = new ListOfClickableObjectsView<>(HBox.class, controller, model);

    model.updateModel(stream);
    controller.addObserver(this.updatedValueObserver);
    return (view);
  }

  private ListOfClickableObjectsView configurePlayerInventoryList()
  {
    Stream<Item> stream = this.bindedPlayer.getInventory().getItems().stream();
    ListOfClickableObjectsModel<Item> model = new ListOfClickableObjectsModel<>();
    ListOfClickableObjectsController<Item> controller = new ListOfClickableObjectsController<>(model);
    ListOfClickableObjectsView<HBox, Item> view = new ListOfClickableObjectsView<>(HBox.class, controller, model);

    model.updateModel(stream);
    controller.addObserver(this.updatedValueObserver);
    return (view);

  }

  private ListOfClickableObjectsView configureRoomInventoryList()
  {
    Stream<Item> stream = this.bindedRoom.getInventory().getItems().stream();
    ListOfClickableObjectsModel<Item> model = new ListOfClickableObjectsModel<>();
    ListOfClickableObjectsController<Item> controller = new ListOfClickableObjectsController<>(model);
    ListOfClickableObjectsView<HBox, Item> view = new ListOfClickableObjectsView<>(HBox.class, controller, model);

    model.updateModel(stream);
    controller.addObserver(this.updatedValueObserver);
    return (view);
  }

  private ListOfClickableObjectsView configureAvailableExitList()
  {
    Stream<Room> stream = this.bindedRoom.getExits().values().stream();
    ListOfClickableObjectsModel<Room> model = new ListOfClickableObjectsModel<>();
    ListOfClickableObjectsController<Room> controller = new ListOfClickableObjectsController<>(model);
    ListOfClickableObjectsView<HBox, Room> view = new ListOfClickableObjectsView<>(HBox.class, controller, model);

    model.updateModel(stream);
    controller.addObserver(this.updatedValueObserver);
    return (view);
  }

  private void setUpdatedValueObserver()
  {
    this.updatedValueObserver = new Observer(value -> {
      String command = (String) value;

      this.commandInterpreter.addValue(command);
    });
  }
}
