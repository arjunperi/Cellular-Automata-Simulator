package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class PercolationCell extends Cell {

  private static final int FULL = 1;
  private static final int EMPTY = 0;

  public PercolationCell(int state, Queue<Cell> emptyQueue) {
    super(state, emptyQueue);
  }

  @Override
  public void updateState(List<Cell> neighbors) {
    if (this.getCurrentState() == EMPTY) {
      for (Cell currentNeighbor : neighbors) {
        int neighborState = currentNeighbor.getCurrentState();
        if (neighborState == FULL) {
          this.setFutureState(neighborState);
        }
      }
    }
  }
}
