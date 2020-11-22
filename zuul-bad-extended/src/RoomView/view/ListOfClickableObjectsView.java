package RoomView.view;

import RoomView.controller.ListOfClickableObjectsController;
import RoomView.model.ListOfClickableObjectsModel;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import misc.Observer;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ListOfClickableObjectsView<Box, Iterable>   extends ScrollPane
{
  ListOfClickableObjectsController<Iterable> controller;
  ListOfClickableObjectsModel<Iterable> model;
  Box box;

  public ListOfClickableObjectsView(Class<? extends Box> constructor,
                                    ListOfClickableObjectsController<Iterable> controller,
                                    ListOfClickableObjectsModel<Iterable> model)
  {
    this.controller = controller;
    this.model = model;
    this.setMaxSize(250, 250);
    this.hbarPolicyProperty().setValue(ScrollBarPolicy.NEVER);
    this.vbarPolicyProperty().setValue(ScrollBarPolicy.ALWAYS);
    this.initInstanceOfBox(constructor);
    this.setModelObserver();
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

  public void updateView(ArrayList<String[]> infos)
  {
    ((Pane)box).getChildren().clear();
    infos.stream().forEach(this::createButton);
    this.setContent((Pane) this.box);
  }

  private void createButton(String[] usefulVariables)
  {
    ImageView imageView = this.getImageView(usefulVariables[0]);
    Button button = this.setupButton(imageView, usefulVariables);

    ((Pane)this.box).getChildren().add(button);
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
      this.controller.setStoredValue(usefulVariables[1]);
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

  private void setModelObserver()
  {
    this.model.addObserver(new Observer(object -> {
      ArrayList<String[]> infos = (ArrayList<String[]>)object;

      this.updateView(infos);
    }));
  }
}
