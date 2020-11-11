package implementation;

import communication.CommandLineInterface;
import communication.Controller;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.CommandWords;
import main.Game;
import misc.LocalizedText;
import RoomView.RoomView;

public class GraphicalUserInterfaceView implements GameView
{

  private final Stage primaryStage;
  public GraphicalUserInterfaceView(Stage primaryStageToAttach)
  {
    Game game = Game.getGameInstance();

    CommandWords.update(System.getProperty("user.dir") + System.getProperty("file.separator") + "availableCommands.json");
    this.primaryStage = primaryStageToAttach;
    primaryStage.setTitle("Hello World");
    primaryStage.setScene(new Scene(new RoomView(game.getStartRoom()), 1280, 720));
    primaryStage.setMinHeight(500);
    primaryStage.setMinWidth(500);
    primaryStage.show();
    Controller.setCommunication(new CommandLineInterface());
    this.runGame(game);
  }

  @Override
  public void runGame(Game game)
  {
    LocalizedText.setLocaleTexts(System.getProperty("user.dir") + System.getProperty("file.separator") + "texts.json", "en");
    game.addPlayers(1);


  }
}
