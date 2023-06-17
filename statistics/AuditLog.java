package statistics;

import java.io.*;
import java.util.*;
import model.scenario.Scenario;
import model.character.*;
import model.location.Location;
import statistics.AgeStatistics;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class AuditLog {
    private String filename;
    // private String logFile;
    private HashMap<String, Integer> seen;
    private HashMap<String, Integer> saved;

    public AuditLog(String filename) {
        this.filename = filename;
        this.seen = new HashMap<>();
        this.saved = new HashMap<>();
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
            // writer.write(scenario.extendedToString());
            // }
            // ("Scenario ID: " + scenarioId + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the audit log.");
            e.printStackTrace();
        }
    }

    public void decisionAudit() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        boolean isUserSection = false;
        int chosenLocation = -1; // Initialize chosen location with an invalid index
        for (String line : lines) {
            if (line.equals("User")) {
                isUserSection = true;
                continue;
            }
            if (isUserSection) {
                if (line.matches("\\d+")) {
                    chosenLocation = Integer.parseInt(line);
                    continue;
                }
                String[] splitLine = line.split(",", -1);
                if (splitLine.length > 1) {
                    System.out.println(line);
                    String gender = splitLine[1];
                    String ageStr = splitLine[2];
                    int age = ageStr.isEmpty() ? 0 : Integer.parseInt(ageStr);
                    String profession = splitLine[4];
                    boolean pregnant = Boolean.parseBoolean(splitLine[5]);
                    String species = splitLine[6];
                    boolean isPet = Boolean.parseBoolean(splitLine[7]);

                    String ageGroup = getAgeGroup(age);
                    seen.put(ageGroup, seen.getOrDefault(ageGroup, 0) + 1);
                    seen.put(gender, seen.getOrDefault(gender, 0) + 1);
                    seen.put(profession, seen.getOrDefault(profession, 0) + 1);
                    seen.put(species, seen.getOrDefault(species, 0) + 1);
                    seen.put("pregnant", seen.getOrDefault("pregnant", 0) + (pregnant ? 1 : 0));
                    seen.put("pet", seen.getOrDefault("pet", 0) + (isPet ? 1 : 0));

                    if (splitLine[0].startsWith("[" + chosenLocation + "]")) {
                        saved.put(ageGroup, saved.getOrDefault(ageGroup, 0) + 1);
                        saved.put(gender, saved.getOrDefault(gender, 0) + 1);
                        saved.put(profession, saved.getOrDefault(profession, 0) + 1);
                        saved.put(species, saved.getOrDefault(species, 0) + 1);
                        saved.put("pregnant", saved.getOrDefault("pregnant", 0) + (pregnant ? 1 : 0));
                        saved.put("pet", saved.getOrDefault("pet", 0) + (isPet ? 1 : 0));
                    }
                }
            }
        }
    }

    // // Prints out the final statistic for all users.
    // public void decisionAudit() throws IOException {
    // File file = new File(this.filename);
    // if (!file.exists() || file.length() == 0) {
    // throw new FileNotFoundException("No history found. Press enter to return to
    // main menu.");
    // }

    // BufferedReader reader = new BufferedReader(new FileReader(file));
    // String line;
    // boolean isUserSection = false;
    // boolean scenarioFinished = false;

    // while ((line = reader.readLine()) != null) {
    // if (line.startsWith("User")) {
    // isUserSection = true;
    // scenarioFinished = false;
    // continue;
    // }

    // if (line.startsWith("Algorithm")) {
    // isUserSection = false;
    // continue;
    // }

    // if (isUserSection && !scenarioFinished) {
    // String[] splitLine = line.split(",", -1);
    // if (splitLine.length > 1 && !line.startsWith("scenario:")) {
    // // process attributes
    // String gender = splitLine[1];
    // System.out.println("attribute: " + splitLine[0]);
    // int age = Integer.parseInt(splitLine[2]);
    // String profession = splitLine[4];
    // boolean pregnant = Boolean.parseBoolean(splitLine[5]);
    // String species = splitLine[6];
    // boolean isPet = Boolean.parseBoolean(splitLine[7]);

    // String ageGroup = getAgeGroup(age);
    // seen.put(ageGroup, seen.getOrDefault(ageGroup, 0) + 1);
    // seen.put(gender, seen.getOrDefault(gender, 0) + 1);
    // seen.put(profession, seen.getOrDefault(profession, 0) + 1);
    // seen.put(species, seen.getOrDefault(species, 0) + 1);
    // seen.put("pregnant", seen.getOrDefault("pregnant", 0) + (pregnant ? 1 : 0));
    // seen.put("pet", seen.getOrDefault("pet", 0) + (isPet ? 1 : 0));
    // } else if (!line.isEmpty() && !line.startsWith("scenario:")) {
    // // This line indicates a decision
    // String[] decision = line.split(" ");
    // int chosenLocation = Integer.parseInt(decision[0]);
    // saved.put(String.valueOf(chosenLocation),
    // saved.getOrDefault(String.valueOf(chosenLocation), 0) + 1);
    // scenarioFinished = true;
    // }
    // }
    // }

    // reader.close();
    // printStatistics();
    // }

    public void printStatistics() {
        System.out.println("======================================");
        System.out.println("# User Audit");
        System.out.println("======================================");
        System.out.println("- % SAVED AFTER " + seen.size() + " RUNS");
        for (Map.Entry<String, Integer> entry : seen.entrySet()) {
            double percentage = (double) saved.getOrDefault(entry.getKey(), 0) / entry.getValue();
            System.out.printf("%s: %.2f\n", entry.getKey(), percentage);
        }
        System.out.println("======================================");
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
