package Model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import org.assertj.core.data.MapEntry;

public class RPSCell extends Cell {

  public static final int THRESHOLD = 2;
//  Map<Integer, List<Integer>> RPSMap = Map.ofEntries(
//      new AbstractMap.SimpleEntry<>( 0, List.of(1,2,3,4,5,6,7)),
//      new AbstractMap.SimpleEntry<>( 1, List.of(2,3,4,5,6,7,8)),
//      new AbstractMap.SimpleEntry<>( 2, List.of(3,4,5,6,7,8,9)),
//      new AbstractMap.SimpleEntry<>( 3, List.of(4,5,6,7,8,9,10)),
//      new AbstractMap.SimpleEntry<>( 4, List.of(5,6,7,8,9,10,11)),
//      new AbstractMap.SimpleEntry<>( 5, List.of(6,7,8,9,10,11,12)),
//      new AbstractMap.SimpleEntry<>( 6, List.of(7,8,9,10,11,12,13)),
//      new AbstractMap.SimpleEntry<>( 7, List.of(8,9,10,11,12,13,14)),
//      new AbstractMap.SimpleEntry<>( 8, List.of(9,10,11,12,13,14,0)),
//      new AbstractMap.SimpleEntry<>( 9, List.of(10,11,12,13,14,0,1)),
//      new AbstractMap.SimpleEntry<>( 10, List.of(11,12,13,14,0,1,2)),
//      new AbstractMap.SimpleEntry<>( 11, List.of(12,13,14,0,1,2,3)),
//      new AbstractMap.SimpleEntry<>( 12, List.of(13,14,0,1,2,3,4)),
//      new AbstractMap.SimpleEntry<>( 13, List.of(14,0,1,2,3,4,5)),
//      new AbstractMap.SimpleEntry<>( 14, List.of(0,1,2,3,4,5,6)));


  //DEFINE IN PROPERTIES FILES
  Map<Integer, List<Integer>> RPSMap = Map.of(
      0, List.of(1,2),
      1, List.of(2,3),
      2, List.of(3,4),
      3, List.of(4,0),
      4, List.of(0,1));


  public RPSCell(int state, Queue<Cell> emptyQueue) {
    super(state, emptyQueue);
  }

  @Override
  public void updateState(List<Cell> neighbors) {
    Map<Integer, Integer> statesCounts = new HashMap<>();
    for (Cell currentNeighbor : neighbors) {
      int neighborState = currentNeighbor.getCurrentState();
      statesCounts.put(neighborState, statesCounts.getOrDefault(neighborState, 0) + 1);
    }
    int nextState=getMostCommonMapState(statesCounts, this.getCurrentState());
    if(RPSMap.get(this.getCurrentState()).contains(nextState)) {
      this.setFutureState(nextState);
    }
  }

  private int getMostCommonMapState(Map<Integer, Integer> statesCounts, int state) {
    Random random = new Random();
    int mostCommonState = state;
    int maxValue = 0;
    int variable = random.nextInt(2);
    for (Map.Entry<Integer, Integer> entry : statesCounts.entrySet()) {
      if (entry.getValue().compareTo(maxValue) > 0
          && entry.getValue().compareTo(THRESHOLD+variable) > 0 && entry.getKey()!=state) {
        maxValue = entry.getValue();
        mostCommonState = entry.getKey();
      }
    }
    return mostCommonState;
  }
}
