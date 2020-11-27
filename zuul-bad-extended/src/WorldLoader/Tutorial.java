package WorldLoader;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Tutorial - this class let the user see a little YouTube tutorial
 * in a WebView to understand how to play the game.
 */
public class Tutorial extends Menu
{
  private final MenuItem item;

  /**
   * Constructor of the Tutorial class
   * The tutorial class is a WebView that opens a YouTube video
   * To help the user play the game
   * @param stage main stage to bind
   */
  public Tutorial(Stage stage)
  {
    super("Tutorial");
    this.item = new MenuItem();
    this.item.setText("Tutorial");
    this.getItems().add(item);
    this.item.setOnAction(actionEvent -> {
      WebView webview = new WebView();
      webview.getEngine().load("https://www.youtube.com/watch?v=EoDCyVIRW7A");
      webview.setPrefSize(640, 500);
      Stage newStage = new Stage();
      newStage.setScene(new Scene(webview));
      newStage.setOnHiding(e -> {
        webview.getEngine().load(null);
      });
      newStage.show();
    });
  }
}
