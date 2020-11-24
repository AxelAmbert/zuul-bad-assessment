package RoomInfos;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.util.Objects;

public class RoomRepresentation extends VBox
{
  private Image image;
  private ImageView imageView;

  public RoomRepresentation(String path)
  {
    image = this.getImageRepresentation(path);
    imageView = new ImageView(image);
    imageView.setFitHeight(300);
    imageView.setFitWidth(450);
    this.getChildren().add(imageView);
    this.setAlignment(Pos.CENTER);
  }

  private Image getImageRepresentation(String path)
  {
    Image image = null;

    try {
      image = new Image(new FileInputStream(Objects.requireNonNullElse(path, "images\\default_placeholders\\door.png")));
    } catch (Exception e) {
      System.out.println("Error at loading image " + Objects.requireNonNullElse(path, "null"));
    }
    return (image);
  }
}
