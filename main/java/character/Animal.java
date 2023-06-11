package main.java.character;

public class Animal extends Character {
    // Instance variables
    private String species;
    private boolean isPet;

    // Constructors
    public Animal(int age, String gender, String bodyType, String species, boolean isPet) {
        super(age, gender, bodyType);
        setSpecies(species);
        setIsPet(isPet);
    }

    // Getters
    public String getSpecies() {
        return this.species;
    }

    public boolean getIsPet() {
        return this.isPet;
    }

    // Setters
    public void setSpecies(String species) {
        this.species = species;
    }

    public void setIsPet(boolean isPet) {
        this.isPet = isPet;
    }

    // Methods
    public String toString() {
        return (this.getSpecies() + (this.getIsPet() ? " is pet" : "")).toLowerCase();
    }
}
