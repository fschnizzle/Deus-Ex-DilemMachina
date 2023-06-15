package main.java;

import java.util.HashMap;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import main.java.scenario.Scenario;
import main.java.statistics.Statistics;
import main.java.location.Location;
import main.java.scenario.ScenarioLoader;

public class User {
    // Instance Variables
    private boolean givesLogConsent;
    private Statistics statistics;
    private ArrayList<Scenario> scenarios;
    private ScenarioLoader scenarioLoader;

    // Constructors
    public User() {
        // Load Scenarios
        setScenarioLoader("main/data/scenarios.csv");
        try {
            setScenarios();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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

    public void setScenarios() throws FileNotFoundException {
        this.scenarios = this.scenarioLoader.loadScenarios();
    }

    public void setScenarioLoader(String filePath) {
        this.scenarioLoader = new ScenarioLoader(filePath);
    }

    public void setGivesLogConsent(boolean givesLogConsent) {
        this.givesLogConsent = givesLogConsent;
    }

    // Methods
    public void updateGivesLogConsent(Scanner keyboard) {
        String consentResponse = null;

        // Initial consent prompt
        System.out.print("Do you consent to have your decisions saved to a file? (yes/no)\n>");
        consentResponse = keyboard.nextLine().toLowerCase();

        // Loops until valid "yes" / "no" reponse given to consent prompt
        do {
            if (consentResponse == null) {
                System.out
                        .print("Invalid response! Do you consent to have your decisions saved to a file? (yes/no)\n>");
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

        // Initiate new statistics
        setStatistics();

        // Scenario count and scenarios seen count
        int scenCount = scenarioLoader.getScenarioCount();
        int scenSeenCount = statistics.getScenariosSeenCount();

        String cont = "yes"; // Continue value

        // Loop until "no" given or out of scenarios
        do {
            // Call judge scenarios
            if (cont.equals("yes")) {
                judgeScenarios(keyboard);

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

    }

    public void judgeScenarios(Scanner keyboard) {
        // id of Location value for each scenarios
        int deployTo;

        for (Scenario scenario : this.scenarios) {
            // Print Scenario details
            System.out.println(scenario.toString());

            // Prompt user for judgement
            System.out.print("To which location should RescueBot be deployed?\n> ");
            deployTo = keyboard.nextInt();
            keyboard.nextLine();

            // Update their statistics
            updateStatistics(scenario, deployTo);

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
