package main.java;

import java.util.HashMap;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import main.java.scenario.Scenario;
import main.java.statistics.Statistics;
import main.java.location.Location;
import main.java.scenario.ScenarioLoader;
import main.java.RescueBot;

public class User {
    // Instance Variables
    private boolean givesLogConsent;
    private Statistics statistics;
    // private ArrayList<Statistics> allStatistics
    private ArrayList<Scenario> scenarios;
    private ScenarioLoader scenarioLoader;

    final int SCENARIOS_PER_RUN = 3;

    // Constructors
    // NOT BEING USED
    public User() {
        // Load Scenarios
        setScenarioLoader("main/data/scenarios.csv");
        try {
            setScenariosFromFile();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // setStatistics();
        // COMPLETE
    }

    public User(String logFilePath, String scenarioFilePath) {
        // Load Scenarios either thru RSG (Random Scenario generation) or from file
        // CHECK IF scenarioFilePath is empty!!
        if (scenarioFilePath == null) {
            // RSG
            System.out.println("NO FILE GIVEN, TIME FOR RSG!!");
            setScenarioLoader();
            // setScenariosFromRSG();
        } else {
            setScenarioLoader(scenarioFilePath);
            try {
                setScenariosFromFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // setStatisticsLogger

        // setStatistics();
        // COMPLETE
    }

    // Getters
    public Statistics getStatistics() {
        return this.statistics;
    }

    public ArrayList<Scenario> getScenarios() {
        return this.scenarios;
    }

    public Scenario getScenario(int id) {
        return getScenarios().get(id);
    }

    public ScenarioLoader getScenarioLoader() {
        return this.scenarioLoader;
    }

    public boolean getGivesLogConsent() {
        return this.givesLogConsent;
    }

    // // Setters
    public void setStatistics() {
        this.statistics = new Statistics();
    }

    public void setScenariosFromFile() throws FileNotFoundException {
        scenarios = scenarioLoader.loadScenariosFromFile();
    }

    public void setScenariosFromRSG() {
        // Set scenarios arraylist
        scenarios = scenarioLoader.loadScenariosFromRSG();
    }

    public void setScenarioLoader(String filePath) {
        this.scenarioLoader = new ScenarioLoader(filePath);
    }

    // RSG
    public void setScenarioLoader() {
        this.scenarioLoader = new ScenarioLoader();
    }

    public void setGivesLogConsent(boolean givesLogConsent) {
        this.givesLogConsent = givesLogConsent;
    }

    // Methods
    public void updateGivesLogConsent(Scanner keyboard) {
        String consentResponse = null;

        // Initial consent prompt
        System.out.print("Do you consent to have your decisions saved to a file? (yes/no)\n> ");
        consentResponse = keyboard.nextLine().toLowerCase();

        // Loops until valid "yes" / "no" reponse given to consent prompt
        do {
            if (consentResponse == null) {
                System.out
                        .print("Invalid response! Do you consent to have your decisions saved to a file? (yes/no)\n> ");
                consentResponse = keyboard.nextLine().toLowerCase();
            }
            // Process consent response and update accordingly
            switch (consentResponse) {
                case "yes":
                case "y":
                    setGivesLogConsent(true);
                    break;
                case "no":
                case "n":
                    setGivesLogConsent(false);
                    break;
                default:
                    // TODO: Change to "an InvalidInputException should be thrown and the user
                    // should be prompted again"
                    consentResponse = null;
                    break;
            }
        } while (consentResponse == null);
    }

    public void handleJudgeScenarios(Scanner keyboard) {
        // First asks for consent and updates the users givesLogConsent value
        updateGivesLogConsent(keyboard);

        // Handle consent or not with get

        // Initiate new statistics
        setStatistics();

        // Scenario count and scenarios seen count
        int scenCount = scenarioLoader.getScenarioCount();
        if (scenCount == 0) {
            // Generate some scenarios (RSG)
            scenarioLoader.loadScenariosFromRSG();
        }
        int scenSeenCount = statistics.getScenariosSeenCount();

        String cont = "yes"; // Continue value

        // Loop until "no" given or out of scenarios
        do {
            // Call judge scenarios
            if (cont.equals("yes")) {
                judgeScenarios(keyboard, scenSeenCount);

                // Update seen scenarios count
                scenSeenCount = statistics.getScenariosSeenCount();
            }

            // Break if user has seen all scenarios
            if (scenCount == scenSeenCount) {
                cont = "no";

                // Output FINAL statistics
                System.out.println(showStatistics());
                break;
            }

            // show session statistics
            System.out.println(showStatistics());

            // Prompt REPEAT ("yes") or RETURN ("no")
            System.out.println("Would you like to continue? (yes/no)");
            System.out.print("> ");
            cont = keyboard.nextLine();

        } while (!cont.equals("no"));

        // Finally, return to main menu
        System.out.print("That's all. Press Enter to return to main menu.\n> ");
        keyboard.nextLine();

    }

    public void judgeScenarios(Scanner keyboard, int scenSeenCount) {
        // id of Location value for each scenarios
        int deployTo;
        int choiceRange; // Number of locations to pick from at scenario

        // Number of scenarios seen in session
        int seshScenCount = scenSeenCount;

        // Number of scenarios seen resets after every 3
        int toThreeCount = 0;

        // for (Scenario scenario : this.scenarios) {
        for (int i = seshScenCount; i < this.getScenarios().size() && toThreeCount < 3; i++) {

            Scenario scenario = this.getScenario(i);
            // Print Scenario details
            System.out.println(scenario.toString());

            // Location count for scenario (upper bound of range)
            choiceRange = scenario.getLocations().size();

            // Prompt user for judgement
            System.out.print("To which location should RescueBot be deployed?\n> ");
            deployTo = keyboard.nextInt();
            keyboard.nextLine();

            // Continue prompting if invalid location id (deployTo) given
            while (deployTo < 1 || deployTo > choiceRange) {
                System.out.print("Invalid response! To which location should RescueBot be deployed?\n> ");
                deployTo = keyboard.nextInt();
                keyboard.nextLine();
            }

            // Update their statistics and toThreeCount
            updateStatistics(scenario, deployTo);
            toThreeCount++;

            // Update log if consent is given
            // if (givesLogConsent){
            // updateLog(logFilePath);
            // }

        }
    }

    public void updateStatistics(Scenario scenario, int choice) {
        statistics.updateSeen(scenario);
        statistics.updateSaved(scenario, choice);
        statistics.updateAgeStatistics(scenario, choice);
    }

    public String showStatistics() {
        return this.statistics.displayStats();
    }

}
