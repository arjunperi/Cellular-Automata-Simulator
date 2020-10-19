package Model;

import java.util.List;

public class PercolationModel extends Model{
  private static final int FULL = 1;
  private static final int EMPTY = 0;

  public PercolationModel(String fileName, String modelType) {
    super(fileName, modelType);
  }

  protected void updateState(int row, int column, List<Cell> neighbors) {
    Cell currentCell = getCell(row,column);
    if (currentCell.getCurrentState() == EMPTY) {
      for (Cell currentNeighbor : neighbors) {
        int neighborState = currentNeighbor.getCurrentState();
        if (neighborState == FULL) {
          currentCell.setFutureState(neighborState);
        }
      }
    }
  }
}
