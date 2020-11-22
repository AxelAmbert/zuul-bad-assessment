package implementation;

import WorldLoader.FileLoader;
import communication.CommandLineInterface;
import communication.Controller;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.*;
import misc.*;
import RoomView.RoomView;


public class GraphicalUserInterfaceView implements GameView
{

  private final Stage primaryStage;
  private final CommandInterpreter commandInterpreter;
  private final Game game;
  private final CommandInvoker invoker;
  private RoomView roomView;

  public GraphicalUserInterfaceView(Stage primaryStageToAttach)
  {
    String defaultFilePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "rooms.json";
    CreationOptions options = new CreationOptions(defaultFilePath, null, false, false);

    CommandWords.update(System.getProperty("user.dir") + System.getProperty("file.separator") + "availableCommands.json");
    this.game = Game.getGameInstance();
    this.game.createRooms(options);
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
        CreationOptions options = (CreationOptions)object;

        game.reset(options);
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
