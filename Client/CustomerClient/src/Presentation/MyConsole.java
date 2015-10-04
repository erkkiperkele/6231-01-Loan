package Presentation;

import java.io.InputStream;
import java.util.Scanner;


public class MyConsole {

    private static Scanner _console;

    public MyConsole(InputStream in)
    {
        _console = new Scanner(in);
    }

    public String readLine()
    {
        return _console.nextLine();
    }

    public String newLine()
    {
        return System.lineSeparator();
    }
}
