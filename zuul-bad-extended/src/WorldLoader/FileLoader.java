package WorldLoader;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import misc.CreationOptions;
import misc.Observable;
import misc.Observer;

import java.io.File;
import java.util.ArrayList;

//TODO add default world
public class FileLoader extends MenuBar implements Observable
{
  private  MenuItem loadWorld;
  private  MenuItem defaultWorld;
  private final Menu world;
  private final FileChooser fileChooser;
  private final ArrayList<Observer> observersList;
  private String chosenFile;
  private Stage mainStage;
  private CreationOptions options;

  public FileLoader(Stage mainStage)
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

  private void setupTutorial()
  {

  }

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

  private void setLoadWorldObserver(Stage mainStage)
  {
    this.loadWorld.setOnAction(t ->
    {
      fileChooser.setTitle("Open Resource File");
      File selectedFile = fileChooser.showOpenDialog(mainStage);
      if (selectedFile == null)
        return;
      chosenFile = selectedFile.toString();
      WorldChooserParametersForm form = new WorldChooserParametersForm(mainStage, chosenFile);
      form.addObserver(new Observer(object -> {
        options = (CreationOptions) object;
        options.setFilePath(chosenFile);
        update();
      }));
      form.showDialog();
    });
  }

  private void setupLoadWorld()
  {
    this.loadWorld = new MenuItem("Load new world");
    this.world.getItems().add(this.loadWorld);
    this.setLoadWorldObserver(this.mainStage);
  }

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
