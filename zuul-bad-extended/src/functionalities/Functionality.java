package functionalities;

import communication.Controller;
import main.Command;
import misc.LocalizedText;

public abstract class Functionality {
    public void run(Command command)
    {

    }

    public boolean evaluateArgs(Command command, int wantedArgs, String key)
    {
        if (command.getNumberOfArgs() < wantedArgs) {
            Controller.showMessage(LocalizedText.getText(key));
            return (false);
        }
        return (true);
    }
}
