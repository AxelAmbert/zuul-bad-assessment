package main;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class Main
{

  /**
   * Start the Game.
   * Feel free to change the GameView to use e.g a GUI
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    Game game = Game.getGameInstance();
    GameView implementation = new CommandLineInterfaceView();

    implementation.runGame(game);
  }
}
