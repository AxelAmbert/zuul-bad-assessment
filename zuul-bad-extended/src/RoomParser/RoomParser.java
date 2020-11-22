package RoomParser;

import main.Room;
import misc.CreationOptions;
import misc.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Predicate;

public abstract class RoomParser
{
  protected HashMap<String, Room> rooms;
  protected Room startRoom;

  public Room update(String filePath, CreationOptions options)
  {
    return (null);
  }

  protected void applyOptions(CreationOptions options)
  {
    if (options == null)
      return;
    if (options.isRemoveNoExit()) {
      this.removeWithoutExits();
    }
    if (options.isRemoveNoItem()) {
      this.removeWithoutItem();
    }
    if (options.isAddItem()) {
      this.addItem(options.getBaseItem());
    }
  }

  private void removeWithoutItem()
  {
    Predicate<Room> predicate = new Predicate<Room>()
    {
      @Override
      public boolean test(Room room)
      {
        return (room.getInventory().getItems().size() == 0);
      }
    };
    this.deleteRoomWithCondition(predicate);
  }

  private void removeWithoutExits()
  {
    Predicate<Room> predicate = new Predicate<Room>()
    {
      @Override
      public boolean test(Room room)
      {
        return (room.getExits().size() == 0);
      }
    };
    this.deleteRoomWithCondition(predicate);
  }

  private void deleteRoomWithCondition(Predicate<Room> predicate)
  {
    Collection<Room> allRooms = this.rooms.values();
    Room[] invalidRooms = allRooms.stream().filter(predicate).toArray(Room[]::new);

    this.removeAllInvalidRooms(invalidRooms);
  }

  private void removeAllInvalidRooms(Room[] invalidRooms)
  {
    for (Room roomToRemove : invalidRooms) {
      this.rooms.values().remove(roomToRemove);
      this.rooms.values().stream().forEach(removeFromRoom -> removeFromRoom.removeExit(roomToRemove));
    }
  }

  private void addItem(Item itemToAdd)
  {
    this.rooms.values()
              .stream()
              .filter((roomTest) -> roomTest.getExits().size() != 0)
              .forEach((room) ->
              {
                String itemName = room.getName() + "-" + itemToAdd.getName();
                Item newItem = new Item(itemName,
                                        itemToAdd.getWeight(),
                                        itemToAdd.getVisualRepresentation());

                room.getInventory().insertItem(newItem);
              });
  }

}
