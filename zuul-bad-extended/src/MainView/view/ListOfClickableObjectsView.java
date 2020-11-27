package MainView.view;

import MainView.controller.ListOfClickableObjectsController;
import MainView.model.ListOfClickableObjectsModel;
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

/**
 * Class ListOfClickableObjectController - the view of a list of clickable objects.
 * This class is the view of the ListOfClickableObject object
 * which is a list of clickable objects
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class ListOfClickableObjectsView<Box, Iterable> extends ScrollPane
{
  private final ListOfClickableObjectsController<Iterable> controller;
  private final ListOfClickableObjectsModel<Iterable> model;
  private Box box;


  /**
   * Constructor of the ListOfClickableObjectsView class
   * @param constructor constructor of the template Box
   * @param controller related MVC controller
   * @param model related MVC model
   */
  public ListOfClickableObjectsView(Class<? extends Box> constructor,
                                    ListOfClickableObjectsController<Iterable> controller,
                                    ListOfClickableObjectsModel<Iterable> model)
  {
    this.controller = controller;
    this.model = model;
    this.setMaxSize(500, 250);
    this.initInstanceOfBox(constructor);
    this.setModelObserver();
  }

  /**
   * Set the bar policies of the Box ScrollPane
   * @param h bar policy
   * @param v bar policy
   */
  public void setBarPolicy(ScrollBarPolicy h, ScrollBarPolicy v)
  {
    this.hbarPolicyProperty().setValue(h);
    this.vbarPolicyProperty().setValue(v);
  }

  /**
   * Init thanks to reflection the instance of box dynamically
   * @param constructor on which reflect
   */
  private void initInstanceOfBox(Class<? extends Box> constructor)
  {
    try {
      this.box = constructor.getConstructor().newInstance();
    } catch (Exception e) {
      System.err.println("Error on initialisation of Box");
      System.exit(1);
    }
  }

  /**
   * Update the view thanks to infos of the Iterable object
   * @param infos get from the Iterable object
   */
  public void updateView(ArrayList<String[]> infos)
  {
    ((Pane)box).getChildren().clear();
    this.caseEmpty(infos);
    infos.stream().forEach(this::createButton);
    this.setContent((Pane) this.box);
  }

  /**
   * Handle the case where infos is empty, with a default placeholder.
   * @param infos to test if it is empty
   */
  private void caseEmpty(ArrayList<String[]> infos)
  {
    String fileSeparator = System.getProperty("file.separator");
    String userDir = System.getProperty("user.dir");

    if (infos.size() != 0)
      return;
    infos.add(new String[]{userDir + fileSeparator + "images" + fileSeparator + "empty.png", "empty", "empty list"});
  }

  /**
   * Create a button in the list thanks to the useful variables
   * @param usefulVariables used to create the button (contains image, name and description)
   */
  private void createButton(String[] usefulVariables)
  {
    ImageView imageView = this.getImageView(usefulVariables[0]);
    Button button = this.setupButton(imageView, usefulVariables);

    ((Pane)this.box).getChildren().add(button);
  }

  /**
   * Create an image view
   * @param path of the image
   * @return the ImageView created
   */
  private ImageView getImageView(String path)
  {
    ImageView view = null;

    try {
      view = new ImageView(new Image(new FileInputStream(path)));
      view.setFitHeight(50);
      view.setFitWidth(50);
    } catch (Exception exception) {
    }
    return (view);
  }

  /**
   * Create the button with the generated variables
   * @param view image view to apply to the button
   * @param usefulVariables useful variables like the image path, name and description
   * @return the created Button
   */
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

  /**
   * Create the tooltip for the button.
   * @param description to add in the tooltip
   * @return the created Tooltip
   */
  private Tooltip createTooltip(String description)
  {
    Tooltip tip = new Tooltip();

    tip.setText(description);
    tip.setShowDelay(Duration.ZERO);
    return (tip);
  }

  /**
   * Set an observer on the model to know when it has new data.
   * Update the view when triggered.
   */
  private void setModelObserver()
  {
    this.model.addObserver(new Observer(object -> {
      ArrayList<String[]> infos = (ArrayList<String[]>)object;

      this.updateView(infos);
    }));
  }
}
