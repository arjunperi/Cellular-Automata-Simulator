package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class RPSModel extends Model{
  private int threshold = 2;
  private int randomVariation = 2;
  private int variable = 0;

  //DEFINE IN PROPERTIES FILES
  Map<Integer, List<Integer>> RPSMap = Map.of(
      0, List.of(1, 2),
      1, List.of(2, 3),
      2, List.of(3, 4),
      3, List.of(4, 0),
      4, List.of(0, 1));

  public RPSModel(String fileName, String modelType) {
    super(fileName, modelType);
    threshold=Integer.parseInt((String)propertyFile.getOrDefault("Threshold", defaultPropertyFile.get("Threshold")));
    randomVariation=Integer.parseInt((String)propertyFile.getOrDefault("Random_Variation", defaultPropertyFile.get("Random_Variation")));
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

}
