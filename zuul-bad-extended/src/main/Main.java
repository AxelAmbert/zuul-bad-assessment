package main;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rej
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ThreadTest ok = new ThreadTest();
        Thread t1 = new Thread(ok);
        t1.start();
        new Game().play();
    }
}
