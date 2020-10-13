package player;

import main.Command;
import main.Parser;
import main.Room;

public class HumanPlayer extends Player
{
    private Parser parser;
    private final int MAX_WEIGHT = 10;

    public HumanPlayer(Room currentRoom)
    {
        super(currentRoom);
        this.parser = new Parser();
        this.inventory.setMaxWeight(this.MAX_WEIGHT);
    }

    @Override public Command play()

    {
        return (this.parser.getCommand());
    }
}
