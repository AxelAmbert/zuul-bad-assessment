package WorldLoader;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import misc.CreationOptions;
import misc.Observable;
import misc.Observer;

import java.io.File;
import java.util.ArrayList;


/**
 * MainMenu - This class handle the MenuBar on top of the game
 * It contains:
 * A file loader to choose a new world
 * A default world loader option
 * A tutorial
 */
public class MainMenu extends MenuBar implements Observable
{
  private  MenuItem loadWorld;
  private  MenuItem defaultWorld;
  private final Menu world;
  private final FileChooser fileChooser;
  private final ArrayList<Observer> observersList;
  private String chosenFile;
  private Stage mainStage;
  private CreationOptions options;

  /**
   * Constructor of the MainMenu class
   * @param mainStage main Stage of the application
   */
  public MainMenu(Stage mainStage)
  {
    this.mainStage = mainStage;
    this.chosenFile = "";
    this.observersList = new ArrayList<>();
    this.world = new Menu("World Loader");
    this.setupLoadWorld();
    this.setupLoadDefault();
    this.fileChooser = this.createFileChooser();
    this.getMenus().addAll(this.world, new Tutorial(mainStage));
  }


  /**
   * Create the FileChooser, with *., *.json, *.txt options
   * @return the file chooser
   */
  private FileChooser createFileChooser()
  {
    FileChooser chooser = new FileChooser();

    chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Json Files", "*.json"),
            new FileChooser.ExtensionFilter("Text Files", "*.txt"),
            new FileChooser.ExtensionFilter("All Files", "*.*"));
    chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    return (chooser);
  }

  /**
   * Set observers on the File chooser
   * @param mainStage main Stage of the application
   */
  private void setLoadWorldObserver(Stage mainStage)
  {
    this.loadWorld.setOnAction(t ->
    {
      fileChooser.setTitle("Open Resource File");
      File selectedFile = fileChooser.showOpenDialog(mainStage);
      if (selectedFile == null)
        return;
      chosenFile = selectedFile.toString();
      WorldChooserParametersForm form = new WorldChooserParametersForm(mainStage);
      form.addObserver(new Observer(object -> {
        options = (CreationOptions) object;
        options.setFilePath(chosenFile);
        update();
      }));
      form.showDialog();
    });
  }

  /**
   * Setup the load world button
   */
  private void setupLoadWorld()
  {
    this.loadWorld = new MenuItem("Load new world");
    this.world.getItems().add(this.loadWorld);
    this.setLoadWorldObserver(this.mainStage);
  }

  /**
   * Setup the load default world button
   * and update to trigger the observers.
   */
  private void setupLoadDefault()
  {
    this.defaultWorld = new MenuItem("Load default world");
    this.defaultWorld.setOnAction(t -> {
      String userDir = System.getProperty("user.dir");
      String fileSeparator = System.getProperty("file.separator");
      String filePath = userDir + fileSeparator + "rooms.json";

      this.options = new CreationOptions(filePath, null, false, false);
      this.update();
    });
    this.world.getItems().add(this.defaultWorld);
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
    this.observersList.stream().forEach(observer -> observer.onUpdate(options));
  }
}
