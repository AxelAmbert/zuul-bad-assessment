package RoomView;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.CommandInfo;
import main.CommandWords;
import misc.Observable;
import misc.Observer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ActionList extends ScrollPane implements Observable
{
  private VBox buttonBox = new VBox();
  private ArrayList<Observer> observersList;
  private String chosenCommand;

  public ActionList()
  {
    ArrayList<CommandInfo> allCommands = CommandWords.getAllCommandInfo();

    this.observersList = new ArrayList<>();
    allCommands.stream().forEach(this::addAnAction);
    this.setContent(buttonBox);
  }

  private void addAnAction(CommandInfo commandInfo)
  {
    Button button;
    Image image;
    ImageView imageView;
    Tooltip tip = this.createTooltip(commandInfo);

    try {
      image = new Image(new FileInputStream(commandInfo.getVisualRepresentation()));
      imageView = this.createImageView(image);
      button = this.createButton(commandInfo, imageView, tip);
    } catch (FileNotFoundException e) {
      return;
    }
    buttonBox.getChildren().add(button);
    this.setMaxSize(250, 250);
    this.hbarPolicyProperty().setValue(ScrollBarPolicy.NEVER);
    this.vbarPolicyProperty().setValue(ScrollBarPolicy.ALWAYS);
  }

  private ImageView createImageView(Image image)
  {
    ImageView imageView;

    imageView = new ImageView(image);
    imageView.setFitHeight(50);
    imageView.setFitWidth(50);
    return (imageView);
  }

  private Button createButton(CommandInfo info, ImageView imageView, Tooltip tip)
  {
    Button button = new Button("", imageView);


    button.setOnAction((e) -> {
      this.onButtonClick(info.getCommandName());
    });
    button.setMaxSize(50, 50);
    button.setMinSize(50, 50);
    button.setTooltip(tip);
    return (button);
  }

  private Tooltip createTooltip(CommandInfo commandInfo)
  {
    Tooltip tip = new Tooltip(commandInfo.getDescription());

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

