package main.java;

import java.util.Scanner;

public class Menu {
    private Scanner keyboard;
    private User user;

    // Constructor
    public Menu() {
        this.keyboard = new Scanner(System.in);
        setUser();
    }

    // Getters
    public User getUser(User user) {
        return user;
    }

    // Setters
    public void setUser() {
        this.user = new User();
    }

    public void displayMenu() {
        // Print out the menu options
        System.out.println("Please enter one of the following commands to continue:");
        System.out.println("- judge scenarios: [judge] or [j]");
        System.out.println("- run simulations with the in-built decision algorithm: [run] or [r]");
        System.out.println("- show audit from history: [audit] or [a]");
        System.out.print("- quit the program: [quit] or [q]\n> ");
    }

    public void runMenuLoop() {
        String input;
        do {
            displayMenu();
            input = keyboard.nextLine().trim();
            handleUserInput(input);
        } while (!input.equals("q") && !input.equals("quit"));
    }

    public void handleUserInput(String input) {
        // Switch statement to handle different user input cases
        System.out.print("You entered: " + input + "\n");

    }
}