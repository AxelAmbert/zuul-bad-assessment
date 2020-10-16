package functionalities;

import communication.Controller;
import main.Command;
import misc.LocalizedText;

public class Help extends Functionality
{
    @Override
    public void run(Command command)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(LocalizedText.getText("lost"));
        builder.append(LocalizedText.getText("lost2"));
        builder.append(System.lineSeparator());
        builder.append(LocalizedText.getText("lost3"));
        //TODO change this
        builder.append(LocalizedText.getText("lost4", "FILL"));
        Controller.showMessage(builder);
    }
}
