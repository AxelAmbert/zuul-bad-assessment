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


/**
 * Class RoomInfoController -
 * This class is the controller of a view that shows room information and enable
 * interaction with it.
 *
 * @author Axel Ambert
 * @version 1.0
 */
public class RoomInfoController
{
  private Room bindedRoom;
  private Player bindedPlayer;
  private final RoomInfoModel model;

  private Observer playerInventoryObserver;
  private Observer roomInventoryObserver;
  private Observer roomChangeObserver;


  /**
   * constructor of the RoomInfoController class
   * @param model related MVC model
   * @param room to bind
   * @param player to bind
   */
  public RoomInfoController(RoomInfoModel model, Room room, Player player)
  {
    this.model = model;
    this.bindedPlayer = player;
    this.bindedRoom = room;
    this.createObservers();
  }

  /**
   * Create the observers for every lists
   */
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

  /**
   * Update the controller with new values.
   * @param room new binded room
   * @param player new binded player
   */
  public void updateController(Room room, Player player)
  {
    this.bindedRoom = room;
    this.bindedPlayer = player;
    this.bindedPlayer.getInventory().addObserver(this.playerInventoryObserver);
    this.bindedRoom.getInventory().addObserver(this.roomInventoryObserver);
    this.bindedPlayer.addObserver(this.roomChangeObserver);
    this.model.updateModel(room, player);
  }

  /**
   * Remove the observers (useful when room and player change)
   */
  private void removeChildrenObservers()
  {
    this.bindedRoom.getInventory().removeObserver(this.roomInventoryObserver);
    this.bindedPlayer.getInventory().removeObserver(this.playerInventoryObserver);
    this.bindedPlayer.removeObserver(this.roomChangeObserver);
  }

  /**
   * Get the binded room
   * @return the binded room
   */
  public Room getBindedRoom()
  {
    return bindedRoom;
  }

  /**
   * Get the binded player.
   * @return the binded player
   */
  public Player getBindedPlayer()
  {
    return bindedPlayer;
  }

  /**
   * Get the related MVC model.
   * @return the related MVC model
   */
  public RoomInfoModel getModel()
  {
    return model;
  }

  /**
   * Set the binded room.
   * @param bindedRoom to set
   */
  public void setBindedRoom(Room bindedRoom)
  {
    this.bindedRoom = bindedRoom;
  }

  /**
   * Set the binded player.
   * @param bindedPlayer to set
   */
  public void setBindedPlayer(Player bindedPlayer)
  {
    this.bindedPlayer = bindedPlayer;
  }
}
