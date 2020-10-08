package Model;

import java.util.List;

public class GameOfLifeModel extends Model {

  public static final int[][] POSSIBLE_NEIGHBORS_GAME_OF_LIFE = new int[][]{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1},
      {0, -1}, {-1, -1}};

  public GameOfLifeModel(String fileName) {
    super(fileName);
  }

  public GameOfLifeModel(String fileName, String fileOut) {
    super(fileName, fileOut);
  }

  protected List<List<Integer>> getNeighbors(int x, int y) {
    return processNeighbors(x,y,POSSIBLE_NEIGHBORS_GAME_OF_LIFE);
  }

  @Override
  public void updateCell(int row, int column) {
    List<List<Integer>> neighbors = getNeighbors(row, column);
    Cell cell = gridOfCells.getCell(row,column);
    int countAliveNeighbors = 0;
    for (List<Integer> neighbor : neighbors) {
      int neighborX = neighbor.get(0);
      int neighborY = neighbor.get(1);
      if (getCellState(neighborX,neighborY) == 1) {
        countAliveNeighbors++;
      }
    }
    if (getCellState(row,column) == 1) {
      if (countAliveNeighbors < 2) {
        cell.setFutureState(0);
      } else if (countAliveNeighbors == 2 || countAliveNeighbors == 3) {
        cell.setFutureState(1);
      } else {
        cell.setFutureState(0);
      }
    } else if (cell.getCurrentState() == 0) {
      if (countAliveNeighbors == 3) {
        cell.setFutureState(1);
      } else {
        cell.setFutureState(0);
      }
    }
  }
}


