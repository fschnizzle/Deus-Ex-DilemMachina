package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;

import main.java.character.Animal;
import main.java.character.Human;

/**
 * COMP90041, Sem1, 2023: Final Project
 * 
 * @author: Flynn Schneider
 *          student id: 982143
 *          student email: fschneider@student.unimelb.edu.au
 */
public class RescueBot {

    private int scenariosImportedCount;

    /**
     * Decides whether to save the passengers or the pedestrians
     * 
     * @param Scenario scenario: the ethical dilemma
     * @return Decision: which group to save
     */

    // Decision Static Method

    // public static Location decide(Scenario scenario) {
    // // a very simple decision engine
    // // TODO: take into account at least 5 characteristics

    // // 50/50
    // if (Math.random() > 0.5) {
    // return scenario.getLocation(1);
    // } else {
    // return scenario.getLocation(2);
    // }
    // }

    public static void printMessage(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    /*
     * Program entry
     */
    public static void main(String[] args) {
        // Print the welcome message
        String welcomeFileName = "main/java/etc/welcome.ascii";
        printMessage(welcomeFileName);

        // Load the scenarios file
        // Print out the number of loaded scenarios
        System.out.println("{N} scenarios imported.");

        // Initiate Menu Loop
        Menu menu = new Menu();
        menu.runMenuLoop();

        // TESTING
        // Human ben = new Human(80, "Male", "ATHLETIC", "Hacker", true);
        // System.out.println(ben.toString());

        // Exit the program
        System.exit(0);
    }
}