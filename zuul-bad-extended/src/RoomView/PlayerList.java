package RoomView;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.Game;
import main.Room;
import misc.Observable;
import misc.Observer;
import player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

public class PlayerList  extends ScrollPane implements Observable
{

  private final VBox buttonBox = new VBox();
  private ArrayList<Observer> observersList;
  private String choosenPlayer;

  public PlayerList(Room room)
  {
    LinkedList<Player> allPlayers = room.getPlayerList();
    Player actualPlayer = Game.getGameInstance().getActualPlayer();

    this.observersList = new ArrayList<>();
    allPlayers.stream()
            .filter(player -> player.getName().equals(actualPlayer.getName()) == false)
            .forEach(this::addAPlayer);
    this.setContent(buttonBox);
  }

  private void addAPlayer(Player player)
  {
    Button button;
    Image image;
    ImageView imageView;
    Tooltip tip = this.createTooltip(player);

    try {
      image = new Image(new FileInputStream("images\\door.png"));
      imageView = this.createImageView(image);
      button = this.createButton(player, imageView, tip);
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

  private Button createButton(Player player, ImageView imageView, Tooltip tip)
  {
    Button button = new Button("", imageView);

    button.setOnAction((e) -> {
      this.onButtonClick(player.getName());
    });
    button.setMaxSize(50, 50);
    button.setMinSize(50, 50);
    button.setTooltip(tip);
    return (button);
  }

  private Tooltip createTooltip(Player player)
  {
    Tooltip tip = new Tooltip(player.getName());

    tip.setShowDelay(Duration.ZERO);
    return (tip);
  }

  private void onButtonClick(String value)
  {
    this.choosenPlayer = value;
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
    this.observersList.stream().forEach((observer -> observer.onUpdate(this.choosenPlayer)));
  }

}
