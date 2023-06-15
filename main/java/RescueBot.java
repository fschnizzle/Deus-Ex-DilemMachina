package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;

/**
 * COMP90041, Sem1, 2023: Final Project
 * 
 * @author: Flynn Schneider
 *          student id: 982143
 *          student email: fschneider@student.unimelb.edu.au
 */
public class RescueBot {

    // Variables
    private String logFilePath;
    private String scenarioFilePath;

    // Constructor
    public RescueBot() {

    }

    // Getters
    public String getLogFilePath() {
        return this.logFilePath;
    }

    public String getScenarioFilePath() {
        return this.scenarioFilePath;
    }

    // Setters
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public void setLogFilePath() {
        this.logFilePath = "main/rescuebot.log";
    }

    public void setScenarioFilePath(String scenarioFilePath) {
        this.scenarioFilePath = scenarioFilePath;
    }

    public void setScenarioFilePath() {
        this.scenarioFilePath = "main/data/scenarios.csv";
    }

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

    /*
     * Program entry
     */
    public static void main(String[] args) {
        // Define bot
        RescueBot rescueBot = new RescueBot();

        // Process arguments if any given
        rescueBot.processArgs(args);

        // Initiate Menu Loop
        Menu menu = new Menu();
        menu.runMenuLoop();

        // Exit the program
        System.exit(0);
    }

    private void processArgs(String[] args) {
        // Loop through arguments
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            // Process each arg accordingly
            switch (arg) {

                // Help Case
                case "-h":
                case "--help":
                    printHelp();
                    break;

                // Log file path given Case
                case "-l":
                case "--log":
                    if (args.length > i + 1) {
                        logFilePath = args[i + 1];
                    }
            }
            // System.out.println(arg);
        }
    }

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

    private static void printHelp() {
        // Prints help
        System.out.println("RescueBot - COMP90041 - Final Project\n");
        System.out.println("Usage: java RescueBot [arguments]\n");
        System.out.println("Arguments:");
        System.out.println("-s or --scenarios    Optional: path to scenario file");
        System.out.println("-h or --help         Optional: Print Help (this message) and exit");
        System.out.println("-l or --log          Optional: path to data log file");

        // System immediately exits after
        System.exit(0);
    }
}