package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RPSCell extends Cell {

  public static final List<Integer> POSSIBLE_STATES = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
  public static final int[][] POSSIBLE_NEIGHBORS_RPS = new int[][]{{-1, 0}, {-1, 1}, {0, 1}, {1, 1},
      {1, 0}, {1, -1},
      {0, -1}, {-1, -1}};
  public static final int THRESHOLD = 3;

  public RPSCell(int state) {
    super(state);
  }

  @Override
  public void updateState(List<Cell> neighbors) {
    Map<Integer, Integer> statesCounts = new HashMap<>();
    for (Cell currentNeighbor : neighbors) {
      int neighborState = currentNeighbor.getCurrentState();
      statesCounts.put(neighborState, statesCounts.getOrDefault(neighborState, 0) + 1);
    }
    int nextState = getMostCommonMapState(statesCounts, this.getCurrentState());
    this.setFutureState(nextState);
  }

  private int getMostCommonMapState(Map<Integer, Integer> statesCounts, int state) {
    Random random = new Random();
    int mostCommonState = state;
    int maxValue = 0;
    int variable = random.nextInt(2);
    for (Map.Entry<Integer, Integer> entry : statesCounts.entrySet()) {
      if (entry.getValue().compareTo(maxValue) > 0
          && entry.getValue().compareTo(THRESHOLD + variable) > 0) {
        maxValue = entry.getValue();
        mostCommonState = entry.getKey();
      }
    }
    return mostCommonState;
  }

  @Override
  public int[][] getPossibleNeighbors() {
    return POSSIBLE_NEIGHBORS_RPS;
  }

  @Override
  public void initializePossibleStates() {
    this.setPossibleStates(new ArrayList<>(Arrays.asList(0,1,2,3,4)));
  }
}
