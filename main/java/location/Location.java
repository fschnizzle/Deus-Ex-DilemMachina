package main.java.location;

import java.util.ArrayList;

import main.java.character.character;

public class Location {
    // Instance variables
    private Coordinate lat; // Latitude Coordinate (N or S)
    private Coordinate lon; // Longitude Coordinate (E or W)
    private boolean isTrespassing;
    private ArrayList<character> characters;
    private int numCharacters;

    // Lat and Lon sub classes
    static class Coordinate {
        private double value;
        private char cardinality; // 'N' for North, 'S' for South, 'E' for East, 'W' for west

        public Coordinate(double value, char cardinality) {
            this.value = value;
            this.cardinality = cardinality;
        }

        public String toString() {
            return String.format("%.4f %c", value, cardinality);
        }
    }

    // Constructors
    public Location(double latVal, char latCard, double lonVal, char lonCard, boolean isTrespassing) {
        // super(age, gender, bodyType);
        this.lat = new Coordinate(latVal, latCard);
        this.lon = new Coordinate(lonVal, lonCard);
        setIsTrespassing(isTrespassing);
        setCharacters();
    }

    // public Location()

    // Getters
    public boolean getIsTrespassing() {
        return this.isTrespassing;
    }

    public character getCharacter(int index) {
        if (index > 0 && index < characters.size()) {
            return characters.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index out of range for characters list");
        }
    }

    public ArrayList<character> getCharacters() {
        return this.characters;
    }

    public int getNumCharacters() {
        return characters.size();
    }

    // Setters
    public void setIsTrespassing(boolean isTrespassing) {
        this.isTrespassing = isTrespassing;
    }

    public void setCharacter(int index, character character) {
        if (index > 0 && index < characters.size()) {
            this.characters.set(index, character);
        } else {
            throw new IndexOutOfBoundsException("Index out of range for characters list");
        }
    }

    public void addCharacter(character character) {
        this.characters.add(character);
    }

    public void setCharacters(ArrayList<character> characters) {
        this.characters = characters;
    }

    public void setCharacters() {
        this.characters = new ArrayList<character>();
    }

    // Methods
    public String toString() {
        // First Line
        String locationDetails = "Location: ";
        locationDetails += this.lat.toString() + ", ";
        locationDetails += this.lon.toString() + "\n";

        // Second and third Lines
        locationDetails += "Tresspassing: " + (this.isTrespassing ? "yes" : "no") + "\n";
        locationDetails += characters.size() + " Characters:\n";

        // Successive (character) Lines
        for (character entity : characters) {
            locationDetails += "- " + entity.toString() + "\n";
        }

        return locationDetails;
    }
}
