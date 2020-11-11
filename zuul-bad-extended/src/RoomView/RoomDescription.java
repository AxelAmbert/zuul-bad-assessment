package RoomView;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RoomDescription extends VBox
{
  private Label roomName;
  private Label roomDesc;


  public RoomDescription(String description)
  {
    this.roomName = new Label("The room");
    this.roomDesc = new Label(description);
    this.roomName.setMaxWidth(Double.MAX_VALUE);
    this.roomName.setAlignment(Pos.CENTER);
    this.roomDesc.setMaxWidth(Double.MAX_VALUE);
    this.roomDesc.setAlignment(Pos.CENTER);
    this.getChildren().addAll(roomName, roomDesc);
  }

}
