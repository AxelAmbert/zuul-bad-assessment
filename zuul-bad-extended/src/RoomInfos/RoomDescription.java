package RoomInfos;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.Room;

public class RoomDescription extends VBox
{
  private final Label roomName;
  private final Label roomDesc;

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
