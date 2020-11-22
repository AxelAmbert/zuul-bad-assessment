package RoomView;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import main.*;
import misc.*;
import player.Player;

import java.util.function.Predicate;
import java.util.stream.Stream;


public class RoomView extends BorderPane
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

  private ListOfClickableObjects<VBox, CommandInfo> actionList;
  private ListOfClickableObjects<VBox, Player> playerList;
  private ListOfClickableObjects<HBox, Item> playerInventoryList;
  private ListOfClickableObjects<HBox, Item> roomInventoryList;
  private ListOfClickableObjects<HBox, Room> availableExitList;

  public RoomView(Room room, Player player, CommandInterpreter interpreter)
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

    this.configureAllList();
    this.inventoriesView.getChildren().addAll(this.playerInventoryList, this.roomInventoryList, this.availableExitList);
    HBox.setHgrow(this.playerInventoryList, Priority.ALWAYS);
    HBox.setHgrow(this.roomInventoryList, Priority.ALWAYS);
    HBox.setHgrow(this.availableExitList, Priority.ALWAYS);

    this.configureActionList();
    this.setTop(this.roomDescription);
    this.setCenter(this.roomRepresentation);
    this.setLeft(this.actionList);
    this.setBottom(this.inventoriesView);
    this.setRight(this.playerList);
    BorderPane.setAlignment(this.actionList, Pos.CENTER_RIGHT);
    BorderPane.setAlignment(this.playerList, Pos.CENTER_RIGHT);

    this.setObserversToChildren();
    this.addInventoryObservers();
    this.addRoomChangeObserver();
  }

  /*
*   private ListOfClickableObjects<VBox, CommandInfo> actionList2;
private ListOfClickableObjects<VBox, Player> playerList;
private ListOfClickableObjects<HBox, Item> playerInventoryList;
private ListOfClickableObjects<HBox, Item> roomInventoryList;
private ListOfClickableObjects<HBox, Room> availableExitList;
* */
  private void setObserversToChildren()
  {
    Observer clickObserver = new Observer(new OneArgObjectInterface()
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
    this.availableExitList.addObserver(clickObserver);
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


  private void configureActionList()
  {
    Stream<CommandInfo> stream = CommandWords.getAllCommandInfo().stream();

    this.actionList = new ListOfClickableObjects<>(VBox.class, stream);
  }

  private void configureAllList()
  {
    this.configureActionList();
    this.configureAvailableExitList();
    this.configurePlayerInventoryList();
    this.configurePlayerList();
    this.configureRoomInventoryList();
  }

  private void configurePlayerList()
  {
    Stream<Player> stream = this.bindedRoom.getPlayerList().stream();
    Predicate<? super Player> filter = new Predicate()
    {
      @Override
      public boolean test(Object o)
      {
        Player player = (Player) o;

        return (player.getName().equals(bindedPlayer.getName()));
      }
    };

    this.playerList = new ListOfClickableObjects<>(VBox.class, stream, filter);
  }

  private void configurePlayerInventoryList()
  {
    Stream<Item> stream = this.bindedPlayer.getInventory().getItems().stream();

    this.playerInventoryList = new ListOfClickableObjects<>(HBox.class, stream);
  }

  private void configureRoomInventoryList()
  {
    Stream<Item> stream = this.bindedRoom.getInventory().getItems().stream();

    this.roomInventoryList = new ListOfClickableObjects<HBox, Item>(HBox.class, stream);
  }

  private void configureAvailableExitList()
  {
    Stream<Room> stream = this.bindedRoom.getExits().values().stream();

    this.availableExitList = new ListOfClickableObjects<HBox, Room>(HBox.class, stream);
  }

}
