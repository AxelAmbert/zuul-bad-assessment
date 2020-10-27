package main;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import implementation.CommandLineInterfaceView;
import implementation.GameView;
import misc.LocalizedText;

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

    LocalizedText.setLocaleTexts(System.getProperty("user.dir") + System.getProperty("file.separator") + "texts.json", "en");
    implementation.runGame(game);
  }
}
