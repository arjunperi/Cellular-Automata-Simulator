package Model;

import java.util.List;
import java.util.Random;

public class SpreadingFireModel extends Model{

  public static final int[][] POSSIBLE_NEIGHBORS_PERCOLATION = new int[][]{{-1, 0}, {0, 1}, {1, 0},
      {0, -1}};
  public static final int ALIVE = 1;
  public static final int EMPTY = 0;
  public static final int BURNING = 2;
  public static final double PROB_CATCH_FIRE = .5;
  public static final Random RANDOM_NUMBER_GEN = new Random();

  public SpreadingFireModel(String fileName) {
    super(fileName);
  }

  public SpreadingFireModel(String fileName, String fileOut) {
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
    if(cell.getCurrentState()==BURNING) {
      cell.setFutureState(EMPTY);
    } else if(cell.getCurrentState()==ALIVE) {
      for(List<Integer> currentNeighbor : neighbors) {
        int neighborState = getCellState(currentNeighbor.get(0), currentNeighbor.get(1));
        if(neighborState==BURNING) {
          double chanceToCatchFire=RANDOM_NUMBER_GEN.nextDouble();
          if(chanceToCatchFire<=PROB_CATCH_FIRE) {
            cell.setFutureState(BURNING);
          }
        }
      }
    }
  }
}
