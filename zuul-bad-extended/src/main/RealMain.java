package main;

import communication.CommandLineInterface;
import communication.Controller;
import communication.GUIInterface;
import implementation.GameView;
import misc.LocalizedText;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RealMain implements GameView
{
    private JFrame frame;
    private JPanel rootPanel;
    private JButton goEast;
    private JButton goWest;
    private JButton goNorth;
    private JButton goSouth;
    private JButton talk;
    private CommandInvoker invoker;
    public RealMain()
    {
        Controller.setCommunication(new GUIInterface());
        LocalizedText.setLocaleTexts(System.getProperty("user.dir") + "\\texts.json", "en");
        this.invoker = new CommandInvoker();
        goEast.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Command command = new Command(new String[]{"go", "east"});

                invoker.invoke(command);

            }
        });

        goWest.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Command command = new Command(new String[]{"go", "west"});

                invoker.invoke(command);
            }
        });

        goNorth.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Command command = new Command(new String[]{"go", "north"});

                invoker.invoke(command);
            }
        });

        goSouth.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Command command = new Command(new String[]{"go", "south"});

                invoker.invoke(command);
            }
        });
        talk.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Controller.showMessage("You said: " + Controller.askUser());
            }
        });
    }

    @Override
    public void runGame(Game game)
    {
        this.frame = new JFrame("HelloWorld");
        frame.setContentPane(new RealMain().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
