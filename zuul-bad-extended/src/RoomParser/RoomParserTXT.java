package RoomParser;

import main.Room;
import misc.Item;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.stream.Stream;

public class RoomParserTXT implements RoomParser
{
  private HashMap<String, Room> rooms;
  private Room startRoom;

  public RoomParserTXT()
  {
    this.rooms = new HashMap<>();
  }

  @Override
  public Room update(String filePath)
  {
    Stream<String> stream;

    try {
      stream = getStream(filePath);
      stream.forEach(this::createARoom);
      stream = getStream(filePath);
      stream.forEach(this::setRoomsVariables);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
      return (null);
    }
    return (startRoom);
  }

  private Stream<String> getStream(String filePath)
  {
    Stream<String> stream = null;

    try {
      stream = Files.lines(new File(filePath).toPath())
              .map(String::trim)
              .filter(s -> !s.isEmpty());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (stream);
  }

  private void createARoom(String line)
  {
    String[] infos = line.split(", ");
    String name = infos[0];
    String description = infos[0];
    Room room =  new Room(name, description);

    if (this.startRoom == null)
      this.startRoom = room;
    this.rooms.put(name, room);
  }

  private void setRoomsVariables(String line)
  {
    String[] infos = line.split(", ");
    Room room = this.rooms.get(infos[0]);


    if (room == null) {
      System.out.println("ciao " + infos[0]);
      return;
    }
    try {
      this.setExitsToNewRoom(room, infos);
      this.setItemsToNewRoom(room, infos);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
      System.out.println("Error at line [" + line + "]");
    }
  }

  private void setExitsToNewRoom(Room room, String[] infos)
  {
    final String[] directions = new String[]{"north", "east", "south", "west"};

    for (int i = 2; i < 6; i++) {
      Room tmpRoom;

      if (infos[i].equals("null"))
        continue;
      tmpRoom = this.rooms.get(infos[i]);
      if (tmpRoom == null)
        continue;
      room.linkARoom(directions[i - 2], tmpRoom);
    }
  }

  private void setItemsToNewRoom(Room room, String[] infos)
  {
    System.out.println("Add items.");
    for (int i = 6; i < infos.length; i += 2) {
      String name = infos[i];
      Integer weight = Integer.parseInt(infos[i + 1]);
      System.out.println("J'add  " + name + " " + weight);

      room.getInventory().insertItem(new Item(name, weight));
    }
  }
}
