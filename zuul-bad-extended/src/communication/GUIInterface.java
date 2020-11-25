package communication;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javax.swing.*;


public class GUIInterface implements Communication
{
  public GUIInterface()
  {
  }


  /**
   * Show an Alert of Information to the user.
   * @param toShow the message to print
   */
  @Override
  public void showMessage(String toShow)
  {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    alert.setTitle("Information");
    alert.setContentText(toShow);
    alert.showAndWait();
  }


  /**
   * Show an Alert of Error to the user.
   * @param toShow the message to print
   */
  @Override
  public void showError(String toShow)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR);

    alert.setTitle("Error");
    alert.setContentText(toShow);
    alert.showAndWait();
  }

  /**
   * Ask for the user's input
   * @return the user's input
   */
  @Override
  public String askUser()
  {
    return "";
  }
}
