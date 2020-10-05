package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConwayModel extends Model {

  public ConwayModel(String fileName) {
    super(fileName);
  }

  @Override
  public void updateCell(Cell cell, int x, int y) {
    List<List<Integer>> neighbors = getNeighbors(x, y);
    Cell currentCell = cell;
    int countAliveNeighbors = 0;
    for (List<Integer> neighbor : neighbors) {
      int neighborX = neighbor.get(0);
      int neighborY = neighbor.get(1);
      if (getModelCells().get(neighborX).get(neighborY).getCurrentState() == 1) {
        countAliveNeighbors++;
      }
    }
    if (currentCell.getCurrentState() == 1) {
      if (countAliveNeighbors < 2) {
        currentCell.setFutureState(0);
      } else if (countAliveNeighbors == 2 || countAliveNeighbors == 3) {
        currentCell.setFutureState(1);
      } else if (countAliveNeighbors > 3) {
        currentCell.setFutureState(0);
      }
    } else if (currentCell.getCurrentState() == 0) {
      if (countAliveNeighbors == 3) {
        cell.setFutureState(1);
      } else {
        cell.setFutureState(0);
      }
    }
  }

  @Override
  protected List<List<Integer>> getNeighbors(int x, int y) {
    int numberCellRows = super.getModelCells().size();
    int numberCellCols = super.getModelCells().get(0).size();
    int[][] possibleNeighbors = new int[][]{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1},
        {0, -1}, {-1, -1}};
    List<List<Integer>> neighbors = new ArrayList<>();
    for (int[] possibleNeighbor : possibleNeighbors) {
      int currentX = x + possibleNeighbor[0];
      int currentY = y + possibleNeighbor[1];
      if (currentX < 0 || currentY < 0) {
        continue;
      }
      if (currentX >= numberCellRows || currentY >= numberCellCols) {
        continue;
      }
      neighbors.add(Arrays.asList(currentX, currentY));
    }
    return neighbors;
  }

}


