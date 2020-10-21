package Model;

import java.util.List;

/**
 * Model designated with handling the game of life simulation.  Gets the number of neighbors alive
 * in the neighborhood and updates state accordingly.
 *
 * @author Chris Shin
 */

public class GameOfLifeModel extends Model {

  private static final int ALIVE = 1;
  private static final int DEAD = 0;
  private static final int TWO = 2;
  private static final int THREE = 3;

  public GameOfLifeModel(String fileName, String modelType) {
    super(fileName, modelType);
  }

  protected void updateState(int row, int column, List<Cell> neighbors) {
    int countAliveNeighbors = getNumberOfLiveNeighbors(neighbors);
    Cell currentCell = getCell(row, column);
    if (currentCell.getCurrentState() == ALIVE && (countAliveNeighbors == TWO
        || countAliveNeighbors == THREE)
        || (
        currentCell.getCurrentState() == DEAD && countAliveNeighbors == THREE)) {
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
