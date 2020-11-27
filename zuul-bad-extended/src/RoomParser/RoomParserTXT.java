package RoomParser;

import main.Room;
import misc.CreationOptions;
import misc.Item;

import java.io.File;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Class RoomParserTXT - the parser that creates and links rooms.
 * Load only text files.
 * <p>
 * The goal of the RoomParserTXT is to look in a file and to recreate the
 * scenery of the game by creating room, putting objects in the room and
 * linking room.
 * <p>
 * Use it to handle your room creation in an easier way or to build a level
 * creator for any game that implement the Room class.
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class RoomParserTXT extends RoomParser
{
  private final short NORMAL_FORMAT_SIZE = 6;

  public RoomParserTXT()
  {
    this.startRoom = null;
    this.rooms = new HashMap<>();
  }

  /**
   * Update the current scenery
   * @param filePath to load to create rooms
   * @param options creations option
   * @return the start room, or null if no start room
   */
  @Override
  public Room update(String filePath, CreationOptions options)
  {
    Stream<String> stream;

    this.rooms = new HashMap<>();
    try {
      stream = getStream(filePath);
      stream.forEach(this::createARoom);
      stream = getStream(filePath);
      stream.forEach(this::setRoomsVariables);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    this.applyOptions(options);
    return (startRoom);
  }

  /**
   * Get file stream
   * @param filePath to load
   * @return the file stream
   */
  private Stream<String> getStream(String filePath)
  {
    Stream<String> stream = null;

    try {
      stream = Files.lines(new File(filePath).toPath())
              .map(String::trim)
              .filter(s -> !s.isEmpty());
    } catch (Exception e) {
      System.err.println("Error while parsing configuration file. " + e.getMessage());
    }
    return (stream);
  }

  /**
   * Create a room by looking at a line of the loaded file.
   * @param line to look at
   */
  private void createARoom(String line)
  {
    String[] infos = line.split(", ");
    String name;
    String description;
    Room room;

    if (infos.length < this.NORMAL_FORMAT_SIZE) {
      System.err.println("Wrong format of file.");
      System.exit(1);
    }
    name = infos[0];
    description = infos[1];
    room = new Room(description, name);
    if (this.startRoom == null)
      this.startRoom = room;
    this.rooms.put(name, room);
  }

  /**
   * Set room variables like: Description, VisualRepresentation, etc...
   * @param line to look at
   */
  private void setRoomsVariables(String line)
  {
    String[] infos = line.split(", ");
    Room room = this.rooms.get(infos[0]);


    if (room == null) {
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

  /**
   * Set the exits to the corresponding room
   * @param room to set exit
   * @param infos of the room
   */
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

  /**
   * Set the items to the corresponding room
   * @param room to set the items
   * @param infos of the room
   */
  private void setItemsToNewRoom(Room room, String[] infos)
  {
    for (int i = 6; i < infos.length; i += 2) {
      String name = infos[i];
      Integer weight = Integer.parseInt(infos[i + 1]);

      room.getInventory().insertItem(new Item(name, weight));
    }
  }
}
