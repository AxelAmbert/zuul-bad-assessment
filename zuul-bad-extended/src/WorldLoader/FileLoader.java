package WorldLoader;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class FileLoader extends MenuBar implements Observable
{
  private final MenuItem loadWorld;
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
    this.loadWorld = new MenuItem("Load new world");
    this.world = new Menu("World");
    this.fileChooser = this.createFileChooser();

    this.setClickObserver(this.mainStage);
    this.world.getItems().add(this.loadWorld);
    this.getMenus().add(this.world);
  }

  private FileChooser createFileChooser()
  {
    FileChooser chooser = new FileChooser();

    chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"),
            new FileChooser.ExtensionFilter("Json Files", "*.json"),
            new FileChooser.ExtensionFilter("All Files", "*.*"));
    chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    return (chooser);
  }

  private void setClickObserver(Stage mainStage)
  {
    this.loadWorld.setOnAction(new EventHandler<ActionEvent>()
    {
      public void handle(ActionEvent t)
      {
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
          chosenFile = selectedFile.toString();
          WorldChooserParametersForm form = new WorldChooserParametersForm(mainStage, chosenFile);

          form.addObserver(new Observer(object -> {
            options = (CreationOptions) object;

            options.setFilePath(chosenFile);
            update();
          }));
          form.showDialog();
        }
      }
    });
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
