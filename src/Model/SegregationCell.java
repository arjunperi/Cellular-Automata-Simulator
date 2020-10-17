package Model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SegregationCell extends Cell{
  public static final int[][] POSSIBLE_NEIGHBORS_SEGREGATION = new int[][]{{-1, 0}, {-1, 1},
      {0, 1}, {1, 1}, {1, 0}, {1, -1},
      {0, -1}, {-1, -1}};
  public static final int EMPTY = 0;
  public static final double PERCENT_SIMILAR = .5;
  private final Queue<Cell> emptyQueue;

  public SegregationCell(int state, Queue<Cell> emptyQueue) {
    super(state, emptyQueue);
    this.emptyQueue=emptyQueue;
  }

  @Override
  public void updateState(List<Cell> neighbors) {
    if (this.getCurrentState() != EMPTY) {
      double percentSimilarInNeighborhood = getPercentageSimilar(neighbors);
      if(percentSimilarInNeighborhood<=PERCENT_SIMILAR) {
        if(!emptyQueue.isEmpty()) {
          emptyQueue.poll().setFutureState(this.getCurrentState());
          this.setFutureState(EMPTY);
          emptyQueue.add(this);
        }
      }
    }
  }

  private double getPercentageSimilar(List<Cell> neighbors) {
    int countSimilar = 0;
    int countOther = 0;
    for (Cell neighbor : neighbors) {
      if(neighbor.getCurrentState()!=EMPTY) {
        if (neighbor.getCurrentState() == this.getCurrentState()) {
          countSimilar++;
        } else {
          countOther++;
        }
      }
    }
    if(countSimilar+countOther>0) {
      return (double)countSimilar/(double)(countSimilar+countOther);
    }
    return 0;
  }

  @Override
  public int[][] getPossibleNeighbors() {
    return POSSIBLE_NEIGHBORS_SEGREGATION;
  }
}
