package Model;

import java.util.Collections;
import java.util.List;

public class WaTorModel extends Model{
  public static final int WATER = 0;
  public static final int FISH = 1;
  public static final int SHARK = 2;
  public static final int FISH_BREEDING = 5;
  public static final int SHARK_BREEDING = 15;
  public static final int SHARK_STARVE = 10;

  public WaTorModel(String fileName, String modelType) {
    super(fileName, modelType);
  }

  public void updateState(int row, int column, List<Cell> neighbors) {
    WaTorCell currentCell = (WaTorCell)getCell(row,column);
    Collections.shuffle(neighbors);
    if (currentCell.getCurrentState() == FISH && currentCell.getFutureState() == FISH) {
      currentCell.moveIfCellIsFish(neighbors);
    } else if (currentCell.getCurrentState() == SHARK) {
      currentCell.moveIfCellIsShark(neighbors);
    }
  }
}
