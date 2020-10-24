package main;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import implementation.CommandLineInterfaceView;
import implementation.GameView;

/**
 * @author rej
 */
public class Main
{

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    Game game = Game.getGameInstance();
    GameView implementation = new CommandLineInterfaceView();

    implementation.runGame(game);
  }
}
