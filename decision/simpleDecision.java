package decision;

import model.scenario.Scenario;
import model.location.Location;
import java.util.HashMap;

public class simpleDecision implements DecisionAlgorithm{
    
    public int decide(Scenario scenario){
        // Make weights dictionary
        HashMap<String, Double> weights = new HashMap<>();
        HashMap<Integer, Double> locationWeights = new HashMap<>();
        
        // Weights for decision algorithm        
        weights.put("male", 1.0);
        weights.put("female", 1.0);
        weights.put("pet", 0.5);
        weights.put("pregnant", 0.5);
        weights.put("legal", 0.25);
        
        // Loop through locations
        int locID = 1;
        for (Location location : scenario.getLocations()){
            
            // Get location string to parse and initialise weight
            String locString = location.toString();
            Double locWeight = 0.0;
            
            // Parse the string for keywords in the dictionary defined 
            // and add weights to locWeight
            // Split the locString into individual words
            String[] wordsInLocString = locString.split("\\s+");

            // Iterate over each word in locString
            for (String word : wordsInLocString) {
                // If the word is in the weights map, add its weight to locWeight
                if (weights.containsKey(word)) {
                    locWeight += weights.get(word);
                }
            }
            
            locationWeights.put(locID, locWeight);
            locID++;
        }
        
        // Find the location ID with the maximum weight
        Double maxEntry = null;
        int maxID = 1;
        
        for (HashMap.Entry<Integer, Double> entry : locationWeights.entrySet()){
            
            // replace the maxID if current value is higher
            if (maxEntry == null || entry.getValue().compareTo(maxEntry) > 0){
                maxEntry = entry.getValue();
                maxID = entry.getKey();
            }
        }
        
        // Return the ID of the location with the highest weight
        return maxID;
    }
    
}
