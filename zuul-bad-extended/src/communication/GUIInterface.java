package communication;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javax.swing.*;


public class GUIInterface implements Communication
{
  public GUIInterface()
  {
  }

  @Override
  public void showMessage(String toShow)
  {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    alert.setTitle("Information");
    alert.setContentText(toShow);
    alert.showAndWait();
  }

  @Override
  public void showError(String toShow)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR);

    alert.setTitle("Error");
    alert.setContentText(toShow);
    alert.showAndWait();
  }

  @Override
  public String askUser()
  {
    return "";
  }
}
