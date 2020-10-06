package Model;

import java.util.List;

public class GameOfLifeModel extends Model {

  public GameOfLifeModel(String fileName) {
    super(fileName);
  }

  @Override
  public void updateCells(Cell cell, int x, int y) {
    List<List<Integer>> neighbors = getNeighbors(x, y);
    int countAliveNeighbors = 0;
    for (List<Integer> neighbor : neighbors) {
      int neighborX = neighbor.get(0);
      int neighborY = neighbor.get(1);
      if (getModelCells().get(neighborX).get(neighborY).getCurrentState() == 1) {
        countAliveNeighbors++;
      }
    }
    if (cell.getCurrentState() == 1) {
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


