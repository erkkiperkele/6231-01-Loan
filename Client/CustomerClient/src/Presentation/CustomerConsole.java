package Presentation;

import Contracts.ICustomerService;
import Data.*;
import Services.CustomerService;
import Transport.UDP.UDPClient;

public class CustomerConsole {

    private static UDPClient _client;
    private static ICustomerService _customerService;
    private static Console _console;
    private static Customer _currentCustomer;

    public static void main(String[] args) {

        _client = new UDPClient();
        _customerService = new CustomerService();
        _console = new Console(System.in);


        boolean isExiting = false;

        while (!isExiting) {
            displayChoices();
            isExiting = executeChoice();
        }

    }

    private static void displayChoices() {

        String message = String.format(
                "Please chose an option:"
                        + "%1$s 1: Open an account"
                        + "%1$s 2: Sign in"
                        + "%1$s Press any other key to exit."
                , _console.newLine());

        _console.println(message);
    }

    private static boolean executeChoice() {

        char choice = _console.readChar();
        boolean isExiting = false;

        switch (choice) {
            case '1':
                displayOpenAccount();
                break;
            case '2':
                _console.println("This option has not been implemented yet. Please choose something else.");
                displaySignin();
                break;
            default:
                _console.println("See you!");
                isExiting = true;
                break;
        }
        return isExiting;
    }

    private static void displaySignin() {
        Bank bank = askBankId();
        String email = askEmail();
        String password = askPassword();

        Customer customer = _customerService.getCustomer(bank, email, password);

        _currentCustomer = customer;
        displayCurrentCustomerInfo();

        displayChoices();
    }

    private static void displayCurrentCustomerInfo() {

        _console.println("Customer logged in as: " + _console.newLine());
        _console.println(_currentCustomer.getFirstName() + _console.newLine());
        _console.println(_currentCustomer.getLastName() + _console.newLine());
        _console.println(_currentCustomer.getBank().toString() + _console.newLine());
    }


    private static void displayOpenAccount() {


        Bank bankId = askBankId();
        String firstName = askFirstName();
        String lastName = askLastName();
        String email = askEmail();
        String phone = askPhone();
        String password = askPassword();

        _console.println("Requesting server to open a new account:" + _console.newLine());

        int accountNumber = openAccount(bankId, firstName, lastName, email, phone, password);

        _console.println("Account Number: " + accountNumber + _console.newLine());

    }

    private static Bank askBankId() {

        _console.println("Enter bankId" );
        _console.println(String.format("(1 - %1$s, 2 - %2$s, 3 %3$s): ", Bank.Royal, Bank.National, Bank.Dominion));
        int userAnswer = _console.readint();
        Bank answer = userAnswer == 0
                ? Bank.Royal
                : Bank.fromInt(userAnswer);

        displayAnswer(answer.toString());
        return answer;
    }

    private static String askFirstName() {
        _console.println("Enter firstName: ");
        String userAnswer = _console.readString();
        String answer =  userAnswer.equals("")
                ? "Aymeric"
                : userAnswer;

        displayAnswer(answer);
        return answer;
    }

    private static String askLastName() {

        _console.println("Enter lastName: ");
        String userAnswer = _console.readString();
        String answer = userAnswer.equals("")
                ? "Grail"
                : userAnswer;

        displayAnswer(answer);
        return answer;
    }

    private static String askEmail() {
        _console.println("Enter email: ");
        String userAnswer = _console.readString();
        String answer = userAnswer.equals("")
                ? "Aymeric.Grail@gmail.com"
                : userAnswer;

        displayAnswer(answer);
        return answer;
    }

    private static String askPhone() {
        _console.println("Enter phone: ");
        String userAnswer = _console.readString();
        String answer = userAnswer.equals("")
                ? "514.660.2812"
                : userAnswer;

        displayAnswer(answer);
        return answer;
    }

    private static String askPassword() {
        _console.println("Enter password: ");
        String userAnswer = _console.readString();
        String answer = userAnswer.equals("")
                ? "zaza"
                : userAnswer;

        displayAnswer(answer);
        return answer;
    }

    private static int openAccount(
            Bank bankId,
            String firstName,
            String lastName,
            String email,
            String phone,
            String password) {

        int accountNumber = _customerService.openAccount(bankId, firstName, lastName, email, phone, password);
        return accountNumber;
    }

    private static void displayAnswer(String answer) {
        _console.println("Value Entered: " + answer + _console.newLine());
    }
}
