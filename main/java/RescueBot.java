package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;

import main.java.character.Animal;
import main.java.character.Human;
import main.java.location.Location;
import main.java.scenario.Scenario;

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

        // MANUAL TESTING (instead of randomly generated or read from file)
        Scenario sc1 = new Scenario("Flood");
        Location loc1 = new Location(13.7154, 'N', 150.9094, 'W', true);
        Human ben = new Human(0, "MALE", "AVERAGE", "Banker", false);
        Animal kol = new Animal(10, "MALE", "ATHLETIC", "Koala", true);
        Location loc2 = new Location(99.7154, 'N', 150.9094, 'W', false);
        Human anne = new Human(30, "FEMALE", "ATHLETIC", "Midwife", true);
        Human jil = new Human(80, "FEMALE", "ATHLETIC", "Midwife", true);
        loc1.addCharacter(ben);
        loc1.addCharacter(kol);
        loc2.addCharacter(anne);
        loc2.addCharacter(jil);
        sc1.addLocation(loc1);
        sc1.addLocation(loc2);
        System.out.println(sc1.toString());

        // Exit the program
        System.exit(0);
    }
}