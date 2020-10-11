import java.util.ArrayList;

public interface Functionality {
    public static ArrayList<String> functionalities = new ArrayList<>();
    public void run(Game game, Command command);
}
