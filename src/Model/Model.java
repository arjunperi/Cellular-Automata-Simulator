package Model;

import java.util.List;


public abstract class Model {

  private final List<List<Cell>> myCells;
  private final Grid myGrid;

  public Model(String fileName) {
    myGrid = new Grid(fileName);
    myCells = myGrid.getGridCells();
  }

  public void step() {
    for (int i = 0; i < myGrid.getCellsPerColumn(); i++) {
      for (int j = 0; j < myGrid.getCellsPerRow(); j++) {
        updateCell(myCells.get(i).get(j), i, j);
      }
    }
    for (int i = 0; i < myGrid.getCellsPerColumn(); i++) {
      for (int j = 0; j < myGrid.getCellsPerRow(); j++) {
        this.myCells.get(i).get(j).nextState();
      }
    }
  }

  public abstract void updateCell(Cell cell, int x, int y);

  protected abstract List<List<Integer>> getNeighbors(int x, int y);

  public List<List<Cell>> getModelCells() {
    return myCells;
  }
}
