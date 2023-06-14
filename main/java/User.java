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
        setScenarioLoader("/Users/flynnschneider/Desktop/RescueBots/Deus-Ex-DilemMachina/main/data/scenarios.csv");
        try {
            setScenarios();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setStatistics();
        // COMPLETE
    }

    // public User(String filePath) {
    // // Load Scenarios
    // setScenarioLoader(filePath);
    // try {
    // setScenarios();
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }

    // setStatistics();
    // // COMPLETE
    // }

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

    // Methods
    public void judgeScenarios(Scanner keyboard) {
        int deployTo;
        for (Scenario scenario : this.scenarios) {
            // Print Scenario details
            System.out.println(scenario.toString());

            // Prompt user for judgement
            System.out.print("To which location should RescueBot be deployed?\n> ");
            deployTo = keyboard.nextInt();

            // Update their statistics
            updateStatistics(scenario, deployTo);
        }

    }

    public void updateStatistics(Scenario scenario, int choice) {
        this.statistics.updateSeen(scenario);
        this.statistics.updateSaved(scenario, choice);
    }

    public String showStatistics() {
        return this.statistics.displayStats();
    }

    // public void setSeenDict(HashMap<String, Integer> seenDict) {
    // this.seenDict = seenDict;
    // }

    // public void setSeenDict() {
    // this.seenDict = new HashMap<String, Integer>();
    // }

    // public void setSavedDict(HashMap<String, Integer> savedDict) {
    // this.savedDict = savedDict;
    // }

    // public void setSavedDict() {
    // this.savedDict = new HashMap<String, Integer>();
    // }

    // // Methods
    // public void updateSeen(Scenario scenario) {
    // // Updates how many times each attribute has been seen

    // for (Location location : scenario.getLocations()) {
    // String[] charactersStrings = location.toString().split("\n");

    // // Loop over each Character String to derive attributes
    // for (String charStr : charactersStrings) {
    // if (charStr.startsWith("-")) {
    // // Extracts list of attribute keywords with regex and string operations
    // String[] attributes = charStr.substring(2).trim().split("\\s+");

    // for (String attribute : attributes) {
    // // Skip if empty
    // if (attribute.isEmpty()) {
    // continue;
    // }

    // // Add to dictionary (if not yet in it) or update count by 1
    // this.seenDict.put(attribute, seenDict.getOrDefault(attribute, 0) + 1);
    // }
    // }
    // }
    // }
    // }

    // public void updateSaved(Scenario scenario, int choice) {
    // // Updates how many times each attribute has been seen

    // // Update dict for characters in chosen location only
    // Location location = scenario.getLocation(choice - 1);
    // String[] charactersStrings = location.toString().split("\n");

    // // Loop over each Character String to derive attributes
    // for (String charStr : charactersStrings) {
    // if (charStr.startsWith("-")) {
    // // Extracts list of attribute keywords with regex and string operations
    // String[] attributes = charStr.substring(2).trim().split("\\s+");

    // for (String attribute : attributes) {
    // // Skip if empty
    // if (attribute.isEmpty()) {
    // continue;
    // }

    // // Add to dictionary (if not yet in it) or update count by 1
    // this.savedDict.put(attribute, savedDict.getOrDefault(attribute, 0) + 1);
    // }
    // }
    // }
    // }

    // public String displayStats() {
    // String statsString = "";
    // HashMap<String, Integer> seen = this.seenDict;
    // HashMap<String, Integer> saved = this.savedDict;
    // // Loops over all keys
    // for (String key : this.seenDict.keySet()) {
    // double percentage = (double) saved.getOrDefault(key, 0) / seen.get(key);
    // statsString += String.format("%s: %.2f\n", key, percentage);
    // }
    // return statsString;
    // }
}
