package RoomView;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import misc.Observable;
import misc.Observer;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ListOfClickableObjects<Box, Iterable>  extends ScrollPane implements Observable
{
  private Box box;
  private String storedValue;
  ArrayList<Observer> observersList;

  public ListOfClickableObjects(Class<? extends Box> constructor,
                                Stream<Iterable> stream,
                                Predicate<? super Iterable> filter)
  {
    this.storedValue = "";
    this.observersList = new ArrayList<>();
    this.setMaxSize(250, 250);
    this.hbarPolicyProperty().setValue(ScrollBarPolicy.NEVER);
    this.vbarPolicyProperty().setValue(ScrollBarPolicy.ALWAYS);
    this.initInstanceOfBox(constructor);
    stream.filter(filter).forEach(this::createButton);
    this.setContent((Node) this.box);
  }

  public ListOfClickableObjects(Class<? extends Box> constructor,
                                Stream<Iterable> stream)
  {
    this.storedValue = "";
    this.observersList = new ArrayList<>();
    this.setMaxSize(250, 250);
    this.hbarPolicyProperty().setValue(ScrollBarPolicy.NEVER);
    this.vbarPolicyProperty().setValue(ScrollBarPolicy.ALWAYS);
    this.initInstanceOfBox(constructor);
    this.updateView(stream);
  }

  private void initInstanceOfBox(Class<? extends Box> constructor)
  {
    try {
      this.box = constructor.getConstructor().newInstance();
    } catch (Exception e) {
      System.out.println("Error while initialisation of Box");
      System.exit(1);
    }
  }

  public void updateView(Stream<Iterable> stream)
  {
    ((Pane)box).getChildren().clear();
    stream.forEach(this::createButton);
    this.setContent((Pane) this.box);
  }
  
  private void createButton(Iterable toIter)
  {
    System.out.println("go!");
    String[] usefulVariables = this.getUsefulVariables(toIter);
    ImageView imageView = this.getImageView(usefulVariables[0]);
    Button button = this.setupButton(imageView, usefulVariables);

    ((Pane)this.box).getChildren().add(button);
  }

  private String[] getUsefulVariables(Iterable toIter)
  {
    String visualRepresentation = null;
    String name = null;
    String description = null;

    try {
      visualRepresentation = (String)toIter.getClass().getMethod("getVisualRepresentation").invoke(toIter);
      name = (String)toIter.getClass().getMethod("getName").invoke(toIter);
      description = (String)toIter.getClass().getMethod("getDescription").invoke(toIter);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    return (new String[]{visualRepresentation, name, description});
  }

  private ImageView getImageView(String path)
  {
    ImageView view = null;

    try {
      view = new ImageView(new Image(new FileInputStream(path)));
      view.setFitHeight(50);
      view.setFitWidth(50);
    } catch (Exception exception) {
      System.out.println("image not found");
    }
    return (view);
  }

  private Button setupButton(ImageView view, String[] usefulVariables)
  {
    Button button = new Button("", view);

    button.setMaxSize(50, 50);
    button.setMinSize(50, 50);
    button.setTooltip(this.createTooltip(usefulVariables[2]));
    button.setOnMouseClicked(mouseEvent -> {
      this.storedValue = usefulVariables[1];
      this.update();
    });
    return (button);
  }


  private Tooltip createTooltip(String description)
  {
    Tooltip tip = new Tooltip();

    tip.setText(description);
    tip.setShowDelay(Duration.ZERO);
    return (tip);
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
    this.observersList.stream().forEach(observer -> observer.onUpdate(this.storedValue));
  }
}