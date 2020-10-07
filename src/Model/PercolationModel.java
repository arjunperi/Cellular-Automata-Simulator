package Model;

import java.util.List;

public class PercolationModel extends Model{

  public static final int[][] POSSIBLE_NEIGHBORS_PERCOLATION = new int[][]{{-1, 0}, {0, 1}, {1, 0},
      {0, -1}};

  public PercolationModel(String fileName) {
    super(fileName);
  }

  public PercolationModel(String fileName, String fileOut) {
    super(fileName, fileOut);
  }

  protected List<List<Integer>> getNeighbors(int x, int y) {
    return processNeighbors(x,y,POSSIBLE_NEIGHBORS_PERCOLATION);
  }

  /**
   * 0 means empty, 1 means filled with water, 2 means wall
   *
   * @param row current row in grid
   * @param column current column in grid
   */

  @Override
  public void updateCell(int row, int column) {
    List<List<Integer>> neighbors = getNeighbors(row, column);
    Cell cell = gridOfCells.getCell(row,column);
    if(cell.getCurrentState()==0) {
      for(List<Integer> currentNeighbor : neighbors) {
        int neighborState = getCellState(currentNeighbor.get(0), currentNeighbor.get(1));
        if(neighborState==1) {
          cell.setFutureState(neighborState);
        }
      }
    }
  }
}
