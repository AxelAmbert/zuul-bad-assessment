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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ActionList extends ScrollPane
{
  private VBox buttonBox = new VBox();

  public ActionList()
  {
    ArrayList<CommandInfo> allCommands = CommandWords.getAllCommandInfo();

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
      image = new Image(new FileInputStream(commandInfo.getCommandImagePath()));
      imageView = this.createImageView(image);
      button = this.createButton(imageView, tip);
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

  private Button createButton(ImageView imageView, Tooltip tip)
  {
    Button button = new Button("", imageView);

    button.setMaxSize(50, 50);
    button.setMinSize(50, 50);
    button.setTooltip(tip);
    return (button);
  }

  private Tooltip createTooltip(CommandInfo commandInfo)
  {
    Tooltip tip = new Tooltip(commandInfo.getCommandDescription());

    tip.setShowDelay(Duration.ZERO);
    return (tip);
  }
}

