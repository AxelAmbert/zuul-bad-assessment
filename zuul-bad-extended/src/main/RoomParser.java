package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import misc.Item;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class RoomParser - the parser that creates and links rooms.
 * <p>
 * The goal of the RoomParser is to look in a file and to recreate the
 * scenery of the game by creating room, putting objects in the room and
 * linking room.
 * <p>
 * Use it to handle your room creation in an easier way or to build a level
 * creator for any game that implement the Room class.
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class RoomParser
{
  private HashMap<String, Room> rooms;
  private Room startRoom;


  /**
   * Create an instance of the RoomParser class.
   */
  RoomParser()
  {
  }

  /**
   * Update the scenery of the game by reading a JSON file that
   * contains every element that set up a room, then :
   * Create every rooms;
   * Link the rooms;
   * Add items into rooms.
   * <p>
   * The JSON file must contains a "start room".
   * Look at the default rooms.json to see the constitution of the file.
   *
   * @param filePath the JSON file to read to update the scenery
   * @return the start room.
   */

  public Room update(String filePath)
  {
    JSONArray roomsArray = this.getRoomArray(filePath);

    this.rooms = new HashMap<>();
    roomsArray.forEach(this::createARoom);
    roomsArray.forEach(this::parseRelatedRoom);
    roomsArray.forEach(this::parseItems);
    return (startRoom);
  }

  /**
   * Get a JSONArray representing every room in the game.
   *
   * Look for the file at filePath and create a JSONArray
   * containing every room in the game.
   *
   * @param filePath the JSON file to read to update the scenery
   * @return the JSONArray of every room.
   */

  private JSONArray getRoomArray(String filePath)
  {
    String file = "";
    JSONArray array = null;
    JSONObject object = null;

    try {
      file = Files.readString(Path.of(filePath));
      object = new JSONObject(file);
      array = object.getJSONArray("rooms");
    } catch (IOException exception) {
      System.out.println("Error while parsing rooms " + exception.toString());
    }
    return (array);
  }

  /**
   * Create and add a specific room to the list of rooms.
   * Use this function in a loop or a forEach to create every room.
   *
   * @param room the actual room to create
   */

  private void createARoom(Object room)
  {
    JSONObject parsedRoom = new JSONObject(room.toString());
    String roomName = parsedRoom.getString("roomName");
    String description = parsedRoom.getString("description");
    Room newRoom = new Room(description);

    if (parsedRoom.getBoolean("startRoom") == true) {
      this.startRoom = newRoom;
    }
    this.rooms.put(roomName, newRoom);
  }

  /**
   * Parse a room and look for every other room to link.
   * Use this function in a loop or a forEach to create every room.
   *
   * @param room the actual room to parse
   */

  private void parseRelatedRoom(Object room)
  {
    JSONObject parsedRoom = new JSONObject(room.toString());
    JSONArray relatedRooms = parsedRoom.getJSONArray("relatedRooms");

    for (Object roomToLink : relatedRooms) {
      JSONObject parsedRoomToLink = new JSONObject(roomToLink.toString());

      this.linkARoom(parsedRoom, parsedRoomToLink);
    }
  }

  /**
   * Link a room to another room.
   *
   * @param mainRoom the main room.
   * @param roomToLink the room to link to the main room.
   */

  private void linkARoom(JSONObject mainRoom, JSONObject roomToLink)
  {
    String mainRoomName = mainRoom.getString("roomName");
    String roomToLinkName = roomToLink.getString("roomName");
    String roomToLinkDirection = roomToLink.getString("direction");

    if (this.rooms.containsKey(mainRoomName) == false ||
            this.rooms.containsKey(roomToLinkName) == false) {
      return;
    }
    this.rooms.get(mainRoomName).linkARoom(roomToLinkDirection, this.rooms.get(roomToLinkName));
  }

  /**
   * Parse a room and look for every item to add.
   * Use this function in a loop or a forEach to create every room.
   *
   * @param room the actual room to parse
   */

  private void parseItems(Object room)
  {
    JSONObject parsedRoom = new JSONObject(room.toString());
    JSONArray itemArray = null;

    try {
      itemArray = parsedRoom.getJSONArray("items");

      for (Object item : itemArray) {
        JSONObject parsedItem = new JSONObject(item.toString());

        this.addItems(parsedItem, parsedRoom);
      }
    } catch (JSONException ea) {

    }
  }

  /**
   * Link a room to another room.
   *
   * @param parsedItem the parsed item to add.
   * @param parsedRoom the room where to add the parsed item.
   */

  private void addItems(JSONObject parsedItem, JSONObject parsedRoom)
  {
    String room = parsedRoom.getString("roomName");
    Item item = new Item(parsedItem.getString("name"), parsedItem.getInt("weight"));

    this.rooms.get(room).getInventory().insertItem(item);
  }
}