package user;

import java.util.HashMap;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import model.scenario.Scenario;
import statistics.Statistics;
import model.location.Location;
import model.scenario.ScenarioLoader;
import decision.DecisionAlgorithm;
import decision.simpleDecision;
// import RescueBot;
import statistics.AuditLog;

public class User {
    // Instance Variables
    private boolean givesLogConsent;
    private Statistics statistics;
    // private ArrayList<Statistics> allStatistics
    private ArrayList<Scenario> scenarios;
    private ScenarioLoader scenarioLoader;
    private String logPath;
    private boolean scenarioFileExists;
    private AuditLog log;
    private Boolean isAlgorithm;
    public DecisionAlgorithm decisionAlgo = new simpleDecision();

    final int SCENARIOS_PER_RUN = 3;

    // Constructors

    public User(String logFilePath, String scenarioFilePath) {
        // Load Scenarios either thru RSG (Random Scenario generation) or from file
        // CHECK IF scenarioFilePath is empty!!

        setLogPath(logFilePath);
        this.log = new AuditLog(logFilePath);
        // this.log = new AuditLog(this.getLogFilePath());
        if (scenarioFilePath == null) {
            // RSG
            this.scenarioFileExists = false;
            setScenarios();
            setScenarioLoader();
            setScenariosFromRSG();
        } else {
            this.scenarioFileExists = true;
            setScenarioLoader(scenarioFilePath);
            try {
                setScenariosFromFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Getters
    public Statistics getStatistics() {
        return this.statistics;
    }

    public boolean getFileExists() {
        return this.scenarioFileExists;
    }

    public ArrayList<Scenario> getScenarios() {
        return this.scenarios;
    }

    public Scenario getScenario(int id) {
        return getScenarios().get(id);
    }

    public AuditLog getLog() {
        return this.log;
    }

    public ScenarioLoader getScenarioLoader() {
        return this.scenarioLoader;
    }

    public boolean getGivesLogConsent() {
        return this.givesLogConsent;
    }

    public String getLogFilePath() {
        return this.logPath;
    }

    // // Setters
    public void setStatistics() {
        this.statistics = new Statistics();
    }

    public void setScenariosFromFile() throws FileNotFoundException {
        scenarios = scenarioLoader.loadScenariosFromFile();
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public void setLog(AuditLog log) {
        this.log = log;
    }

    public void setScenarios() {
        this.scenarios = new ArrayList<Scenario>();
    }

    public void setScenariosFromRSG() {
        // Set scenarios arraylist
        ArrayList<Scenario> scenarios = this.getScenarios();
        this.addScenarios(scenarioLoader.loadScenariosFromRSG(this.getScenarios(), 3));
    }

    public void setScenarioLoader(String filePath) {
        this.scenarioLoader = new ScenarioLoader(filePath);
    }

    // RSG
    public void addScenarios(ArrayList<Scenario> scenarios) {
        this.scenarios.addAll(scenarios);
    }

    public void setScenarioLoader() {
        this.scenarioLoader = new ScenarioLoader();
    }

    public void setGivesLogConsent(boolean givesLogConsent) {
        this.givesLogConsent = givesLogConsent;
    }

    // Methods
    public void updateGivesLogConsent(Scanner keyboard) {
        String consentResponse = null;

        // Initial consent prompt
        System.out.print("Do you consent to have your decisions saved to a file? (yes/no)\n> ");
        consentResponse = keyboard.nextLine().toLowerCase();
        // keyboard.nextLine();

        // Loops until valid "yes" / "no" reponse given to consent prompt
        do {
            if (consentResponse == null) {
                System.out
                        .print("Invalid response! Do you consent to have your decisions saved to a file? (yes/no)\n> ");
                consentResponse = keyboard.nextLine().toLowerCase();
            }
            // Process consent response and update accordingly
            switch (consentResponse) {
                case "yes":
                case "y":
                    setGivesLogConsent(true);
                    break;
                case "no":
                case "n":
                    setGivesLogConsent(false);
                    break;
                default:
                    // TODO: Change to "an InvalidInputException should be thrown and the user
                    // should be prompted again"
                    consentResponse = null;
                    break;
            }
        } while (consentResponse == null);
    }

    public void handleSimulateScenarios(Scanner keyboard) {
        
        // Machines have no rights to consent
        givesLogConsent = true;
        // Initiate new statistics
        setStatistics();
        int N; // Number of scenarios to simulate

        if (this.scenarioFileExists) {
            // Scenario count and scenarios seen count
            N = scenarioLoader.getScenarioCount();
        } else {
            // Prompt user for N number of scenarios to simulate
            System.out.println("How many scenarios should be run?");
            System.out.print("> ");
            N = keyboard.nextInt();
            keyboard.nextLine();
            
            // Reprompt user for N number of scenarios to simulate if invalid N given
            while (N < 1 || N > 100){   // Hardcoded as 100 scenario maximum for now
                System.out.println("Invalid input! How many scenarios should be run?\n> ");
                N = keyboard.nextInt();
                keyboard.nextLine();
            }

        }

        int scenSeenCount = statistics.getScenariosSeenCount();

        // Start writing to log
        log.writeLogHeader("Algorithm");
        if (!scenarioFileExists) {
            scenarioLoader.loadScenariosFromRSG(this.getScenarios(), N);
        }

        // Loop until "no" given or out of scenarios
        // do {
        //     // Generate Scenarios
        //     if (!scenarioFileExists) {
        //         scenarioLoader.loadScenariosFromRSG(this.getScenarios());
        //     }

        simulateScenarios(scenSeenCount, N);

        //     // Break if user has seen all scenarios
            // if (scenCount == scenSeenCount && this.getFileExists()) {
            //     cont = "no";

            //     // Output FINAL statistics
            //     System.out.println(showStatistics());
            //     break;
            // }

        // show sumlation statistics
        scenSeenCount = statistics.getScenariosSeenCount();
        System.out.println(showStatistics());


        // } while (!cont.equals("no"));

        // Finally, return to main menu
        System.out.print("That's all. Press Enter to return to main menu.\n> ");
        keyboard.nextLine();
    }
    
    public int decide(Scenario scenario){
        return decisionAlgo.decide(scenario);
    }
    
    public void simulateScenarios(int scenSeenCount, int N) {
        // id of Location value for each scenarios
        int deployTo;
        int choiceRange; // Number of locations to pick from at scenario

        // Number of scenarios seen resets after every 3

        // Auto stop at N
        // lastID = this.getScenarios().size();
        // System.out.println("lastID = " + lastID);
        // scenCount = scenSeenCount;
        // System.out.println("seen = " + scenSeenCount);
        

        for (int i = 0; i < N; i++) {
            Scenario scenario;
            
            scenario = this.getScenario(i);

            // Print Scenario details
            // System.out.println(scenario.getLocationCount());
            // System.out.println(scenario.toString());

            // Location count for scenario (upper bound of range)
            // choiceRange = scenario.getLocations().size();

            // Prompt user for judgement
            // System.out.print("To which location should RescueBot be deployed?\n> ");
            // deployTo = keyboard.nextInt();
            // keyboard.nextLine();
            deployTo = this.decide(scenario);

            // Continue prompting if invalid location id (deployTo) given
            // while (deployTo < 1 || deployTo > choiceRange) {
            //     System.out.print("Invalid response! To which location should RescueBot be deployed?\n> ");
            //     deployTo = keyboard.nextInt();
            //     keyboard.nextLine();
            // }

            // Update their statistics and toThreeCount
            updateStatistics(scenario, deployTo, givesLogConsent);

        }
    }

    public void handleJudgeScenarios(Scanner keyboard) {
        // First asks for consent and updates the users givesLogConsent value
        updateGivesLogConsent(keyboard);

        // Handle consent or not with get
        // Initiate new statistics
        setStatistics();

        // Scenario count and scenarios seen count
        int scenCount = scenarioLoader.getScenarioCount();

        int scenSeenCount = statistics.getScenariosSeenCount();

        String cont = "yes"; // Continue value

        // Start writing to log
        if (this.givesLogConsent) {
            log.writeLogHeader("User");
        }

        // Loop until "no" given or out of scenarios
        do {
            // Generate Scenarios
            if (!scenarioFileExists) {
                scenarioLoader.loadScenariosFromRSG(this.getScenarios(), 3);
            }

            // Call judge scenarios
            if (cont.equals("yes")) {
                judgeScenarios(keyboard, scenSeenCount);

                // Update seen scenarios count
                scenSeenCount = statistics.getScenariosSeenCount();
            }

            // Break if user has seen all scenarios
            if (scenCount == scenSeenCount && this.getFileExists()) {
                cont = "no";

                // Output FINAL statistics
                System.out.println(showStatistics());
                break;
            }

            // show current statistics
            System.out.println(showStatistics());

            // Prompt REPEAT ("yes") or RETURN ("no")
            System.out.println("Would you like to continue? (yes/no)");
            System.out.print("> ");
            cont = keyboard.nextLine();

        } while (!cont.equals("no"));

        // Finally, return to main menu
        System.out.print("That's all. Press Enter to return to main menu.\n> ");
        keyboard.nextLine();

    }

    public void judgeScenarios(Scanner keyboard, int scenSeenCount) {
        // id of Location value for each scenarios
        int deployTo;
        int choiceRange; // Number of locations to pick from at scenario

        // Number of scenarios seen in session
        // int scenCount = scenSeenCount;

        // Number of scenarios seen resets after every 3
        int toThreeCount = 0;
        int lastID;

        // Auto stop at lastID (never if RSG)
        if (scenarioFileExists) {
            lastID = this.getScenarios().size();
            // System.out.println("lastID = " + lastID);
            // scenCount = scenSeenCount;
            // System.out.println("seen = " + scenSeenCount);
        } else {
            lastID = 999; // Will never reach
            // scenSeenCount = this.getScenarios().size() - 3;
        }

        for (int i = scenSeenCount; i < lastID
                && toThreeCount < 3; i++) {
            Scenario scenario;

            // // If file
            // if (this.getFileExists()) {
            // scenario = this.getScenario(i);
            // }

            // // Else RSG
            // else {
            // scenario = this.getScenario(i);
            // }
            scenario = this.getScenario(i);

            // Print Scenario details
            System.out.println(scenario.getLocationCount());
            System.out.println(scenario.toString());

            // Location count for scenario (upper bound of range)
            choiceRange = scenario.getLocations().size();

            // Prompt user for judgement
            System.out.print("To which location should RescueBot be deployed?\n> ");
            deployTo = keyboard.nextInt();
            keyboard.nextLine();

            // Continue prompting if invalid location id (deployTo) given
            while (deployTo < 1 || deployTo > choiceRange) {
                System.out.print("Invalid response! To which location should RescueBot be deployed?\n> ");
                deployTo = keyboard.nextInt();
                keyboard.nextLine();
            }

            // Update their statistics and toThreeCount
            updateStatistics(scenario, deployTo, givesLogConsent);
            toThreeCount++;

        }
    }

    public void updateStatistics(Scenario scenario, int choice, boolean givesLogConsent) {
        statistics.updateSeen(scenario);
        statistics.updateSaved(scenario, choice);
        statistics.updateAgeStatistics(scenario, choice);

        // Only append to logfile if consent is given
        if (givesLogConsent) {
            log.writeDecision(scenario, choice);
        }

    }

    public String showStatistics() {
        return this.statistics.displayStats(false);
    }

}
