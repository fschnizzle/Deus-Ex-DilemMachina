package model.character;

public abstract class character {
    // Base Variables
    private int age;
    private String gender;
    private String bodyType;

    // Enums
    public enum Gender {
        male, female
    }

    public enum BodyType {
        athletic, average, overweight
    }

    // Constructor
    public character(int age, String gender, String bodyType) {
        setAge(age);
        setGender(gender);
        setBodyType(bodyType);
    }

    public character() {
        setAge();
        setGender();
        setBodyType();
    }

    // public character() {
    // setAge(age);
    // }

    // Getters
    public int getAge() {
        return this.age;
    }

    public String getGender() {
        return this.gender;
    }

    public String getBodyType() {
        return this.bodyType;
    }

    // Setters
    public void setAge(int age) {
        this.age = age;
    }

    public void setAge() { // Default
        this.age = 0;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setGender() { // Default
        this.gender = "UNKNOWN";
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public void setBodyType() { // Default
        this.bodyType = "UNSPECIFIED";
    }

    // Methods
    public abstract String toString(); // Animal & Human overrides this

    public String extendedToString() {
        return "";
    }
    
    private boolean isValidBodyType(String bodyType) {
        // Replace this with your actual validation logic
        return bodyType.equals("athletic") || bodyType.equals("average") || bodyType.equals("overweight");
    }
}
