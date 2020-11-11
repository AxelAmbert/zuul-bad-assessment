package RoomView;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.Room;



public class RoomView extends BorderPane
{
  private Room attachedRoom;

  public RoomView(Room room)
  {
    ActionList list = new ActionList();
    this.attachedRoom = room;
    this.setTop(new RoomDescription(room.getDescription()));
    this.setCenter(new RoomRepresentation("C:\\Users\\ImPar\\OneDrive\\Documents\\test\\zuul-bad-assessment\\zuul-bad-extended\\images\\room.jpg"));
    this.setLeft(list);
    BorderPane.setAlignment(list, Pos.CENTER_RIGHT);
  }

  public void update(Room room)
  {

  }



}
