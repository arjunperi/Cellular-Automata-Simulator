package Model;

import java.util.List;

public class GameOfLifeModel extends Model {

  public GameOfLifeModel(String fileName) {
    super(fileName);
  }

  public GameOfLifeModel(String fileName, String fileOut) {
    super(fileName, fileOut);
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


