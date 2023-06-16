package model.scenario;

import java.util.ArrayList;

import model.RandomGenerator;
import model.location.Location;
import java.util.Random;
import helper.HelperFunctions;

public class Scenario implements RandomGenerator {
    // Instance Variables
    private String emergencyDescription;
    private ArrayList<Location> locations;
    private int locationCount;

    public enum EmergencyDescription {
        fire,
        flood,
        earthquake,
        hurricane,
        cyclone
    }

    // Constructor
    public Scenario(String emergencyDescription) {
        setEmergencyDescription(emergencyDescription);
        setLocations();
    }

    public Scenario(Boolean randomlyGenerated) {
        // Creates new scenario using ENUM attributes
    }

    // Getters
    public String getEmergencyDescription() {
        return this.emergencyDescription;
    }

    public Location getLocation(int index) {
        if (index >= 0 && index < locations.size()) {
            return locations.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index out of range for locations list");
        }
    }

    public ArrayList<Location> getLocations() {
        return this.locations;
    }

    public int getLocationCount() {
        return locations.size();
    }

    // Setters
    public void setEmergencyDescription(String emergencyDescription) {
        this.emergencyDescription = emergencyDescription;
    }

    public void setLocation(int index, Location location) {
        if (index > 0 && index < locations.size()) {
            this.locations.set(index, location);
        } else {
            throw new IndexOutOfBoundsException("Index out of range for locations list");
        }
    }

    public void addLocation(Location location) {
        this.locations.add(location);
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public void setLocations() {
        this.locations = new ArrayList<Location>();
        ;
    }

    // Methods

    public String toString() {
        int locCounter = 1;
        String scenarioOutput = "======================================\n";
        scenarioOutput += "# Scenario: " + this.emergencyDescription + "\n";
        scenarioOutput += "======================================\n";
        // Loop over locations
        for (Location location : locations) {
            scenarioOutput += "[" + locCounter + "] ";
            scenarioOutput += location.toString();
            locCounter++;
        }

        return scenarioOutput;
    }

    // RG Methods
    @Override
    public Scenario randomGen() {
        Random random = new Random();

        // Implementation of generateRandomScenario() goes here
        String thisEmergencyDescription = HelperFunctions.randomFromEnum(EmergencyDescription.class);

        // Randomise locations list
        ArrayList<Location> thisLocations = new ArrayList<>();
        int numLocations = random.nextInt(3) + 2; // Generate between 1 and 5 locations

        for (int i = 0; i < numLocations; i++) {
            Location locationRandomizer = new Location(true);
            Location newLocation = locationRandomizer.randomGen();
            thisLocations.add(newLocation);
        }

        // Create the new Scenario object
        Scenario scenario = new Scenario(thisEmergencyDescription);
        scenario.setLocations(thisLocations);
        return scenario;
    }

    @Override
    public String extendedToString() {
        // Initialise scenario header
        String scenString = String.format("scenario:%s,,,,,,,\n", this.emergencyDescription);

        // Add each location (and respectives characters) to scenString
        int locID = 0;
        for (Location location : this.locations) {
            locID++;
            scenString += String.format("[%d] ", locID) + location.extendedToString();
        }
        return scenString;
    }

}
