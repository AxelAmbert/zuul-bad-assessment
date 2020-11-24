package RoomInfos.view;

import RoomInfos.RoomDescription;
import RoomInfos.RoomRepresentation;
import RoomInfos.controller.RoomInfoController;
import RoomInfos.model.RoomInfoModel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import misc.Observer;

import java.util.HashMap;

public class RoomInfoView extends BorderPane
{
  private final RoomInfoController controller;
  private final RoomInfoModel model;

  private RoomRepresentation roomRepresentation;
  private RoomDescription roomDescription;

  private HBox downView;
  private VBox leftView;
  private VBox rightView;

  private HashMap<String, ListOfClickableObjectsView> views;


  public RoomInfoView(RoomInfoController controller, RoomInfoModel model)
  {
    this.controller = controller;
    this.model = model;
    this.setUpdateObserver();
    this.downView = new HBox();
    this.roomRepresentation = new RoomRepresentation(this.controller.getBindedRoom().getVisualRepresentation());
    this.roomDescription = new RoomDescription(this.controller.getBindedRoom());
    this.downView.setSpacing(10);
  }

  private void setHgrow(VBox[] boxes)
  {
    for (VBox box : boxes) {
      HBox.setHgrow(box, Priority.ALWAYS);
    }
  }

  private void setInventoriesView()
  {
    Label[] names = new Label[]{new Label("Player's inventory"), new Label("Room's inventory"), new Label("Available Exits")};
    ListOfClickableObjectsView[] views = new ListOfClickableObjectsView[]{this.views.get("playerInventoryList"), this.views.get("roomInventoryList"), this.views.get("exitList")};
    VBox[] boxes = new VBox[3];

    this.downView.getChildren().clear();
    for (int i = 0; i < names.length; i++) {
      boxes[i] = new VBox();
      boxes[i].getChildren().addAll(names[i], views[i]);
      this.downView.getChildren().add(boxes[i]);
    }

    this.setHgrow(boxes);
  }

  private void setBorderPane()
  {
    this.setTop(this.roomDescription);
    this.setCenter(this.roomRepresentation);
    this.setLeft(this.leftView);
    this.setBottom(this.downView);
    this.setRight(this.rightView);
    BorderPane.setAlignment(this.leftView, Pos.CENTER_RIGHT);
    BorderPane.setAlignment(this.rightView, Pos.CENTER_RIGHT);
    VBox.setVgrow(this.leftView, Priority.ALWAYS);
    VBox.setVgrow(this.rightView, Priority.ALWAYS);
  }

  public void updateView(HashMap<String, ListOfClickableObjectsView> viewList)
  {
    this.views = viewList;
    this.roomRepresentation = new RoomRepresentation(this.controller.getBindedRoom().getVisualRepresentation());
    this.roomDescription = new RoomDescription(this.controller.getBindedRoom());
    this.setInventoriesView();
    this.setTextToList();
    this.setBorderPane();
    this.setBarPolicy();
  }

  private void setUpdateObserver()
  {
    this.model.addObserver(new Observer(object -> {
      HashMap<String, ListOfClickableObjectsView> map = (HashMap<String, ListOfClickableObjectsView>)object;

      updateView(map);
    }));
  }

  private void setBarPolicy()
  {
    this.views.get("playerInventoryList").setBarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED, ScrollPane.ScrollBarPolicy.AS_NEEDED);
    this.views.get("roomInventoryList").setBarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED, ScrollPane.ScrollBarPolicy.AS_NEEDED);
    this.views.get("exitList").setBarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED, ScrollPane.ScrollBarPolicy.AS_NEEDED);
    this.views.get("actionList").setBarPolicy(ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.ALWAYS);
    this.views.get("playerList").setBarPolicy(ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.ALWAYS);
  }

  private void setTextToList()
  {

    Label[] names = new Label[]{new Label("Actions"), new Label("Players")};
    VBox[] boxes = new VBox[2];
    ListOfClickableObjectsView[] views = new ListOfClickableObjectsView[]{this.views.get("actionList"), this.views.get("playerList"), this.views.get("exitList")};

    for (int i = 0; i < names.length; i++) {
      boxes[i] = new VBox();
      boxes[i].getChildren().addAll(names[i], views[i]);
      boxes[i].setSpacing(3);
      boxes[i].setMaxSize(500, 250);
    }
    this.leftView = boxes[0];
    this.rightView = boxes[1];

  }

  public RoomInfoController getController()
  {
    return controller;
  }

  public RoomInfoModel getModel()
  {
    return model;
  }
}
