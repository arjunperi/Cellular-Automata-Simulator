package Model;

import java.util.List;
import java.util.Random;

/**
 * Model designated with handling the spreading fire simulation.
 *
 * @author Chris Shin
 */

public class SpreadingFireModel extends Model {

  public static final int ALIVE = 1;
  public static final int EMPTY = 0;
  public static final int BURNING = 2;
  private final double probCatchFire;
  public static final Random RANDOM_NUMBER_GEN = new Random();
  private static final String PROB_CATCH_FIRE = "Probability_Catch_Fire";

  public SpreadingFireModel(String fileName, String modelType) {
    super(fileName, modelType);
    try {
      probCatchFire = Double.parseDouble((String) propertyFile
          .getOrDefault(PROB_CATCH_FIRE, defaultPropertyFile.get(PROB_CATCH_FIRE)));
    } catch (NumberFormatException e) {
      throw new ModelException("Invalid Catching Fire Probability Input");
    }
  }

  protected void updateState(int row, int column, List<Cell> neighbors) {
    Cell currentCell = getCell(row, column);
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
