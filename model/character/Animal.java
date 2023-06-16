package model.character;

import model.RandomGenerator;
import helper.HelperFunctions;

import java.util.Random;

public class Animal extends character implements RandomGenerator {
    // Instance variables
    private String species;
    private boolean isPet;

    public enum Species {
        dog, koala, wallaby, kangaroo, chicken, snake, ferret, cockatoo, tiger, cat
    }

    // Constructors
    public Animal(int age, String gender, String bodyType, String species, boolean isPet) {
        super(age, gender, bodyType);
        setSpecies(species);
        setIsPet(isPet);
    }

    public Animal(Boolean randomlyGenerated) {
        super();
        randomGen();
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

    // RG Methods

    // Generate a new Animal with random values for the attributes of the object
    @Override
    public Animal randomGen() {
        // Uses randomiser functions
        Random random = new Random();

        // Randomise super attributes
        String thisGender = HelperFunctions.randomFromEnum(character.Gender.class);
        String thisBodyType = HelperFunctions.randomFromEnum(character.BodyType.class);
        int thisAge = random.nextInt(50) + 1;

        // Randomise instance attributes
        String thisSpecies = HelperFunctions.randomFromEnum(Species.class);
        boolean thisIsPet = HelperFunctions.randomBoolean(25, 75);

        // Call to constructor
        Animal animal = new Animal(thisAge, thisGender, thisBodyType, thisSpecies, thisIsPet);

        return animal;
    }

    // Output the values of the attributes in a specific format
    @Override
    public String extendedToString() {
        // return "" + + + + + +;
        // return (this.getSpecies() + (this.getIsPet() ? " is pet" :
        // "")).toLowerCase();
        return String.format("%s,%s,%d,%s,,,%s,%b", this.getClass().getSimpleName().toLowerCase(), this.getGender(),
                this.getAge(), this.getBodyType(), this.getSpecies(), this.getIsPet());
    }
}
