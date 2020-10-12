package Model;

import java.util.List;

public class WaTorModel extends Model{

  private final Grid mainGrid;

  public WaTorModel(String fileName, String modelType, String fileOut) {
    super(fileName,modelType, fileOut);
    mainGrid=this.getGridOfCells();
  }

  @Override
  public void updateCells() {
    for (int row = 0; row < mainGrid.getCellsPerColumn(); row++) {
      for (int column = 0; column < mainGrid.getCellsPerRow(); column++) {
        Cell currentCell = mainGrid.getCell(row,column);
        List<Cell> cellNeighbors = mainGrid.getNeighbors(row, column, currentCell);
        currentCell.updateState(cellNeighbors);
      }
    }
  }
}
