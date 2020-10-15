package main;

public class CommandParser
{
    public CommandParser()
    {

    }

    public Command parse(String toParse, String delimiters)
    {
        String[] parsedCommand = toParse.split(delimiters);

        return (new Command(parsedCommand));
    }
}
