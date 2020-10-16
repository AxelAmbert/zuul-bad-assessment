package functionalities;

import communication.Controller;
import main.Command;
import main.CommandWords;
import misc.LocalizedText;

public class Help extends Functionality
{
    @Override
    public void run(Command command)
    {
        StringBuilder builder = new StringBuilder();
        CommandWords commandWords = new CommandWords(System.getProperty("user.dir") + "\\availableCommands");

        builder.append(LocalizedText.getText("lost"));
        builder.append(LocalizedText.getText("lost2"));
        builder.append(System.lineSeparator());
        builder.append(LocalizedText.getText("lost3"));
        builder.append(LocalizedText.getText("lost4", commandWords.getCommandString()));
        Controller.showMessage(builder);
    }
}
