package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;

import java.io.File;

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

    public boolean scenarioFileExists() {
        if (this.scenarioFilePath == null) {
            return false;
        } else {
            return true;
        }
    }

    // Setters
    public void setLogFilePath(String logFilePath) {
        // Do a more thorough check if valid pathname by trying to open the file
        try {
            File file = new File(logFilePath);

            // File already exists
            if (file.exists() && !file.isDirectory()) {
                this.logFilePath = logFilePath;
            }
            // If file is directory
            else if (file.isDirectory()) {
                printHelp();
            }
            // Else file does not exist. Create it and set this log filepath to it.
            else {
                file.createNewFile();
                this.logFilePath = logFilePath;
            }
        } catch (IOException e) {
            // System.out.println("HERE");
            // If filepath is invalid then create or use default logfile
            System.out.print("ERROR: could not print results. Target directory does not exist.\n");
            // terminate
            System.exit(1);
            // setLogFilePath();

        }
    }

    public void setLogFilePath() {
        this.logFilePath = "rescuebot.log";
    }

    public void setScenarioFilePath(String scenarioFilePath) {
        // Do a more thorough check if valid pathname by trying to open the file
        try {
            File file = new File(scenarioFilePath);

            // File already exists
            if (file.exists() && !file.isDirectory()) {
                this.scenarioFilePath = scenarioFilePath;
            }
            // If file is directory (not a file)
            else if (file.isDirectory()) {
                printHelp();
            }
            // Else file does not exist, default is used (ie: no scenarios file)
            else {
                setScenarioFilePath();
            }
        } catch (Exception e) {
            System.out.println("HERE");
            // If filepath is invalid then scenario path should be null (default)
            setScenarioFilePath();

        }
    }

    public void setScenarioFilePath() {
        // this.scenarioFilePath = "main/data/scenarios.csv";
        this.scenarioFilePath = null;
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

        // TESTING:
        // System.out.println(rescueBot.getLogFilePath());
        // System.out.println(rescueBot.getScenarioFilePath());

        // Initiate Menu Loop
        Menu menu = new Menu(rescueBot.logFilePath, rescueBot.scenarioFilePath);
        menu.runMenuLoop(rescueBot.scenarioFileExists());

        // Exit the program
        System.exit(0);
    }

    private void processArgs(String[] args) {

        // Set default values for log and scenarios filePaths
        setScenarioFilePath();
        setLogFilePath();
        String potentialPathName;

        // Display help if a singular argument is given (Incorrect argument or arg
        // format results in same as --help)
        if (args.length == 1) {
            printHelp();
        }

        // Loop through arguments. Only works for multiple arguments
        for (int i = 0; i < args.length && args.length > 1; i++) {
            String arg = args[i];
            // Process each arg accordingly
            switch (arg) {
                // Log file path given Case
                case "-l":
                case "--log":
                    if (args.length > i + 1) {
                        // Next argument should be a pathname
                        potentialPathName = args[i + 1];

                        // Performs simple 'could be a pathname' check with regex
                        // setLogFilePath will later check if it is actually valid
                        if (potentialPathName.matches(".*\\.[a-zA-Z0-9]+")) {
                            // try {
                            setLogFilePath(potentialPathName);
                            // } catch (Exception e) {
                            // // ErrorHandler.handleFileOpeningError(potentialPathName, e);
                            // // System.out.println("ERROR!");
                            // // System.exit(1);
                            // }

                            i++; // Skip processing next argument in switch (pathName)
                        } else {
                            printHelp();
                        }
                    }
                    break;
                case "-s":
                case "--scenarios":
                    if (args.length > i + 1) {
                        // Next argument should be a pathname
                        potentialPathName = args[i + 1];

                        // Performs simple 'could be a pathname' check with regex
                        // setLogFilePath will later check if it is actually valid
                        if (potentialPathName.matches(".*\\.[a-zA-Z0-9]+")) {
                            // try {
                            setScenarioFilePath(potentialPathName);
                            // } catch (Exception e) {
                            // // ErrorHandler.handleFileOpeningError(potentialPathName, e);
                            // System.out.println("ERROR!");
                            // System.exit(1);
                            // }
                            i++; // Skip processing next argument in switch (pathName)
                        } else {
                            printHelp();
                        }
                    }
                    break;
                // Anything else defaults to help. *Cases ("-h" / "--help") kept for clarity
                case "-h":
                case "--help":
                default:
                    printHelp();
                    break;
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