package WorldLoader;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Tutorial extends Menu
{
  private final MenuItem item;

  public Tutorial(Stage stage)
  {
    super("Tutorial");
    this.item = new MenuItem();
    this.item.setText("Tutorial");
    this.getItems().add(item);
    this.item.setOnAction(actionEvent -> {
      WebView webview = new WebView();
      webview.getEngine().load(
              "https://www.youtube.com/watch?v=EoDCyVIRW7A"
      );
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
