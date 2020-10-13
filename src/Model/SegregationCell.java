package Model;

import java.util.List;

public class SegregationCell extends Cell{
  public static final int[][] POSSIBLE_NEIGHBORS_SEGREGATION = new int[][]{{-1, 0}, {-1, 1},
      {0, 1}, {1, 1}, {1, 0}, {1, -1},
      {0, -1}, {-1, -1}};
  public static final int EMPTY = 0;
  public static final double PERCENT_SIMILAR = .5;

  public SegregationCell(int state) {
    super(state);
  }

  @Override
  public void updateState(List<Cell> neighbors) {
    if (this.getCurrentState() != EMPTY) {
      double percentSimilarInNeighborhood = getPercentageSimilar(neighbors);
      if(percentSimilarInNeighborhood<=PERCENT_SIMILAR) {
        this.setFutureState(EMPTY);
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
