package main.java.character;

public class Human extends character {
    // Instance variables
    private String ageCategory;
    private String profession;
    private boolean isPregnant;

    // Constructors
    public Human(int age, String gender, String bodyType, String profession, boolean isPregnant) {
        super(age, gender, bodyType);
        setAge(age);
        setProfession(profession);
        setIsPregnant(isPregnant);

    }

    // Getters
    public String getAgeCategory() {
        return this.ageCategory;
    }

    public String getProfession() {
        return this.profession;
    }

    public boolean getIsPregant() {
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

    public void setIsPregnant(boolean isPregnant) {
        this.isPregnant = isPregnant;
    }

    // Methods
    public String toString() {
        return (this.getBodyType() + " " + this.getAgeCategory()
                + (this.getAgeCategory() == "ADULT" ? " " + this.getProfession() + " " : " ")
                + (this.getGender() != "UNKNOWN" ? this.getGender() : "") + " "
                + (this.getIsPregant() && this.getGender() == "Female" ? "pregnant" : "")).toLowerCase();
    }
}
