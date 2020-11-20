package RoomView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.CommandInfo;
import main.CommandWords;
import main.Room;
import misc.Observable;
import misc.Observer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class RoomListView extends ScrollPane implements Observable
{
  private HBox buttonBox = new HBox();
  private ArrayList<Observer> observersList;
  private String chosenCommand;

  public RoomListView(Room room)
  {
    HashMap<String, Room> everyRoom = room.getExits();

    this.observersList = new ArrayList<>();
    everyRoom.forEach(this::addAnExit);
    this.setContent(buttonBox);
  }

  private void addAnExit(String direction, Room exit)
  {
    Button button;
    Image image;
    ImageView imageView;
    Tooltip tip = this.createTooltip(direction + ": " + exit.getName());

    System.out.println("J'ajoute " + direction + " :" + exit.getName());
    try {
      if (exit.getVisualRepresentation() != null) {
        image = new Image(new FileInputStream(exit.getVisualRepresentation()));
      } else {
        image = new Image(new FileInputStream("images\\default_placeholders\\door.png"));
      }
      imageView = this.createImageView(image);
      button = this.createButton(direction, imageView, tip);
    } catch (FileNotFoundException e) {
      return;
    }
    buttonBox.getChildren().add(button);
    this.setMaxSize(250, 250);
    this.hbarPolicyProperty().setValue(ScrollBarPolicy.AS_NEEDED);
    this.vbarPolicyProperty().setValue(ScrollBarPolicy.NEVER);
  }

  private ImageView createImageView(Image image)
  {
    ImageView imageView;

    imageView = new ImageView(image);
    imageView.setFitHeight(50);
    imageView.setFitWidth(50);
    return (imageView);
  }

  private Button createButton(String direction, ImageView imageView, Tooltip tip)
  {
    Button button = new Button("", imageView);

    button.setOnDragDetected(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        /* drag was detected, start a drag-and-drop gesture*/
        /* allow any transfer mode */
        Dragboard db = button.startDragAndDrop(TransferMode.ANY);

        /* Put a string on a dragboard */
        ClipboardContent content = new ClipboardContent();
        content.putString("source.getText()");
        db.setContent(content);

        event.consume();
      }
    });

    button.setOnDragOver(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        /* data is dragged over the target */
        /* accept it only if it is not dragged from the same node
         * and if it has a string data */
        if (event.getGestureSource() != button &&
                event.getDragboard().hasString()) {
          /* allow for both copying and moving, whatever user chooses */
          event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
      }
    });

    button.setOnDragEntered(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        /* the drag-and-drop gesture entered the target */
        /* show to the user that it is an actual gesture target */
        if (event.getGestureSource() != button &&
                event.getDragboard().hasString()) {

        }

        event.consume();
      }
    });

    button.setOnDragDropped(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        /* data dropped */
        /* if there is a string data on dragboard, read it and use it */
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
          button.setText(db.getString());
          success = true;
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        event.setDropCompleted(success);

        event.consume();
      }
    });
    button.setOnAction((e) -> {
      this.onButtonClick(direction);
    });
    button.setMaxSize(50, 50);
    button.setMinSize(50, 50);
    button.setTooltip(tip);
    return (button);
  }

  private Tooltip createTooltip(String exitInfo)
  {
    Tooltip tip = new Tooltip(exitInfo);

    tip.setShowDelay(Duration.ZERO);
    return (tip);
  }

  private void onButtonClick(String value)
  {
    this.chosenCommand = value;
    this.update();
  }

  @Override
  public void addObserver(Observer observerToAdd)
  {
    this.observersList.add(observerToAdd);
  }

  @Override
  public void removeObserver(Observer observerToRemove)
  {
    this.observersList.remove(observerToRemove);
  }

  @Override
  public void update()
  {
    System.out.println(this.chosenCommand);
    this.observersList.stream().forEach((observer -> observer.onUpdate(this.chosenCommand)));
  }
}

