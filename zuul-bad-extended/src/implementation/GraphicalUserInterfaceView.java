package implementation;

import RoomInfos.controller.RoomInfoController;
import RoomInfos.model.RoomInfoModel;
import RoomInfos.view.RoomInfoView;
import WorldLoader.MainMenu;
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
  private CommandInterpreter commandInterpreter;
  private Game game;
  private CommandInvoker invoker;
  private RoomInfoView roomInfos;

  /**
   * Constructor of the GraphicalUserInterfaceView class
   */
  public GraphicalUserInterfaceView(Stage primaryStageToAttach)
  {
    this.primaryStage = primaryStageToAttach;
    this.setupGUIView();
  }


  /**
   * Setup every variables of the GUI view
   */
  private void setupGUIView()
  {
    String defaultFilePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "rooms.json";
    CreationOptions options = new CreationOptions(defaultFilePath, null, false, false);

    CommandWords.update(System.getProperty("user.dir") + System.getProperty("file.separator") + "availableCommands.json");
    this.game = Game.getGameInstance();
    this.game.createRooms(options);
    this.game.addPlayers(3);
    this.commandInterpreter = new CommandInterpreter();
    this.invoker = new CommandInvoker();
    Controller.setCommunication(new GUIInterface());
    this.setupScene();
    this.setupCommandInterpreter();
    this.runGame(this.game);
  }

  /**
   * Setup the scene of the GUI view
   * Setup its menus and main view
   */
  private void setupScene()
  {
    VBox vertical = new VBox();
    MainMenu loader = new MainMenu(this.primaryStage);

    this.roomInfos = this.setupRoomInfoView();
    vertical.setFillWidth(true);
    vertical.getChildren().addAll(loader, this.roomInfos);
    this.setMenuObservers(loader);
    VBox.setVgrow(roomInfos, Priority.ALWAYS);
    this.primaryStage.setTitle("Zuul - GUI");
    this.primaryStage.setScene(new Scene(vertical, 700, 500));
    this.primaryStage.setMinHeight(500);
    this.primaryStage.setMinWidth(500);
    this.primaryStage.show();
  }


  /**
   * Create the MVC of the RoomInfo main view
   */
  private RoomInfoView setupRoomInfoView()
  {
    RoomInfoModel model = new RoomInfoModel(this.commandInterpreter);
    RoomInfoController controller = new RoomInfoController(model, this.game.getStartRoom(), this.game.getActualPlayer());
    RoomInfoView view = new RoomInfoView(controller, model);

    controller.updateController(this.game.getStartRoom(), this.game.getActualPlayer());
    return (view);
  }


  /**
   * Setup the command interpreter that handle commands
   */
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

  /**
   * Setup the observer on the FileLoader to refresh the main view
   * when a new world is loaded.
   * @param loader the FileLoader menu
   */
  private void setMenuObservers(MainMenu loader)
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

  /**
   * Run the game.
   * @param game the game instance.
   */
  @Override
  public void runGame(Game game)
  {
    LocalizedText.setLocaleTexts(System.getProperty("user.dir") + System.getProperty("file.separator") + "texts.json", "en");
  }
}
