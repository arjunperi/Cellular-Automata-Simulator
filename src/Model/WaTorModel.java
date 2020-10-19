package Model;

import java.util.Collections;
import java.util.List;

public class WaTorModel extends Model{
  public static final int WATER = 0;
  public static final int FISH = 1;
  public static final int SHARK = 2;
  private final int fishBreedingRate;
  private final int sharkBreedingRate;
  private final int sharkStarve;

  public WaTorModel(String fileName, String modelType) {
    super(fileName, modelType);
    fishBreedingRate=Integer.parseInt((String)propertyFile.getOrDefault("Fish_Breeding_Rate", defaultPropertyFile.get("Fish_Breeding_Rate")));
    sharkBreedingRate=Integer.parseInt((String)propertyFile.getOrDefault("Shark_Breeding_Rate", defaultPropertyFile.get("Shark_Breeding_Rate")));
    sharkStarve=Integer.parseInt((String)propertyFile.getOrDefault("Shark_Starve_Rate", defaultPropertyFile.get("Shark_Starve_Rate")));
  }

  public void updateState(int row, int column, List<Cell> neighbors) {
    WaTorCell currentCell = (WaTorCell)getCell(row,column);
    currentCell.setParameters(fishBreedingRate,sharkBreedingRate,sharkStarve);
    Collections.shuffle(neighbors);
    if (currentCell.getCurrentState() == FISH && currentCell.getFutureState() == FISH) {
      currentCell.moveIfCellIsFish(neighbors);
    } else if (currentCell.getCurrentState() == SHARK) {
      currentCell.moveIfCellIsShark(neighbors);
    }
  }


}
