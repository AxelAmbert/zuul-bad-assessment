package implementation;

import RoomInfos.controller.RoomInfoController;
import RoomInfos.model.RoomInfoModel;
import RoomInfos.view.RoomInfoView;
import WorldLoader.FileLoader;
import communication.CommandLineInterface;
import communication.Controller;
import communication.GUIInterface;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.*;
import misc.*;



public class GraphicalUserInterfaceView implements GameView
{

  private final Stage primaryStage;
  private final CommandInterpreter commandInterpreter;
  private final Game game;
  private final CommandInvoker invoker;
  private RoomInfoView roomInfos;

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
    Controller.setCommunication(new GUIInterface());
    this.setupScene();
    this.setupCommandInterpreter();
    this.runGame(this.game);
  }

  private void setupScene()
  {
    VBox vertical = new VBox();
    //this.roomInfos = new RoomInfos(this.game.getStartRoom(), this.game.getActualPlayer(), commandInterpreter);
    this.roomInfos = this.setupRoomInfoView();
    FileLoader loader = new FileLoader(this.primaryStage);
    vertical.setFillWidth(true);
    vertical.getChildren().addAll(loader, this.roomInfos);

    this.setMenuObservers(loader);
    VBox.setVgrow(roomInfos, Priority.ALWAYS);
    primaryStage.setTitle("Zuul - GUI");
    primaryStage.setScene(new Scene(vertical, 700, 500));
    primaryStage.setMinHeight(500);
    primaryStage.setMinWidth(500);
    primaryStage.show();
  }

  private RoomInfoView setupRoomInfoView()
  {
    RoomInfoModel model = new RoomInfoModel(this.commandInterpreter);
    RoomInfoController controller = new RoomInfoController(model, this.game.getStartRoom(), this.game.getActualPlayer());
    RoomInfoView view = new RoomInfoView(controller, model);

    controller.updateController(this.game.getStartRoom(), this.game.getActualPlayer());
    return (view);
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
        roomInfos.getController().updateController(game.getActualPlayer().getCurrentRoom(), game.getActualPlayer());
      }
    }));
  }

  @Override
  public void runGame(Game game)
  {
    LocalizedText.setLocaleTexts(System.getProperty("user.dir") + System.getProperty("file.separator") + "texts.json", "en");
  }
}
