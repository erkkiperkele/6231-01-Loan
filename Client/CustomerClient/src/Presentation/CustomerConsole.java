package Presentation;

import Contracts.ICustomerService;
import Data.BankName;
import Services.CustomerService;

public class CustomerConsole {

    private static ICustomerService _customerService;
    private static MyConsole _console;

    public static void main(String[] args) {

        _customerService = new CustomerService();
        _console = new MyConsole(System.in);

        boolean isExiting = false;

        while (!isExiting) {
            displayChoices();
            isExiting = executeChoice();
        }

    }

    private static void displayChoices() {

//        String.format("Duke's Birthday: %1$tm %1$te,%1$tY", c);
        String message = String.format(
                "Please chose an option:"
                        + "%1$s 1: Open an account"
                        + "%1$s 2: To come"
                        + "%1$s Press any other key to exit."
                , _console.newLine());

        System.out.println(message);
    }

    private static boolean executeChoice() {


        char choice = readChar();
        boolean isExiting = false;

        switch (choice) {
            case '1':
                displayOpenAccount();
                break;
            case '2':
                System.out.println("This option has not been implemented yet. Please choose something else.");
                displayChoices();
                break;
            default:
                System.out.println("See you!");
                isExiting = true;
                break;
        }
        return isExiting;
    }

    private static char readChar() {
        String answer = _console.readLine().trim();

        if (answer.equals(""))
        {
            return '0';
        }

        return answer.charAt(0);
    }

    private static String readString() {
        return _console.readLine().trim();
    }

    private static int readint() {
        String input = _console.readLine().trim();
        int answer = 0;

        if (input.equals("")) {
            return answer;
        }

        answer = Integer.parseInt(input);
        return answer;
    }


    private static void displayOpenAccount() {


        int bankId = askBankId();
        String firstName = askFirstName();
        String lastName = askLastName();
        String email = askEmail();
        String phone = askPhone();
        String password = askPassword();

        _customerService.openAccount(bankId, firstName, lastName, email, phone, password);
    }

    private static int askBankId() {

        System.out.println("Enter bankId: ");
        int userAnswer = readint();
        int answer = userAnswer == 0
                ? BankName.Royal.toInt()
                : userAnswer;

        displayAnswer(Integer.toString(answer));
        return answer;
    }

    private static String askFirstName() {
        System.out.println("Enter firstName: ");
        String userAnswer = readString();
        String answer =  userAnswer.equals("")
                ? "Aymeric"
                : userAnswer;

        displayAnswer(answer);
        return answer;
    }

    private static String askLastName() {

        System.out.println("Enter lastName: ");
        String userAnswer = readString();
        String answer = userAnswer.equals("")
                ? "Grail"
                : userAnswer;

        displayAnswer(answer);
        return answer;
    }

    private static String askEmail() {
        System.out.println("Enter email: ");
        String userAnswer = readString();
        String answer = userAnswer.equals("")
                ? "Aymeric.Grail@gmail.com"
                : userAnswer;

        displayAnswer(answer);
        return answer;
    }

    private static String askPhone() {
        System.out.println("Enter phone: ");
        String userAnswer = readString();
        String answer = userAnswer.equals("")
                ? "514.660.2812"
                : userAnswer;

        displayAnswer(answer);
        return answer;
    }

    private static String askPassword() {
        System.out.println("Enter password: ");
        String userAnswer = readString();
        String answer = userAnswer.equals("")
                ? "zaza"
                : userAnswer;

        displayAnswer(answer);
        return answer;
    }

    private static void displayAnswer(String answer) {
        System.out.println("Value Entered: " + answer + _console.newLine());
    }
}
