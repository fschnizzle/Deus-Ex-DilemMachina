package main.java.scenario;

import java.util.ArrayList;

import main.java.location.Location;

public class Scenario {
    // Instance Variables
    private String emergencyDescription;
    private ArrayList<Location> locations;
    private int locationCount;
    // TESTING
    // public HashMap<String, Integer> seenDict;

    // Constructor
    public Scenario(String emergencyDescription) {
        setEmergencyDescription(emergencyDescription);
        setLocations();
    }

    // Getters
    public String getEmergencyDescription() {
        return this.emergencyDescription;
    }

    public Location getLocation(int index) {
        if (index >= 0 && index < locations.size() - 1) {
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

}
