package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class RPSModel extends Model{
  private final int threshold;
  private final int randomVariation;
  private static final String MAPPING = "Mapping";
  private int variable = 0;
  Map<Integer, List<Integer>> RPSMap;


  public RPSModel(String fileName, String modelType) {
    super(fileName, modelType);
    try{
      threshold=Integer.parseInt((String)propertyFile.getOrDefault("Threshold", defaultPropertyFile.get("Threshold")));
      randomVariation=Integer.parseInt((String)propertyFile.getOrDefault("Random_Variation", defaultPropertyFile.get("Random_Variation")));
    }
    catch(NumberFormatException e){
      throw new ModelException("Invalid Threshold/Variation Input");
    }
    RPSMap = new HashMap<>();
    try{
      initializeMap();
    }
    catch (NumberFormatException e){
      throw new ModelException("Invalid RPS Mapping Inputs");
    }
  }

  public void updateState(int row, int column, List<Cell> neighbors) {
    Cell currentCell = getCell(row,column);
    Map<Integer, Integer> statesCounts = new HashMap<>();
    for (Cell currentNeighbor : neighbors) {
      int neighborState = currentNeighbor.getCurrentState();
      statesCounts.put(neighborState, statesCounts.getOrDefault(neighborState, 0) + 1);
    }
    int nextState = getMostCommonMapState(statesCounts, currentCell.getCurrentState());
    if (RPSMap.get(currentCell.getCurrentState()).contains(nextState)) {
      currentCell.setFutureState(nextState);
    }
  }

  private int getMostCommonMapState(Map<Integer, Integer> statesCounts, int state) {
    Random random = new Random();
    int mostCommonState = state;
    int maxValue = 0;
    if(randomVariation>0) {
      variable = random.nextInt(randomVariation);
    }
    for (Map.Entry<Integer, Integer> entry : statesCounts.entrySet()) {
      if (entry.getValue().compareTo(maxValue) > 0
          && entry.getValue().compareTo(threshold + variable) > 0 && entry.getKey() != state) {
        maxValue = entry.getValue();
        mostCommonState = entry.getKey();
      }
    }
    return mostCommonState;
  }

  private void initializeMap() {
    for(Integer state:allStates) {
      String mappings =(String)propertyFile.getOrDefault(state + MAPPING, defaultPropertyFile.get(state + MAPPING));
      String[] mappingsArray = mappings.split(",");
      List<Integer> mappingsList = new ArrayList<>();
      for(String mappingString:mappingsArray) {
        mappingsList.add(Integer.parseInt(mappingString));
      }
      RPSMap.put(state, mappingsList);
    }
  }
}
