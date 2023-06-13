package main.java.character;

public abstract class character {
    // Base Variables
    private int age;
    private String gender;
    private String bodyType;

    // Constructor
    public character(int age, String gender, String bodyType) {
        setAge(age);
        setGender(gender);
        setBodyType(bodyType);
    }

    // public character() {
    //     setAge(age);
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
}
