package communication;

import java.util.Scanner;

public class CommandLineInterface implements Communication
{
    private Scanner reader;

    public CommandLineInterface()
    {
        this.reader = new Scanner(System.in);
    }

    public void showMessage(String toShow)
    {
        System.out.print(toShow);
    }

    public void showError(String toShow)
    {
        System.err.print(toShow);
    }

    public String askUser()
    {
        return (reader.nextLine());
    }
}
