package main.java.statistics;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import main.java.scenario.Scenario;
import main.java.location.Location;

public class Statistics {
    // Instance Variables
    public HashMap<String, Integer> seenDict;
    public HashMap<String, Integer> savedDict;

    static class AttributePercentagePair {
        String attribute;
        double percentage;

        AttributePercentagePair(String attribute, double percentage) {
            this.attribute = attribute;
            this.percentage = percentage;
        }

        String getAttribute() {
            return attribute;
        }

        double getPercentage() {
            return percentage;
        }
    }

    // Constructors
    public Statistics() {
        setSeenDict();
        setSavedDict();
        // COMPLETE
    }

    // Getters

    // Setters
    public void setSeenDict(HashMap<String, Integer> seenDict) {
        this.seenDict = seenDict;
    }

    public void setSeenDict() {
        this.seenDict = new HashMap<String, Integer>();
    }

    public void setSavedDict(HashMap<String, Integer> savedDict) {
        this.savedDict = savedDict;
    }

    public void setSavedDict() {
        this.savedDict = new HashMap<String, Integer>();
    }

    // Methods
    public void updateSeen(Scenario scenario) {
        // Updates how many times each attribute has been seen

        for (Location location : scenario.getLocations()) {
            String[] charactersStrings = location.toString().split("\n");

            // Loop over each Character String to derive attributes
            for (String charStr : charactersStrings) {
                if (charStr.startsWith("-")) {
                    // Extracts list of attribute keywords with regex and string operations
                    String[] attributes = charStr.substring(2).trim().split("\\s+");

                    for (String attribute : attributes) {
                        // Skip if empty
                        if (attribute.isEmpty() || attribute.equals("is")) {
                            continue;
                        }

                        // Add to dictionary (if not yet in it) or update count by 1
                        this.seenDict.put(attribute, seenDict.getOrDefault(attribute, 0) + 1);
                    }
                }
            }
        }
    }

    public void updateSaved(Scenario scenario, int choice) {
        // Updates how many times each attribute has been seen

        // Update dict for characters in chosen location only
        Location location = scenario.getLocation(choice - 1);
        String[] charactersStrings = location.toString().split("\n");

        // Loop over each Character String to derive attributes
        for (String charStr : charactersStrings) {
            if (charStr.startsWith("-")) {
                // Extracts list of attribute keywords with regex and string operations
                String[] attributes = charStr.substring(2).trim().split("\\s+");

                for (String attribute : attributes) {
                    // Skip if empty
                    if (attribute.isEmpty() || attribute.equals("is")) {
                        continue;
                    }

                    // Add to dictionary (if not yet in it) or update count by 1
                    this.savedDict.put(attribute, savedDict.getOrDefault(attribute, 0) + 1);
                }
            }
        }
    }

    public String displayStats() {
        String statsString = "";
        HashMap<String, Integer> seen = this.seenDict;
        HashMap<String, Integer> saved = this.savedDict;

        ArrayList<AttributePercentagePair> attributes = new ArrayList<>();

        // Loops over all keys
        for (String key : seen.keySet()) {
            double percentage = (double) saved.getOrDefault(key, 0) / seen.get(key);
            AttributePercentagePair apPair = new AttributePercentagePair(key, percentage);

            // Uses binary search (on both descending percentage, and then ascending alpha
            // order) to find the id to insert at
            int id = Collections.binarySearch(attributes, apPair,
                    Comparator.comparing(AttributePercentagePair::getPercentage)
                            .thenComparing(AttributePercentagePair::getAttribute).reversed());
            if (id < 0) {
                // Insert at id
                attributes.add(-(id + 1), apPair);
            }

        }

        for (AttributePercentagePair attribute : attributes) {
            statsString += String.format("%s: %.2f\n", attribute.getAttribute(), attribute.getPercentage());
        }
        return statsString;
    }
}
