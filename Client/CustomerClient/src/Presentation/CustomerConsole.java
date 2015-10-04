package Presentation;

import Contracts.ICustomerService;
import Data.BankName;
import Services.CustomerService;

public class CustomerConsole {

    private static ICustomerService _customerService;

    public static void main(String[] args) {

        _customerService = new CustomerService();

        boolean isExiting = false;

        while (!isExiting) {
            displayChoices();
            isExiting = executeChoice();
        }

    }

    private static void displayChoices() {

        System.out.println(
                "Please chose an option:"
                        + "1: Open an account"
                        + "2: To come"
                        + "Press any other key to exit."
        );
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
                displayChoices();
                isExiting = true;
                break;
        }
        return isExiting;
    }

    private static char readChar() {
        String answer = System.console().readLine().trim();
        return answer.charAt(0);
    }

    private static String readString() {
        return System.console().readLine().trim();
    }

    private static int readint() {
        String input = System.console().readLine().trim();
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
        return userAnswer == 0
                ? BankName.Royal.toInt()
                : userAnswer;
    }

    private static String askFirstName() {
        System.out.println("Enter firstName: ");
        String userAnswer = readString();
        return userAnswer.equals("")
                ? "Aymeric"
                : userAnswer;
    }

    private static String askLastName() {

        System.out.println("Enter lastName: ");
        String userAnswer = readString();
        return userAnswer.equals("")
                ? "Grail"
                : userAnswer;
    }

    private static String askEmail() {
        System.out.println("Enter email: ");
        String userAnswer = readString();
        return userAnswer.equals("")
                ? "Aymeric.Grail@gmail.com"
                : userAnswer;
    }

    private static String askPhone() {
        System.out.println("Enter phone: ");
        String userAnswer = readString();
        return userAnswer.equals("")
                ? "514.660.2812"
                : userAnswer;
    }

    private static String askPassword() {
        System.out.println("Enter password: ");
        String userAnswer = readString();
        return userAnswer.equals("")
                ? "zaza"
                : userAnswer;
    }
}
