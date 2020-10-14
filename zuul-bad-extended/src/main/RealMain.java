package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RealMain
{
    private JPanel rootPanel;
    private JButton mdrButton;
    public RealMain()
    {
        System.out.println("hihi");
        mdrButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Game.getGameInstance().printOK();
            }
        });
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("HelloWorld");
        frame.setContentPane(new RealMain().rootPanel);
        System.out.println("oo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        Game.getGameInstance().play();
    }
}
