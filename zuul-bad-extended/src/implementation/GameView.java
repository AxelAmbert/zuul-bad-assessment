package implementation;

import main.Game;

/**
 * View interface for the game.
 * To let the game handles both CLI, GUI, etc..., inherit
 * from this interface and implement your own game
 * @author Axel Ambert
 * @version 1.0
 */
public interface GameView
{
  /**
   * Main function of the program, use it either as
   * your main loop (CLI) or as an init function
   * (GUI).
   * @param game main instance of the game
   */
  void runGame(Game game);
}
