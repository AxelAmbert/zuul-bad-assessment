package WorldLoader;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Room;
import misc.CreationOptions;
import misc.Item;
import misc.Observable;
import misc.Observer;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * WorldChooserParametersForm - This class let the user chose between some options
 * while loading a world
 */
public class WorldChooserParametersForm extends Stage implements Observable
{
  private TextField itemName;
  private TextField itemWeight;
  private CheckBox destroyNoExits;
  private CheckBox destroyNoItems;
  private Button confirm;
  private final Stage dialog;
  private final ArrayList<Observer> observersList;
  private CreationOptions options;

  /**
   * Constructor of the WorldChooserParametersForm
   * @param primaryStage main stage of the application
   */
  public WorldChooserParametersForm(Stage primaryStage)
  {
    VBox dialogVbox = new VBox(20);

    this.observersList = new ArrayList<>();
    this.dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(primaryStage);
    this.setupConfirm();
    dialogVbox.getChildren().addAll(this.addItems(), this.destroyEmptyRoom(), this.destroyNoItemsRoom(), this.confirm);
    Scene dialogScene = new Scene(dialogVbox, 500, 200);
    dialog.setScene(dialogScene);
  }

  /**
   * Show the dialog
   */
  public void showDialog()
  {
    this.dialog.show();
  }

  /**
   * Setup add items textfields
   * @return add item VBox
   */
  private VBox addItems()
  {
    VBox addItemBox = new VBox();
    HBox textAndInputs = new HBox();

    this.itemName = new TextField();
    this.itemWeight = new TextField();
    addItemBox.setSpacing(10);
    textAndInputs.getChildren().addAll(new Label("Name: "), this.itemName, new Label(" Weight:"), this.itemWeight);
    addItemBox.getChildren().addAll(new Label("Add an item to every room: "), textAndInputs);

    return (addItemBox);
  }

  /**
   * Setup destroy room without exit checkbox
   * @return destroy empty room HBox
   */
  private HBox destroyEmptyRoom()
  {
    HBox box = new HBox();

    this.destroyNoExits = new CheckBox();
    box.setSpacing(20);
    box.getChildren().addAll(new Label("Remove every room that don't have exits ?"), this.destroyNoExits);
    return (box);
  }

  /**
   * Setup destroy room without items checkbox
   * @return destroy empty room HBox
   */
  private HBox destroyNoItemsRoom()
  {
    HBox box = new HBox();

    this.destroyNoItems = new CheckBox();
    box.setSpacing(20);
    box.getChildren().addAll(new Label("Remove every room that don't have items ?"), this.destroyNoItems);
    return (box);
  }

  /**
   * Setup the confirm button
   */
  private void setupConfirm()
  {
    this.confirm = new Button("Confirm");
    this.confirm.setOnMouseClicked(mouseEvent -> {
      int itemNameLength = itemName.getLength();
      int itemWeightLength = itemWeight.getLength();

      if ((itemNameLength != 0 && itemWeightLength == 0) ||
         (itemNameLength == 0 && itemWeightLength != 0)) {
        this.createAlert("Please fill BOTH name and weight.").showAndWait();
        return;
      } else if (this.testNumerical() == false) {
        this.createAlert("Please set an integer value for the weight.").showAndWait();
        return;
      }
      Item itemToAdd = this.generateItem();
      this.options = new CreationOptions(
              null,
              itemToAdd,
              destroyNoExits.isSelected(),
              destroyNoItems.isSelected());
      dialog.close();
      this.update();
    });
  }

  /**
   * Generate the Item with the given input
   * @return the generated input, or null
   */
  private Item generateItem()
  {
    if (this.itemName.getLength() == 0 || this.itemWeight.getLength() == 0)
      return (null);
    return new Item(this.itemName.getText(), Integer.parseInt(this.itemWeight.getText()));
  }

  /**
   * Create an alert in case of bad input
   * @param text to show
   * @return the created alert
   */
  private Alert createAlert(String text)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Invalid Field");
    alert.setContentText(text);
    return (alert);
  }

  /**
   * Test if an input is numerical
   * @return whether or not the input is numerical
   */
  private boolean testNumerical()
  {
    if (this.itemWeight.getLength() == 0)
      return (true);
    try {
      Integer.parseInt(this.itemWeight.getText());
    } catch (Exception e) {
      return (false);
    }
    return (true);
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
    this.observersList.stream().forEach(observer -> observer.onUpdate(this.options));
  }
}
