package main;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import implementation.GraphicalUserInterfaceView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import communication.Controller;
import implementation.CommandLineInterfaceView;
import implementation.GameView;
import misc.LocalizedText;

/**
 * Main class of the game.
 *
 * @author Axel Ambert
 * @version 1.0
 */
public class Main extends Application
{

  /**
   * Start the Game.
   * Feel free to change the GameView to use e.g a GUI
   */
/*  public static void main(String[] args)
  {
    Game game = Game.getGameInstance();
    GameView implementation = new CommandLineInterfaceView();

    implementation.runGame(game);
  }*/

  public void start(Stage primaryStage) throws Exception
  {
    Game game = Game.getGameInstance();
    GameView implementation = new GraphicalUserInterfaceView(primaryStage);

    implementation.runGame(game);
  }


  public static void main(String[] args)
  {
    launch(args);
  }
}
