package communication;

import java.util.Scanner;

public class CommandLineInterface implements Communication
{
    private Scanner reader;

    public CommandLineInterface()
    { }

    public void showMessage(String toShow)
    {
        System.out.println(toShow);
    }

    public void showError(String toShow)
    {
        System.err.println(toShow);
    }

    public String askUser()
    {
        return (reader.nextLine());
    }
}
