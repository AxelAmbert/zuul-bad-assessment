package RoomView;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import main.*;
import misc.CommandInterpreter;
import misc.Inventory;
import misc.Observer;
import misc.OneArgObjectInterface;
import player.Player;

import java.util.stream.Stream;


public class RoomView extends BorderPane
{
  private RoomDescription roomDescription;
  private RoomRepresentation roomRepresentation;
  private ActionList actionList;
  private CommandInterpreter commandInterpreter;
  private HBox inventoriesView;
  private InventoryView inventoryView;
  private InventoryView roomInventoryView;
  private RoomListView roomListView;

  private Player bindedPlayer;
  private Room bindedRoom;

  private Observer playerInventoryObserver;
  private Observer roomInventoryObserver;
  private Observer roomChangeObserver;

  private ListOfClickableObjects<VBox, CommandInfo> actionList2;

  public RoomView(Room room, Player player, CommandInterpreter interpreter)
  {
    this.commandInterpreter = interpreter;
    this.updateView(room, player);
  }

  public void updateView(Room room, Player player)
  {
    this.bindedRoom = room;
    this.bindedPlayer = player;
    this.actionList = new ActionList();
    this.roomDescription = new RoomDescription(room.getDescription());
    this.roomRepresentation = new RoomRepresentation(room.getVisualRepresentation());
    this.inventoryView = new InventoryView(player.getInventory());
    this.roomInventoryView = new InventoryView(room.getInventory());
    this.inventoriesView = new HBox();
    this.roomListView = new RoomListView(room);

    PlayerList ok = new PlayerList(this.bindedRoom);
    this.inventoriesView.getChildren().addAll(this.inventoryView, this.roomInventoryView, roomListView);
    HBox.setHgrow(this.inventoryView, Priority.ALWAYS);
    HBox.setHgrow(this.roomInventoryView, Priority.ALWAYS);
    HBox.setHgrow(this.roomListView, Priority.ALWAYS);

    this.configureActionList();
    this.setTop(this.roomDescription);
    this.setCenter(this.roomRepresentation);
    this.setLeft(this.actionList);
    this.setBottom(this.inventoriesView);
    this.setRight(ok);
    BorderPane.setAlignment(this.actionList, Pos.CENTER_RIGHT);
    BorderPane.setAlignment(ok, Pos.CENTER_RIGHT);

    this.setObserversToChildren();
    this.addInventoryObservers();
    this.addRoomChangeObserver();

  }

  private void setObserversToChildren()
  {
    Observer clickObserver = new Observer(new OneArgObjectInterface()
    {
      @Override
      public void run(Object object)
      {
        String command = (String)object;

        commandInterpreter.addValue(command);
      }
    });

    this.actionList.addObserver(clickObserver);
    this.roomListView.addObserver(clickObserver);
    this.roomInventoryView.addObserver(clickObserver);
    this.inventoryView.addObserver(clickObserver);
  }

  private void addInventoryObservers()
  {
    this.playerInventoryObserver = new Observer(new OneArgObjectInterface()
    {
      @Override
      public void run(Object object)
      {
        Inventory newInventory = (Inventory) object;

        inventoryView.updateView(newInventory);
      }
    });
    bindedPlayer.getInventory().addObserver(this.playerInventoryObserver);

    this.roomInventoryObserver = new Observer(new OneArgObjectInterface()
    {
      @Override
      public void run(Object object)
      {
        Inventory newInventory = (Inventory) object;

        roomInventoryView.updateView(newInventory);
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
    this.actionList2 = new ListOfClickableObjects<>(VBox.class, stream);
  }

}
