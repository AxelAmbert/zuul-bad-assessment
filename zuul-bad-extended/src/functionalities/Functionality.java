package functionalities;

import java.util.ArrayList;

import main.Command;
import player.Player;

public interface Functionality {
    public static ArrayList<String> functionalities = new ArrayList<>();
    public void run(Player player, Command command);
}
