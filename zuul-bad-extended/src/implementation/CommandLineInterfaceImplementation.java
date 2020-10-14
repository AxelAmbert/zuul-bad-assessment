package implementation;

import communication.CommandLineInterface;
import communication.Communication;
import communication.UserCommunication;
import main.Game;
import player.Player;

import java.util.ArrayList;

public class CommandLineInterfaceImplementation implements GameImplementation
{

    public CommandLineInterfaceImplementation() { }

    @Override
    public void runGame(Game game)
    {
        ArrayList<Player> playerList = game.getPlayerList();
        UserCommunication userCommunication = UserCommunication.getInstance();

        userCommunication.setCommunication(new CommandLineInterface());
        game.printWelcome();
        while (playerList.size() > 0) {
            String userCommand = userCommunication.askUser();
            userCommunication.showMessage(userCommand + " he said ");
        }
    }
}
