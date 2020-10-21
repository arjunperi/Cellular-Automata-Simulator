package Model;

import java.util.Collections;
import java.util.List;

/**
 * Model designated with handling the WaTor simulation.
 *
 * @author Chris Shin
 */

public class WaTorModel extends Model {

  public static final int WATER = 0;
  public static final int FISH = 1;
  public static final int SHARK = 2;
  private final int fishBreedingRate;
  private final int sharkBreedingRate;
  private final int sharkStarve;
  private static final String FISH_BREEDING_RATE = "Fish_Breeding_Rate";
  private static final String SHARK_BREEDING_RATE = "Shark_Breeding_Rate";
  private static final String SHARK_STARVE_RATE = "Shark_Starve_Rate";

  public WaTorModel(String fileName, String modelType) {
    super(fileName, modelType);
    try {
      fishBreedingRate = Integer.parseInt((String) propertyFile
          .getOrDefault(FISH_BREEDING_RATE, defaultPropertyFile.get(FISH_BREEDING_RATE)));
      sharkBreedingRate = Integer.parseInt((String) propertyFile
          .getOrDefault(SHARK_BREEDING_RATE, defaultPropertyFile.get(SHARK_BREEDING_RATE)));
      sharkStarve = Integer.parseInt((String) propertyFile
          .getOrDefault(SHARK_STARVE_RATE, defaultPropertyFile.get(SHARK_STARVE_RATE)));
    } catch (NumberFormatException e) {
      throw new ModelException("Invalid Breeding/Starve Rate Input");
    }
  }

  protected void updateState(int row, int column, List<Cell> neighbors) {
    WaTorCell currentCell = (WaTorCell) getCell(row, column);
    currentCell.setParameters(fishBreedingRate, sharkBreedingRate, sharkStarve);
    Collections.shuffle(neighbors);
    if (currentCell.getCurrentState() == FISH && currentCell.getFutureState() == FISH) {
      currentCell.moveIfCellIsFish(neighbors);
    } else if (currentCell.getCurrentState() == SHARK) {
      currentCell.moveIfCellIsShark(neighbors);
    }
  }
}
