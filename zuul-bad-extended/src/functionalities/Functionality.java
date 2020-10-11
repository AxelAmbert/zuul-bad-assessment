package functionalities;

import java.util.ArrayList;
import main.Game;
import main.Command;

public interface Functionality {
    public static ArrayList<String> functionalities = new ArrayList<>();
    public void run(Game game, Command command);
}
