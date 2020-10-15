package functionalities;

import communication.Controller;
import main.Command;

public class Help implements Functionality
{
    @Override
    public void run(Command command)
    {
        StringBuilder builder = new StringBuilder();

        builder.append("You are lost. You are alone. You wander").append(System.lineSeparator());
        builder.append("around at the university.").append(System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append("Your command words are:").append(System.lineSeparator());
        builder.append("   go quit help").append(System.lineSeparator());
        Controller.getInstance().showMessage(builder);
    }
}
