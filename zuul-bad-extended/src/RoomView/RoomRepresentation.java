package RoomView;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RoomRepresentation extends VBox
{
  private Image image;
  private ImageView imageView;

  public RoomRepresentation(String path)
  {
    try {
      System.out.println("mfdr");
      image = new Image(new FileInputStream(path));
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
    imageView = new ImageView(image);
    this.getChildren().add(imageView);
    this.setAlignment(Pos.CENTER);
  }
}
