package implementation;

import FileLoader.FileLoader;
import communication.CommandLineInterface;
import communication.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.*;
import misc.CommandInterpreter;
import misc.LocalizedText;
import RoomView.RoomView;
import misc.Observer;
import misc.OneArgObjectInterface;


public class GraphicalUserInterfaceView implements GameView
{

  private final Stage primaryStage;
  private final CommandInterpreter commandInterpreter;
  private final Game game;
  private final CommandInvoker invoker;
  private RoomView roomView;

  public GraphicalUserInterfaceView(Stage primaryStageToAttach)
  {
    CommandWords.update(System.getProperty("user.dir") + System.getProperty("file.separator") + "availableCommands.json");
    this.game = Game.getGameInstance();
    this.game.createRooms(System.getProperty("user.dir") + System.getProperty("file.separator") + "rooms.json");
    this.game.addPlayers(3);
    this.primaryStage = primaryStageToAttach;
    this.commandInterpreter = new CommandInterpreter();
    this.invoker = new CommandInvoker();
    Controller.setCommunication(new CommandLineInterface());
    this.setupScene();
    this.setupCommandInterpreter();
    this.runGame(this.game);
  }

  private void setupScene()
  {
    VBox vertical = new VBox();
    this.roomView = new RoomView(this.game.getStartRoom(), this.game.getActualPlayer(), commandInterpreter);
    FileLoader loader = new FileLoader(this.primaryStage);


    vertical.setFillWidth(true);
    vertical.getChildren().addAll(loader, roomView);

    this.setMenuObservers(loader);
    VBox.setVgrow(roomView, Priority.ALWAYS);
    primaryStage.setTitle("Zuul - GUI");
    primaryStage.setScene(new Scene(vertical, 700, 500));
    primaryStage.setMinHeight(500);
    primaryStage.setMinWidth(500);
    primaryStage.show();
  }

  private void setupCommandInterpreter()
  {
    commandInterpreter.addObserver(new Observer(new OneArgObjectInterface()
    {
      @Override
      public void run(Object object)
      {
        String[] commandStrings = (String[])object;
        Command command = new Command(commandStrings);

        invoker.invoke(command);
      }
    }));
  }

  private void setMenuObservers(FileLoader loader)
  {
    loader.addObserver(new Observer(new OneArgObjectInterface()
    {
      @Override
      public void run(Object object)
      {
        String path = (String)object;

        System.out.println("OK");
        game.reset(path);
        roomView.updateView(game.getActualPlayer().getCurrentRoom(), game.getActualPlayer());
      }
    }));
  }

  @Override
  public void runGame(Game game)
  {
    LocalizedText.setLocaleTexts(System.getProperty("user.dir") + System.getProperty("file.separator") + "texts.json", "en");

  }
}
