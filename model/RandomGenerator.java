
package model;

import model.character.Animal;
import helper.HelperFunctions;

public abstract interface RandomGenerator<T> {
    // Generate random values for the attributes of the object
    public T randomGen();

    // Output the values of the attributes in a specific format
    public String extendedToString();
}