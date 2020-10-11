package Model;

import java.util.List;

public class GameOfLifeCell extends Cell {

  public static final int[][] POSSIBLE_NEIGHBORS_GAME_OF_LIFE = new int[][]{{-1, 0}, {-1, 1},
      {0, 1}, {1, 1}, {1, 0}, {1, -1},
      {0, -1}, {-1, -1}};

  public GameOfLifeCell(int state) {
    super(state);
  }

  @Override
  public void updateState(List<Cell> neighbors) {
    int countAliveNeighbors = getNumberOfLiveNeighbors(neighbors);
    if (this.getCurrentState() == 1 && (countAliveNeighbors == 2 || countAliveNeighbors == 3) || (
        this.getCurrentState() == 0 && countAliveNeighbors == 3)) {
      this.setFutureState(1);
    } else {
      this.setFutureState(0);
    }
  }

  private int getNumberOfLiveNeighbors(List<Cell> neighbors) {
    int countAliveNeighbors = 0;
    for (Cell neighbor : neighbors) {
      if (neighbor.getCurrentState() == 1) {
        countAliveNeighbors++;
      }
    }
    return countAliveNeighbors;
  }

  @Override
  public int[][] getPossibleNeighbors() {
    return POSSIBLE_NEIGHBORS_GAME_OF_LIFE;
  }
}
