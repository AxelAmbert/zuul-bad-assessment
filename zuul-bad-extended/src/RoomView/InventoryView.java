package RoomView;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import misc.Inventory;
import misc.Item;
import misc.Observable;
import misc.Observer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

public class InventoryView extends ScrollPane implements Observable
{

  private HBox buttonBox;
  private ArrayList<Observer> observersList;
  private String chosenCommand;

  public InventoryView(Inventory inventory)
  {
    this.updateView(inventory);
    this.observersList = new ArrayList<>();
  }

  public void updateView(Inventory inventory)
  {
    LinkedList<Item> allCommands = inventory.getItems();

    System.out.println("SIZE ? " + allCommands.size());
    buttonBox = new HBox();
    if (allCommands.size() == 0) {
      allCommands.add(new Item("Empty", 0, "images\\empty.png"));
    }
    allCommands.stream().forEach(this::addAnItem);

    this.setContent(buttonBox);
  }

  private void addAnItem(Item item)
  {
    Button button;
    Image image;
    ImageView imageView;
    Tooltip tip = this.createTooltip(item);

    try {
      if (item.getVisualRepresentation() != null) {
        image = new Image(new FileInputStream(item.getVisualRepresentation()));

      } else {
        image = new Image(new FileInputStream("images\\default_placeholders\\item.png"));
      }

      imageView = this.createImageView(image);
      button = this.createButton(item, imageView, tip);
    } catch (FileNotFoundException e) {
      return;
    }
    buttonBox.getChildren().add(button);
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

  private Button createButton(Item item, ImageView imageView, Tooltip tip)
  {
    Button button = new Button("", imageView);


    button.setOnAction((e) -> {
      this.onButtonClick(item.getName());
    });
    button.setMaxSize(50, 50);
    button.setMinSize(50, 50);
    button.setTooltip(tip);
    return (button);
  }

  private Tooltip createTooltip(Item item)
  {
    Tooltip tip = new Tooltip("a " + item.getName() + " " + item.getWeight() + "kg");

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
    this.observersList.stream().forEach((observer -> observer.onUpdate(this.chosenCommand)));
  }
}
