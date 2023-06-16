package main.java;

import java.util.Scanner;

import main.java.character.Animal;
import main.java.character.Human;
import main.java.location.Location;
import main.java.scenario.Scenario;
import main.java.User;

public class Menu {
    private Scanner keyboard;
    private User user;

    // Constructor
    public Menu() {
        keyboard = new Scanner(System.in);
        setUser();
    }

    // Getters
    public User getUser() {
        return user;
    }

    // Setters
    public void setUser() {
        this.user = new User(); // Calls to filepath DEFAULT
    }

    public void displayMenuText() {
        // Print out the menu options
        System.out.println("Please enter one of the following commands to continue:");
        System.out.println("- judge scenarios: [judge] or [j]");
        System.out.println("- run simulations with the in-built decision algorithm: [run] or [r]");
        System.out.println("- show audit from history: [audit] or [a]");
        System.out.print("- quit the program: [quit] or [q]\n> ");
    }

    public void runMenuLoop() {
        // Print the welcome message
        String welcomeFileName = "main/java/etc/welcome.ascii";
        // RescueBot.printMessage(welcomeFileName); // UNCOMMENT LATER (TODO)

        // Count the scenarios loaded
        int N = this.getUser().getScenarioLoader().getScenarioCount();

        // Print out the number of loaded scenarios
        System.out.println(N + " scenarios imported.");

        String input;
        do {
            displayMenuText();
            input = keyboard.nextLine().toLowerCase();
            handleUserInput(input);
        } while (!input.equals("q") && !input.equals("quit"));
    }

    public void handleUserInput(String input) {
        // Switch statement to handle different user input cases
        switch (input) {
            case "judge":
            case "j":
                // Starts the user judge
                user.handleJudgeScenarios(keyboard);
                // this.user.judgeScenarios(this.keyboard);
                // this.user.judgeScenarios(this.keyboard);
                break;

            case "run":
            case "r":
                // Code for running simulations goes here.
                break;

            case "audit":
            case "a":
                // Code for showing audit from history goes here.

                // How many statistics file does user have
                // user.getStatistics();
                System.out.println("SHOW CURRENT FINAL AUDIT FOR BOTH USER AND ALGORITHM!!");

                // System.out.println(this.user.showStatistics());
                break;

            case "quit":
            case "q":
                // Code for quitting the program goes here.
                break;

            default:
                System.out.println("Invalid command. Please try again.");
                break;
        }
    }
}