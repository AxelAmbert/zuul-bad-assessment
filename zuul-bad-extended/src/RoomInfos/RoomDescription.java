package RoomInfos;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.Room;

/**
 * RoomDescription - Graphical description of the room
 * This room show the user the room's name and description
 */
public class RoomDescription extends VBox
{
  private final Label roomName;
  private final Label roomDesc;

  /**
   * Constructor of the RoomDescription class
   * @param room The room that need its description to be created
   */
  public RoomDescription(Room room)
  {
    this.roomName = new Label(room.getName());
    this.roomDesc = new Label(room.getDescription());
    this.roomName.setMaxWidth(Double.MAX_VALUE);
    this.roomName.setAlignment(Pos.CENTER);
    this.roomDesc.setMaxWidth(Double.MAX_VALUE);
    this.roomDesc.setAlignment(Pos.CENTER);
    this.getChildren().addAll(roomName, roomDesc);
  }

}
