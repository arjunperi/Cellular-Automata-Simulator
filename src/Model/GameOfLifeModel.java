package Model;

import java.util.List;

public class GameOfLifeModel extends Model{
  private static final int ALIVE = 1;
  private static final int DEAD = 0;

  public GameOfLifeModel(String fileName, String modelType) {
    super(fileName,modelType);
  }
  
  protected void updateState(int row, int column, List<Cell> neighbors) {
    int countAliveNeighbors = getNumberOfLiveNeighbors(neighbors);
    Cell currentCell = getCell(row,column);
    if (currentCell.getCurrentState() == ALIVE && (countAliveNeighbors == 2 || countAliveNeighbors == 3)
        || (
        currentCell.getCurrentState() == DEAD && countAliveNeighbors == 3)) {
      currentCell.setFutureState(ALIVE);
    } else {
      currentCell.setFutureState(DEAD);
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
