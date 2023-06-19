package model.scenario;

import model.scenario.Scenario;
import statistics.Statistics;
import model.location.Location;
import model.character.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScenarioLoader {

    private File scenarioFile;
    private int scenarioCount;

    public ScenarioLoader(String filePath) {
        this.scenarioFile = new File(filePath);
        setScenarioCount();
        // System.out.println(filePath);
    }

    // RSG ScenarioLoader
    public ScenarioLoader() {
        // this.scenarioFile = new File();
        setScenarioCount();
    }

    // Getters
    public int getScenarioCount() {
        return this.scenarioCount;
    }

    // Setters
    public void setScenarioCount() {
        this.scenarioCount = 0;
    }

    public void setScenarioCount(int add) {
        this.scenarioCount = this.scenarioCount + add;
    }

    public ArrayList<Scenario> loadScenariosFromRSG(ArrayList<Scenario> scenarios, int N) {

        // Scenario curScenario = null;
        // Location curLocation = null;
        int outOfNCount = 0;

        while (outOfNCount < N) {
            // Generate a scenario and add to scenarios
            // Scenario
            Scenario rgScen = new Scenario(true);
            Scenario scenario = rgScen.randomGen();
            scenarios.add(scenario);
            outOfNCount++;
        }
        setScenarioCount(outOfNCount);
        return scenarios;
    }

    public ArrayList<Scenario> loadScenariosFromFile() throws FileNotFoundException {
        ArrayList<Scenario> scenarios = new ArrayList<>();
        Scanner scanner = new Scanner(this.scenarioFile);
        Scenario currentScenario = null;
        Location currentLocation = null;
        int lineNumber = 0;
    
        try {
    
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();
    
                // HEADER LINE: skip
                if (line.startsWith(",")) {
                    continue;
                }
    
                try {
                    // Check for invalid data format
                    if (line.split(",").length != 8) {
                        throw new InvalidDataFormatException("Invalid data format in line " + lineNumber);
                    }
    
                    // Handle different types of lines
                    if (line.startsWith("scenario:")) {
                        // NEW SCENARIO, so save the current one and start a new one
                        if (currentScenario != null) {
                            scenarios.add(currentScenario);
                        }
                        // FORMAT: "scenario:flood,,,,,,,"
                        String scenarioType = line.split(":")[1].split(",")[0].trim();
                        currentScenario = new Scenario(scenarioType);
                        this.scenarioCount++;
    
    
                    } else if (line.startsWith("location:")) {
                        // New location, so add it to the current scenario
                        // FORMAT: "location:13.7154 N;150.9094 W;trespassing,,,,,,,"
                        String locationDetails = line.split(":")[1].trim();
                        String[] locationParts = locationDetails.split(";");
    
                        try {
                            double latVal = Double.parseDouble(locationParts[0].split(" ")[0]);
                            char latCard = locationParts[0].split(" ")[1].charAt(0);
                            double lonVal = Double.parseDouble(locationParts[1].split(" ")[0]);
                            char lonCard = locationParts[1].split(" ")[1].charAt(0);
                            String isTrespassing = (locationParts[2].substring(0, 5).equals("legal") ? "legal" : "trespassing");
                            currentLocation = new Location(latVal, latCard, lonVal, lonCard, isTrespassing);
                            currentScenario.addLocation(currentLocation);
                        } catch (NumberFormatException e) {
                            System.out.println("WARNING: invalid number format in scenarios file in line " + lineNumber);
                            continue;
                        }
    
                        
                    } else {
                        // Character details, so add a new character to the current scenario's current
                        // location
                        // FORMAT: "human,male,71,athletic,none,false,,"
                        String[] characterDetails = line.split(",");
    
                        try {
                            int age = Integer.parseInt(characterDetails[2]);
                            String gender = characterDetails[1];
                            String bodyType = characterDetails[3];
    
                            if (!characterDetails[4].isEmpty()) { // it's a human
                                String profession = characterDetails[4];
                                boolean isPregnant = Boolean.parseBoolean(characterDetails[5]);
                                Human human = new Human(age, gender, bodyType, profession, isPregnant);
                                currentLocation.addCharacter(human);
                            } else if (!characterDetails[6].isEmpty()) { // it's an animal
                                String species = characterDetails[6];
                                boolean isPet = Boolean.parseBoolean(characterDetails[7]);
                                Animal animal = new Animal(age, gender, bodyType, species, isPet);
                                currentLocation.addCharacter(animal);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("WARNING: invalid number format in scenarios file in line " + lineNumber);
                            continue;
                        }
                    }
                } catch (InvalidDataFormatException e) {
                    System.out.println("WARNING: invalid data format in scenarios file in line " + lineNumber);
                    continue;
                }
            }
    
            // Don't forget to add the last scenario
            if (currentScenario != null) {
                scenarios.add(currentScenario);
            }
    
            scanner.close();
    
            return scenarios;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return scenarios;
    }
    

}
