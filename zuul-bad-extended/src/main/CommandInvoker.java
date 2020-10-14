package main;

import functionalities.Functionality;

import java.lang.reflect.Constructor;

public class CommandInvoker
{
    CommandWords availableCommands;

    public CommandInvoker()
    {
        this.availableCommands = new CommandWords();
    }

    public void invoke(Command command)
    {
        String commandName = this.adaptCaseSensitive(command.getCommandName());

        try {
            Class<?> cls = Class.forName("functionalities." + commandName);
            Constructor ct = cls.getConstructor();
            Functionality fun = (Functionality) ct.newInstance();

            fun.run(command);
        } catch (Throwable e) {
            System.err.println(command.getCommandName() + " is not a valid command " + e.toString());
        }
    }

    private String adaptCaseSensitive(String command)
    {
        StringBuilder builder = new StringBuilder(command);

        if (builder.charAt(0) >= 65 && builder.charAt(0) <= 90) {
            builder.setCharAt(0, (char) (builder.charAt(0) + 32));
        }
        return (builder.toString());
    }
}
