package Presentation;

import java.io.InputStream;
import java.util.Scanner;


public class Console {

    private static Scanner _console;

    public Console(InputStream in) {
        _console = new Scanner(in);
    }

    public String newLine() {
        return System.lineSeparator();
    }

    public char readChar() {
        String answer = this.readLine().trim();

        if (answer.equals("")) {
            return '0';
        }

        return answer.charAt(0);
    }

    public String readString() {
        return this.readLine().trim();
    }

    public int readint() {
        String input = this.readLine().trim();
        int answer = 0;

        if (input.equals("")) {
            return answer;
        }

        answer = Integer.parseInt(input);
        return answer;
    }

    public void println(String message) {
        System.out.println(message);
    }

    private String readLine() {
        return _console.nextLine();
    }
}
