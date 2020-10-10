package Model;

import java.util.List;

public class GameOfLifeCell extends Cell{

  public static final int[][] POSSIBLE_NEIGHBORS_GAME_OF_LIFE = new int[][]{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1},
      {0, -1}, {-1, -1}};

  public GameOfLifeCell(int state) {
    super(state);
  }

  @Override
  public void updateState(List<Cell> neighbors) {
    int countAliveNeighbors = 0;
    for (Cell neighbor : neighbors) {
      if(neighbor.getCurrentState()==1) {
        countAliveNeighbors++;
      }
    }
    if(this.getCurrentState()==1) {
      if (countAliveNeighbors < 2) {
        this.setFutureState(0);
      } else if (countAliveNeighbors == 2 || countAliveNeighbors == 3) {
        this.setFutureState(1);
      } else {
        this.setFutureState(0);
      }
    } else if (this.getCurrentState() == 0) {
      if (countAliveNeighbors == 3) {
        this.setFutureState(1);
      } else {
        this.setFutureState(0);
      }
    }
  }

  @Override
  public int[][] getPossibleNeighbors() {
    return POSSIBLE_NEIGHBORS_GAME_OF_LIFE;
  }
}
