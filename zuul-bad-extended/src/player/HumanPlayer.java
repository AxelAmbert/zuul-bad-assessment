package player;

import communication.Controller;
import main.Room;

public class HumanPlayer extends Player
{
    private final int MAX_WEIGHT = 10;

    public HumanPlayer(Room currentRoom, String playerName)
    {
        super(currentRoom);
        this.inventory.setMaxWeight(this.MAX_WEIGHT);
        this.playerName = playerName;
    }

    @Override public String play()

    {
        Controller controller = Controller.getInstance();

        return (controller.askUser());
    }
}
