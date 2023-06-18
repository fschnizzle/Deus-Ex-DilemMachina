package statistics;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.math.BigDecimal;
import java.math.RoundingMode;

import model.scenario.Scenario;
import model.character.*;
import model.location.Location;
import statistics.AgeStatistics;

public class Statistics {
    // Instance Variables
    private HashMap<String, Integer> seenDict;
    private HashMap<String, Integer> savedDict;

    private int scenariosSeenCount;

    private AgeStatistics ageStatistics;

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
        setScenariosFromFileSeenCount();
        setAgeStatistics();
        // COMPLETE
    }

    // Getters
    public int getScenariosSeenCount() {
        return this.scenariosSeenCount;
    }

    public AgeStatistics getAgeStatistics() {
        return this.ageStatistics;
    }

    // Setters
    public void setScenariosFromFileSeenCount() {
        this.scenariosSeenCount = 0;
    }

    public void setAgeStatistics() {
        this.ageStatistics = new AgeStatistics();
    }

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
        // Increment scenarios seen count
        incrementScenariosSeenCount();

        // Updates how many times each attribute has been seen
        for (Location location : scenario.getLocations()) {
            String[] charactersStrings = location.toString().split("\n");

            // Adds extra details (HUMAN, ANIMAL, LEGAL, TRESPASSING)
            String[] extDetails = location.extendedToString().split("\n");

            for (String detail : extDetails) {
                String[] attributes = detail.split(",");

                // Extract entity type from first attribute
                String entityType = attributes[0];

                // Update seenDict for these entity types
                if (entityType.equals("legal") || entityType.equals("trespassing") || entityType.equals("animal")
                        || entityType.equals("human")) {
                    incrementDictValue(this.seenDict, entityType);
                }
            }

            // Loop over each Character String to derive attributes
            for (String charStr : charactersStrings) {
                // Update trespassing attribute count
                if (charStr.startsWith("Trespassing")) {
                    // If "yes"
                    if (charStr.substring(charStr.length() - "yes".length()).equals("yes")) {
                        incrementDictValue(this.seenDict, "trespassing");
                    }
                }
                // Update character attributes
                if (charStr.startsWith("-")) {
                    // Extracts list of attribute keywords with regex and string operations
                    String[] attributes = charStr.substring(2).trim().split("\\s+");

                    for (String attribute : attributes) {
                        // Skip if empty
                        if (attribute.isEmpty() || attribute.equals("is")) {
                            continue;
                        }

                        // Add to dictionary (if not yet in it) or update count by 1
                        incrementDictValue(this.seenDict, attribute);
                    }
                }
            }
        }
    }

    public void updateSaved(Scenario scenario, int choice) {
        // Updates how many times each attribute has been seen

        // Update dict for characters in chosen location only
        Location location = scenario.getLocation(choice - 1);

        // Adds extra details (HUMAN, ANIMAL, LEGAL, TRESPASSING)
        String[] extDetails = location.extendedToString().split("\n");

        for (String detail : extDetails) {
            String[] attributes = detail.split(",");

            // Extract entity type from first attribute
            String entityType = attributes[0];

            // Update seenDict for these entity types
            if (entityType.equals("legal") || entityType.equals("trespassing") || entityType.equals("animal")
                    || entityType.equals("human")) {
                incrementDictValue(this.savedDict, entityType);
            }
        }
        String[] charactersStrings = location.toString().split("\n");

        // Loop over each Character String to derive attributes
        for (String charStr : charactersStrings) {
            // Update trespassing attribute count
            if (charStr.startsWith("Trespassing")) {
                // If "yes"
                if (charStr.substring(charStr.length() - "yes".length()).equals("yes")) {
                    incrementDictValue(this.savedDict, "trespassing");
                }
            }
            if (charStr.startsWith("-")) {
                // Extracts list of attribute keywords with regex and string operations
                String[] attributes = charStr.substring(2).trim().split("\\s+");

                for (String attribute : attributes) {
                    // Skip if empty
                    if (attribute.isEmpty() || attribute.equals("is")) {
                        continue;
                    }

                    // Add to dictionary (if not yet in it) or update count by 1
                    incrementDictValue(this.savedDict, attribute);
                }
            }
        }
    }

    public void updateAgeStatistics(Scenario scenario, int choice) {
        // Get chosen location
        Location location = scenario.getLocation(choice - 1);
        ArrayList<character> characters = location.getCharacters();

        // For each human
        for (character chara : characters) {

            if (chara instanceof Human) {
                Human human = (Human) chara;

                // Increment age stats
                this.ageStatistics.incrementSavedHumanAge(human.getAge());
            }
        }

    }

    public String displayStats(boolean bypassHeader) {
        HashMap<String, Integer> seen = this.seenDict;
        HashMap<String, Integer> saved = this.savedDict;

        ArrayList<AttributePercentagePair> attributes = new ArrayList<>();

        // Statistics Header
        String statsString = "";
        if (!bypassHeader) {
            statsString += "======================================\n";
            statsString += "# Statistic\n";
            statsString += "======================================\n";
        }

        statsString += "- % SAVED AFTER " + scenariosSeenCount + " RUNS\n";

        // Loops over all keys
        for (String key : seen.keySet()) {
            double rawPercentage = (double) saved.getOrDefault(key, 0) / seen.get(key);
            double percentage = formatPercentage(rawPercentage); // Ensures rounding up to X.XX
            AttributePercentagePair apPair = new AttributePercentagePair(key, percentage);

            // Uses binary search (on both descending percentage, and then ascending alpha
            // order) to find the id to insert at
            int id = Collections.binarySearch(attributes, apPair,
                    Comparator.comparing(AttributePercentagePair::getPercentage).reversed()
                            .thenComparing(AttributePercentagePair::getAttribute));
            if (id < 0) {
                // Insert at id
                attributes.add(-(id + 1), apPair);
            }

        }
        double percentage;
        String value;
        // Output each attribute in format: 'attribute: percentage'
        for (AttributePercentagePair attribute : attributes) {
            percentage = attribute.getPercentage();
            value = attribute.getAttribute();
            statsString += String.format("%s: %.2f\n", value, percentage);
        }

        statsString += "--\n";
        statsString += String.format("average age: %.2f", formatPercentage(ageStatistics.getAverageAgeOfSavedHumans()));
        return statsString;
    }

    // HELPERS
    public void incrementDictValue(HashMap<String, Integer> dict, String key) {
        dict.put(key, dict.getOrDefault(key, 0) + 1);
    }

    public static Double formatPercentage(double percentage) {
        BigDecimal bd = new BigDecimal(Double.toString(percentage));
        bd = bd.setScale(2, RoundingMode.CEILING);
        return bd.doubleValue();
    }

    public void incrementScenariosSeenCount() {
        this.scenariosSeenCount += 1;
    }
}
