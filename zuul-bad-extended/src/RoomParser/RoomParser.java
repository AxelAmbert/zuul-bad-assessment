package RoomParser;

import main.Room;
import misc.CreationOptions;
import misc.Item;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Predicate;

/**
 * Abstract class RoomParser
 * This class goal is to handle the parsing of room configuration file
 * This class does nothing by default, you have to implement your own version.
 * <p>
 * This class has the ability to handle options like:
 *  Adding a new item in every valid rooms
 *  Removing all rooms without items
 *  Removing all rooms without exits
 */
public abstract class RoomParser
{
  protected HashMap<String, Room> rooms;
  protected Room startRoom;

  public Room update(String filePath, CreationOptions options)
  {
    return (null);
  }

  /**
   * Apply the options that the user decided to generate the world
   * @param options decided by the user
   */
  protected void applyOptions(CreationOptions options)
  {
    if (options == null)
      return;
    if (options.isRemoveNoItem()) {
      this.removeWithoutItem();
    }
    if (options.isRemoveNoExit()) {
      this.removeWithoutExits();
    }
    if (options.isAddItem()) {
      this.addItem(options.getBaseItem());
    }
  }

  /**
   * Create the predicate to remove every room without item
   */
  private void removeWithoutItem()
  {
    Predicate<Room> predicate = room ->  (room.getInventory().getItems().size() == 0);

    this.deleteRoomWithCondition(predicate);
  }

  /**
   * Create the predicate to remove every room without exit
   */
  private void removeWithoutExits()
  {
    Predicate<Room> predicate = room -> room.getExits().size() == 0;

    this.deleteRoomWithCondition(predicate);
  }

  /**
   * Delete every room that don't fulfill the predicate's conditions.
   * @param predicate to test
   */
  private void deleteRoomWithCondition(Predicate<Room> predicate)
  {
    Collection<Room> allRooms = this.rooms.values();
    Room[] invalidRooms = allRooms.stream().filter(predicate).toArray(Room[]::new);

    this.removeAllInvalidRooms(invalidRooms);
  }

  /**
   * Remove every invalid rooms from the list of rooms, and
   * remove corresponding exits.
   * @param invalidRooms to remove
   */
  private void removeAllInvalidRooms(Room[] invalidRooms)
  {
    for (Room roomToRemove : invalidRooms) {
      this.rooms.values().stream().forEach(removeFromRoom -> {
        removeFromRoom.removeExit(roomToRemove);
      });
    }
    for (Room roomToRemove : invalidRooms) {
      this.rooms.values().remove(roomToRemove);
    }
    this.setStartRoom();
  }

  /**
   * In case of the start room is removed, set a new start room
   */
  private void setStartRoom()
  {
    if (this.rooms.values().contains(this.startRoom) == false) {
      var roomsIterator =  this.rooms.values().iterator();

      if (roomsIterator.hasNext()) {
        this.startRoom = roomsIterator.next();
      }
    }
  }

  /**
   * Add an item to every valid room,
   * The final item's name will be:
   * object's name + "-" + room's name
   * @param itemToAdd to add to every room
   */
  private void addItem(Item itemToAdd)
  {
    this.rooms.values()
            .stream()
            .filter((roomTest) -> roomTest.getExits().size() != 0)
            .forEach((room) ->
            {
              String itemName = itemToAdd.getName() + "-" + room.getName();
              Item newItem = new Item(itemName,
                      itemToAdd.getWeight(),
                      itemToAdd.getVisualRepresentation());

              room.getInventory().insertItem(newItem);
            });
  }

}
