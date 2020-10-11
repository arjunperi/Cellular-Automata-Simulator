package Model;

import java.util.List;

public class PercolationCell extends Cell {

  public static final int[][] POSSIBLE_NEIGHBORS_PERCOLATION = new int[][]{{-1, 0}, {0, 1}, {1, 0},
      {0, -1}};

  public PercolationCell(int state) {
    super(state);
  }

  @Override
  public void updateState(List<Cell> neighbors) {
    if (this.getCurrentState() == 0) {
      for (Cell currentNeighbor : neighbors) {
        int neighborState = currentNeighbor.getCurrentState();
        if (neighborState == 1) {
          this.setFutureState(neighborState);
        }
      }
    }
  }

  @Override
  public int[][] getPossibleNeighbors() {
    return POSSIBLE_NEIGHBORS_PERCOLATION;
  }

}
