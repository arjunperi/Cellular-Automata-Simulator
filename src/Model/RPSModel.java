package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import org.assertj.core.data.MapEntry;

public class RPSModel extends Model{

  public static final int[][] POSSIBLE_NEIGHBORS_RPS = new int[][]{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1},
      {0, -1}, {-1, -1}};
  public static final int THRESHOLD = 3;

  public RPSModel(String fileName) {
    super(fileName);
  }

  public RPSModel(String fileName, String fileOut) {
    super(fileName, fileOut);
  }

  protected List<List<Integer>> getNeighbors(int x, int y) {
    return processNeighbors(x,y,POSSIBLE_NEIGHBORS_RPS);
  }

  @Override
  public void updateCell(int row, int column) {
    List<List<Integer>> neighbors = getNeighbors(row, column);
    Cell cell = gridOfCells.getCell(row,column);
    Map<Integer,Integer> statesCounts = new HashMap<>();
    for(List<Integer> currentNeighbor:neighbors) {
      int neighborState=getCellState(currentNeighbor.get(0), currentNeighbor.get(1));
      statesCounts.put(neighborState, statesCounts.getOrDefault(neighborState,0)+1);
    }
    int nextState = getMostCommonMapState(statesCounts,cell.getCurrentState());
    cell.setFutureState(nextState);
  }
  private int getMostCommonMapState(Map<Integer,Integer> statesCounts, int state) {
    Random random = new Random();
    int mostCommonState = state;
    int maxValue=0;
    int variable = random.nextInt(2);
    for (Map.Entry<Integer,Integer> entry : statesCounts.entrySet()) {
      if (entry.getValue().compareTo(maxValue) > 0 && entry.getValue().compareTo(THRESHOLD+variable)>0) {
        maxValue=entry.getValue();
        mostCommonState=entry.getKey();
      }
    }
    return mostCommonState;
  }

}
