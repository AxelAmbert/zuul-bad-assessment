package main;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import implementation.CommandLineInterfaceImplementation;
import implementation.GameImplementation;

/**
 *
 * @author rej
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = Game.getGameInstance();
        GameImplementation implementation = new CommandLineInterfaceImplementation();

        implementation.runGame(game);
    }
}
