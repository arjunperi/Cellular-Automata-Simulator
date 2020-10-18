package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class GameOfLifeCell extends Cell {

  public static final int[][] POSSIBLE_NEIGHBORS_GAME_OF_LIFE = new int[][]{{-1, 0}, {-1, 1},
      {0, 1}, {1, 1}, {1, 0}, {1, -1},
      {0, -1}, {-1, -1}};

  private static final int ALIVE=1;
  private static final int DEAD=0;

  public GameOfLifeCell(int state, Queue<Cell> emptyQueue) {
    super(state, emptyQueue);
  }

  @Override
  public void updateState(List<Cell> neighbors) {
    int countAliveNeighbors = getNumberOfLiveNeighbors(neighbors);
    if (this.getCurrentState() == ALIVE && (countAliveNeighbors == 2 || countAliveNeighbors == 3) || (
        this.getCurrentState() == DEAD && countAliveNeighbors == 3)) {
      this.setFutureState(ALIVE);
    } else {
      this.setFutureState(DEAD);
    }
  }

  private int getNumberOfLiveNeighbors(List<Cell> neighbors) {
    int countAliveNeighbors = 0;
    for (Cell neighbor : neighbors) {
      if (neighbor.getCurrentState() == ALIVE) {
        countAliveNeighbors++;
      }
    }
    return countAliveNeighbors;
  }
}
