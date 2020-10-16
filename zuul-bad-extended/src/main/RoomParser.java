package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import misc.Item;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RoomParser
{
    private HashMap<String, Room> rooms;
    private Room startRoom;

    RoomParser()
    {
    }

    public Room update(String filePath)
    {
        JSONArray roomsArray = this.getRoomArray(filePath);

        this.rooms = new HashMap<>();
        roomsArray.forEach(this::createARoom);
        roomsArray.forEach(this::parseRelatedRoom);
        roomsArray.forEach(this::parseItems);
        return (startRoom);
    }

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

    private void parseRelatedRoom(Object room)
    {
        JSONObject parsedRoom = new JSONObject(room.toString());
        JSONArray relatedRooms = parsedRoom.getJSONArray("relatedRooms");

        for (Object roomToLink : relatedRooms) {
            JSONObject parsedRoomToLink = new JSONObject(roomToLink.toString());

            this.linkARoom(parsedRoom, parsedRoomToLink);
        }
    }

    private void linkARoom(JSONObject mainRoom, JSONObject roomToLink)
    {
        String mainRoomName = mainRoom.getString("roomName");
        String roomToLinkName = roomToLink.getString("roomName");
        String roomToLinkDirection = roomToLink.getString("direction");

        if (this.rooms.containsKey(mainRoomName) == false || this.rooms.containsKey(roomToLinkName) == false) {
            return;
        }
        this.rooms.get(mainRoomName).linkARoom(roomToLinkDirection, this.rooms.get(roomToLinkName));
    }

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

    private void addItems(JSONObject parsedItem, JSONObject parsedRoom)
    {
        String room = parsedRoom.getString("roomName");
        Item item = new Item(parsedItem.getString("name"), parsedItem.getInt("weight"));

        this.rooms.get(room).getInventory().insertItem(item);
    }
}