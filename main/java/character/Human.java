package main.java.character;

import main.java.RandomGenerator;
import main.java.helper.HelperFunctions;

import java.util.Random;

public class Human extends character implements RandomGenerator {
    // Instance variables
    private String ageCategory;
    private String profession;
    private boolean isPregnant;

    public enum Profession {
        doctor, ceo, homeless, criminal, unemployed, teacher, student, retiree
    }

    // Constructors
    public Human(int age, String gender, String bodyType, String profession, boolean isPregnant) {
        super(age, gender, bodyType);
        setAge(age);
        setProfession(profession);
        setIsPregnant(isPregnant);

    }

    public Human(Boolean randomlyGenerated) {
        super();
        randomGen();
    }

    // Getters
    public String getAgeCategory() {
        return this.ageCategory;
    }

    public String getProfession() {
        return this.profession;
    }

    public boolean getIsPregnant() {
        return this.isPregnant;
    }

    // Setters
    @Override
    public void setAge(int age) {
        super.setAge(age);

        // Also update age category accordingly
        if (age >= 0 && age <= 4) {
            this.ageCategory = "BABY";
        } else if (age >= 5 && age <= 16) {
            this.ageCategory = "CHILD";
        } else if (age >= 17 && age <= 68) {
            this.ageCategory = "ADULT";
        } else if (age >= 68) {
            this.ageCategory = "SENIOR";
        }
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setProfession() {
        this.profession = "none";
    }

    public void setIsPregnant(boolean isPregnant) {
        this.isPregnant = isPregnant;
    }

    // Methods
    public String toString() {
        return (this.getBodyType() + " " + this.getAgeCategory()
                + (this.getAgeCategory() == "ADULT" ? " " + this.getProfession() + " " : " ")
                + (this.getGender() != "unknown" ? this.getGender() : "") + " "
                + (this.getIsPregnant() && this.getGender().equals("female") ? "pregnant" : "")).toLowerCase();
    }

    // RG Methods
    // Generate a new Human with random values for the attributes of the object
    @Override
    public Human randomGen() {
        // Uses randomiser functions
        Random random = new Random();

        // Randomise super attributes
        String thisGender = HelperFunctions.randomFromEnum(character.Gender.class);
        String thisBodyType = HelperFunctions.randomFromEnum(character.BodyType.class);
        int thisAge = random.nextInt(100) + 1;

        // Randomise instance attributes
        String thisProfession = HelperFunctions.randomFromEnum(Profession.class);
        boolean thisIsPregnant;
        if (thisGender.equals("female") && thisAge > 15) {
            thisIsPregnant = HelperFunctions.randomBoolean(25, 75);
        } else {
            thisIsPregnant = false;
        }

        // Call to constructor
        Human human = new Human(thisAge, thisGender, thisBodyType, thisProfession, thisIsPregnant);

        return human;
    }

    // Output the values of the attributes in a specific format
    public String extendedToString() {
        return String.format("%s,%s,%d,%s,%s,%b,,", this.getClass().getSimpleName().toLowerCase(), this.getGender(),
                this.getAge(), this.getBodyType(), this.getProfession(), this.getIsPregnant());
    }
}
