package model.location;

import model.character.character;
import model.RandomGenerator;

import java.util.ArrayList;
import java.util.Random;
import model.character.Animal;
import model.character.Human;

public class Location implements RandomGenerator {
    // Instance variables
    private Coordinate lat; // Latitude Coordinate (N or S)
    private Coordinate lon; // Longitude Coordinate (E or W)
    private String isTrespassing;
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
    public Location(double latVal, char latCard, double lonVal, char lonCard, String isTrespassing) {
        this.lat = new Coordinate(latVal, latCard);
        this.lon = new Coordinate(lonVal, lonCard);
        setIsTrespassing(isTrespassing);
        setCharacters();
    }

    public Location(Boolean randomlyGenerated) {
        // Creates new location using ENUM attributes and RNG
        setCharacters();
    }

    // Getters
    public String getIsTrespassing() {
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
    public void setIsTrespassing(String isTrespassing) {
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
        locationDetails += "Trespassing: " + (this.isTrespassing == "trespassing" ? "yes" : "no") + "\n";
        locationDetails += characters.size() + " Characters:\n";

        // Successive (character) Lines
        for (character entity : characters) {
            locationDetails += "- " + entity.toString() + "\n";
        }

        return locationDetails;
    }

    // RG Methods
    @Override
    public Location randomGen() {
        Random random = new Random();

        // Randomize latitude and longitude
        double latVal = random.nextDouble() * 180.0 - 90.0; // Latitude is between -90 and 90 degrees
        char latCard = random.nextBoolean() ? 'N' : 'S';
        double lonVal = random.nextDouble() * 360.0 - 180.0; // Longitude is between -180 and 180 degrees
        char lonCard = random.nextBoolean() ? 'E' : 'W';

        Coordinate thisLat = new Coordinate(latVal, latCard);
        Coordinate thisLon = new Coordinate(lonVal, lonCard);

        // Randomize isTrespassing
        String thisIsTrespassing = (random.nextBoolean() ? "trespassing" : "legal");

        // Randomize characters list
        ArrayList<character> thisCharacters = new ArrayList<character>();
        int numCharacters = random.nextInt(5) + 1; // Generate between 1 and 5 characters
        for (int i = 0; i < numCharacters; i++) {
            Human humanRandomizer = new Human(true);
            Animal animalRandomizer = new Animal(true);
            if (random.nextBoolean()) {
                Animal newAnimal = animalRandomizer.randomGen();
                thisCharacters.add(newAnimal);
            } else {
                Human newHuman = humanRandomizer.randomGen();
                thisCharacters.add(newHuman);
            }

        }
        Location location = new Location(latVal, latCard, lonVal, lonCard, thisIsTrespassing);
        location.setCharacters(thisCharacters);
        return location;
    }

    public String extendedToString() {
        String locString = String.format("location:%s;%s;\n%s,,,,,,,\n", this.lat.toString(), this.lon.toString(),
                (this.getIsTrespassing() == "trespassing" ? "trespassing" : "legal"));
        for (character entity : this.getCharacters()) {
            locString += entity.extendedToString() + "\n";
        }

        return locString;
    }
}
