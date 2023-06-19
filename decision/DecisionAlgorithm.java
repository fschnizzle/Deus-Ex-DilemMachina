package decision;

import model.scenario.Scenario;

public abstract interface DecisionAlgorithm {

    // Method to be implemented by classes that implement the interface
    public int decide(Scenario scenario);
}
