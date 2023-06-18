package statistics;

import java.io.*;
import java.util.*;
import model.scenario.Scenario;
import model.character.*;
import model.location.Location;
import statistics.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class AuditLog {
    private String filename;
    // private String logFile;
    private HashMap<String, Integer> seen;
    private HashMap<String, Integer> saved;
    public Statistics statistic;
    public String userOrAlgo;

    public AuditLog(String filename) {
        this.filename = filename;
        this.seen = new HashMap<>();
        this.saved = new HashMap<>();
        this.statistic = new Statistics();
    }

    public void writeLogHeader(String userOrAlgo) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(userOrAlgo + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the audit log.");
            e.printStackTrace();
        }
    }

    // Writes decisions to a file using the extendedToString method of scenarios.
    public void writeDecision(Scenario scenario, int decisionLocID) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            // writer.write("User\n");
            // for (Scenario scenario : scenarios) {
            writer.write(decisionLocID + "\n");
            writer.write(scenario.extendedToString() + "\n");
            // }
            // ("Scenario ID: " + scenarioId + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the audit log.");
            e.printStackTrace();
        }
    }

    public void decisionAudit(String userOrAlgo) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        boolean isUserSection = false;
        this.userOrAlgo = userOrAlgo;
        int chosenLocation = -1; // Initialize chosen location with an invalid index
        int curLocID = 0;
        Scenario scenario = null; // Assuming you have a Scenario class that contains a list of Locations
        // int lineNum = 0;
        String line;

        // Set which of User or Algorithm to NOT record for
        String whileNot;

        if (userOrAlgo == "User") {
            whileNot = "Algorithm";
        } else if (userOrAlgo == "Algorithm") {
            whileNot = "User";
        } else {
            whileNot = ""; // Never Reaches
        }

        String userOrAlgoSection = whileNot;

        for (int i = 0; i < lines.size(); i++) {

            // get next line
            line = lines.get(i);

            // Flip whose section it is (User or Algo)
            if (line.equals(userOrAlgo)) {
                userOrAlgoSection = userOrAlgo;
                // Reset scenario
                scenario = null; // Reset the scenario
                curLocID = 0; // Reset the location id

            } else if (line.equals(userOrAlgo)) {
                userOrAlgoSection = userOrAlgo;
                // Reset scenario
                scenario = null; // Reset the scenario
                curLocID = 0; // Reset the location id
            }

            // Reset decision index
            if (line.matches("\\d+")) {
                chosenLocation = Integer.parseInt(line);
                // System.out.println("updates!");
                // If current scenario exists
                // currentScenario.addLocation(currentLocation);
                continue;
            }

            // Body of scenario (and correct UserOrAlgo)
            if (userOrAlgoSection == userOrAlgo) {
                // Scenario Header
                if (line.startsWith("scenario:")) {
                    scenario = new Scenario(true);
                    scenario.setEmergencyDescription(line);
                    scenario.setLocations();
                    curLocID = 0; // Reset the location id
                    continue;
                }

                // Start of location
                if (line.startsWith("[")) {

                    // Iterate locID
                    curLocID++;

                    Location location = new Location(null);

                    // Location line for loc details
                    String locationDetails = line.split(":")[1].trim();
                    String[] locationParts = locationDetails.split(";");

                    double latVal = Double.parseDouble(locationParts[0].split(" ")[0]);
                    char latCard = locationParts[0].split(" ")[1].charAt(0);
                    double lonVal = Double.parseDouble(locationParts[1].split(" ")[0]);
                    char lonCard = locationParts[1].split(" ")[1].charAt(0);

                    // Get next line
                    i++;
                    line = lines.get(i);
                    String trespassing = line.split(",")[0].trim();
                    // locationParts = locationDetails.split(";");
                    // System.out.println(trespassing);
                    String isTrespassing = (trespassing.equals("legal") ? "legal" : "trespassing");

                    Location curLoc = new Location(latVal, latCard, lonVal, lonCard, isTrespassing);
                    scenario.addLocation(curLoc);
                    // continue;
                }
                // character details
                String[] splitLine = line.split(",", -1);
                // System.out.println(splitLine[0]);
                if (splitLine[0].equals("human") || splitLine[0].equals("animal")) {
                    // System.out.println(line);
                    String gender = splitLine[1];
                    String ageStr = splitLine[2];
                    String bodyType = splitLine[3];
                    int age = ageStr.isEmpty() ? 0 : Integer.parseInt(ageStr);
                    String profession = splitLine[4];
                    boolean pregnant = Boolean.parseBoolean(splitLine[5]);
                    String species = splitLine[6];
                    boolean isPet = Boolean.parseBoolean(splitLine[7]);

                    character character;
                    // Assuming you have a Character class that can take these parameters
                    if (splitLine[0].equals("human")) {
                        character = new Human(age, gender, bodyType, profession, pregnant);
                    } else if (splitLine[0].equals("animal")) {
                        character = new Animal(age, gender, bodyType, species, isPet);
                    } else {
                        // System.out.println();
                        throw new IllegalArgumentException(splitLine[0] + " is Not a human or animal apparently");
                    }
                    // int locationIndex = Integer.parseInt(splitLine[0].substring(1,
                    // splitLine[0].length() - 1));
                    scenario.getLocation(curLocID - 1).addCharacter(character); // Assuming your Location class has an
                                                                                // addCharacter method

                }
                if (line.equals("")) { // Assuming an empty line indicates the end of a scenario
                    isUserSection = false;
                    if (scenario != null) {
                        statistic.updateSeen(scenario);
                        statistic.updateSaved(scenario, chosenLocation);
                        statistic.updateAgeStatistics(scenario, chosenLocation);
                    }

                }
            }
        }
    }

    public void printStatistics() {

        // Print only if the user or algorithm has seen at least 1 scenario
        if (statistic.getScenariosSeenCount() > 0) {
            System.out.println("======================================");
            System.out.println("# " + this.userOrAlgo + " Audit");
            System.out.println("======================================");
            // System.out.println("- % SAVED AFTER " + seen.size() + " RUNS");
            System.out.println(statistic.displayStats(true));
            // System.out.println(statistic.getAgeStatistics().getAverageAgeOfSavedHumans());
            System.out.println();
            // for (Map.Entry<String, Integer> entry : seen.entrySet()) {
            // double percentage = (double) saved.getOrDefault(entry.getKey(), 0) /
            // entry.getValue();
            // System.out.printf("%s: %.2f\n", entry.getKey(), percentage);
            // }
            // System.out.println("======================================");
        }

    }

    public static String getAgeGroup(int age) {
        if (age >= 0 && age <= 4) {
            return ("baby");
        } else if (age >= 5 && age <= 16) {
            return ("child");
        } else if (age >= 17 && age <= 68) {
            return ("adult");
        } else {
            return ("senior");
        }
    }
}
// }

// private void printStatistics(String title, Map<String, Integer> decisions) {
// System.out.println("======================================");
// System.out.println("# " + title);
// System.out.println("======================================");

// // You might want to sort the decisions map here by values before printing.

// for (Map.Entry<String, Integer> entry : decisions.entrySet()) {
// System.out.println(entry.getKey() + ": " + entry.getValue());
// }
// }
// }
