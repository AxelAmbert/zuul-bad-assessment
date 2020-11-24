package RoomInfos.controller;

import RoomInfos.model.RoomInfoModel;
import RoomInfos.view.ListOfClickableObjectsView;
import main.Room;
import misc.CommandInterpreter;
import misc.Inventory;
import misc.Observer;
import misc.OneArgObjectInterface;
import player.Player;

import java.util.HashMap;

public class RoomInfoController
{
  private Room bindedRoom;
  private Player bindedPlayer;
  private final RoomInfoModel model;

  private Observer playerInventoryObserver;
  private Observer roomInventoryObserver;
  private Observer roomChangeObserver;



  public RoomInfoController(RoomInfoModel model, Room room, Player player)
  {
    this.model = model;
    this.bindedPlayer = player;
    this.bindedRoom = room;
    this.createObservers();
  }

  private void createObservers()
  {
    this.playerInventoryObserver = new Observer(object -> model.updateModel(bindedRoom, bindedPlayer));
    this.roomInventoryObserver = new Observer(object -> model.updateModel(bindedRoom, bindedPlayer));
    this.roomChangeObserver = new Observer(object -> {
      Player player = (Player) object;

      removeChildrenObservers();
      updateController(player.getCurrentRoom(), player);
    });
  }

  public void updateController(Room room, Player player)
  {
    this.bindedRoom = room;
    this.bindedPlayer = player;
    this.bindedPlayer.getInventory().addObserver(this.playerInventoryObserver);
    this.bindedRoom.getInventory().addObserver(this.roomInventoryObserver);
    this.bindedPlayer.addObserver(this.roomChangeObserver);
    this.model.updateModel(room, player);
  }

  private void removeChildrenObservers()
  {
    this.bindedRoom.getInventory().removeObserver(this.roomInventoryObserver);
    this.bindedPlayer.getInventory().removeObserver(this.playerInventoryObserver);
    this.bindedPlayer.removeObserver(this.roomChangeObserver);
  }

  public Room getBindedRoom()
  {
    return bindedRoom;
  }

  public Player getBindedPlayer()
  {
    return bindedPlayer;
  }

  public RoomInfoModel getModel()
  {
    return model;
  }

  public void setBindedRoom(Room bindedRoom)
  {
    this.bindedRoom = bindedRoom;
  }

  public void setBindedPlayer(Player bindedPlayer)
  {
    this.bindedPlayer = bindedPlayer;
  }
}
