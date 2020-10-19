package Model;

import java.util.List;
import java.util.Random;

public class SpreadingFireModel extends Model{
  public static final int ALIVE = 1;
  public static final int EMPTY = 0;
  public static final int BURNING = 2;
  private final double probCatchFire;
  public static final Random RANDOM_NUMBER_GEN = new Random();

  public SpreadingFireModel(String fileName, String modelType) {
    super(fileName, modelType);
    probCatchFire=Double.parseDouble((String)propertyFile.getOrDefault("Probability_Catch_Fire", defaultPropertyFile.get("Probability_Catch_Fire")));
  }

  public void updateState(int row, int column, List<Cell> neighbors) {
    Cell currentCell = getCell(row,column);
    if (currentCell.getCurrentState() == BURNING) {
      currentCell.setFutureState(EMPTY);
    } else if (currentCell.getCurrentState() == ALIVE) {
      for (Cell currentNeighbor : neighbors) {
        int neighborState = currentNeighbor.getCurrentState();
        if (neighborState == BURNING) {
          double chanceToCatchFire = RANDOM_NUMBER_GEN.nextDouble();
          if (chanceToCatchFire <= probCatchFire) {
            currentCell.setFutureState(BURNING);
          }
        }
      }
    }
  }
}
