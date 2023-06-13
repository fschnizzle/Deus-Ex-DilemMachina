package main.java;

import java.util.HashMap;

import main.java.scenario.Scenario;
import main.java.statistics.Statistics;
import main.java.location.Location;

public class User {
    // Instance Variables
    // public HashMap<String, Integer> seenDict;
    // public HashMap<String, Integer> savedDict;
    private boolean givesLogConsent;
    private Statistics statistics;

    // Constructors
    public User() {
        setStatistics();
        // COMPLETE
    }

    // Getters
    public Statistics getStatistics() {
        return this.statistics;
    }

    // // Setters
    public void setStatistics() {
        this.statistics = new Statistics();
    }

    // Methods
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
