package main.java;

import java.util.Scanner;

import main.java.character.Animal;
import main.java.character.Human;
import main.java.location.Location;
import main.java.scenario.Scenario;
import main.java.User;

// TODO: SetStatisticsLogger which contains deets on algo and user

public class Menu {
    private Scanner keyboard;
    private User user;

    // Constructor
    public Menu() {
        keyboard = new Scanner(System.in);
        setUser();
    }

    public Menu(String logFilePath, String scenarioFilePath) {
        keyboard = new Scanner(System.in);
        setUser(logFilePath, scenarioFilePath);
    }

    // Getters
    public User getUser() {
        return user;
    }

    // Setters
    public void setUser() {
        this.user = new User(); // Calls to filepath DEFAULT
    }

    public void setUser(String logFilePath, String scenarioFilePath) {
        this.user = new User(logFilePath, scenarioFilePath); // Calls to filepath DEFAULT
    }

    public void displayMenuText() {
        // Print out the menu options
        System.out.println("Please enter one of the following commands to continue:");
        System.out.println("- judge scenarios: [judge] or [j]");
        System.out.println("- run simulations with the in-built decision algorithm: [run] or [r]");
        System.out.println("- show audit from history: [audit] or [a]");
        System.out.print("- quit the program: [quit] or [q]\n> ");
    }

    public void runMenuLoop(boolean scenarioFileExists) {
        // Print the welcome message
        String welcomeFileName = "main/java/etc/welcome.ascii";
        // RescueBot.printMessage(welcomeFileName); // UNCOMMENT LATER (TODO)

        // If scenarios file given, count the scenarios loaded from file and print
        if (scenarioFileExists) {
            int N = this.getUser().getScenarioLoader().getScenarioCount();
            System.out.println(N + " scenarios imported.");
        }

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
                // user.handleJudgeScenarios(keyboard)
                // Prompt users for scenario count

                // Create scenarios (RSG generation)

                // Add scenario objects using loadScenariosFromFile() equivalent

                // Call decision() method (simulate decision making process)
                // Code for running simulations goes here.
                break;

            case "audit":
            case "a":
                // Code for showing audit from history goes here.

                // How many statistics file does user have
                // user.getStatistics();
                // System.out.println("SHOW CURRENT FINAL AUDIT FOR BOTH USER AND ALGORITHM!!");

                // Interface Implentations
                // Animal
                Animal rgAnimal = new Animal(true);
                Animal animal = rgAnimal.randomGen();
                System.out.println(animal.extendedToString());
                // Human
                Human rgHuman = new Human(true);
                Human human = rgHuman.randomGen();
                System.out.println(human.extendedToString());

                // Location
                Location rgLoc = new Location(true);
                Location location = rgLoc.randomGen();
                System.out.println(location.extendedToString());
                System.out.println(location.toString());

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