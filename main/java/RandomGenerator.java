package main.java;

import main.java.character.Animal;
import main.java.helper.HelperFunctions;

public abstract interface RandomGenerator {
    // Generate random values for the attributes of the object
    public Animal randomGen();

    // Output the values of the attributes in a specific format
    public String extendedToString();
}