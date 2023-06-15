package main.java.statistics;

public class AgeStatistics {
    private int totalSavedHumanAge;
    private int savedHumanCount;

    public AgeStatistics() {
        this.totalSavedHumanAge = 0;
        this.savedHumanCount = 0;
    }

    public void incrementSavedHumanAge(int age) {
        this.totalSavedHumanAge += age;
        this.savedHumanCount++;
    }

    public double getAverageAgeOfSavedHumans() {
        if (this.savedHumanCount == 0) {
            return 0.0;
        } else {
            return (double) this.totalSavedHumanAge / this.savedHumanCount;
        }
    }
}
