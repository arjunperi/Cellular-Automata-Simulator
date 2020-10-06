package Model;


import cellsociety.Simulation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class Model {

  private final List<List<Cell>> myCells;
  private final String fileOut;
  private final Grid myGrid;
  private boolean isPaused = false;
  private boolean isStep = false;
  private double cycles = 0;

  public Model(String fileName) {
    myGrid = new Grid(fileName);
    myCells = myGrid.getGridCells();
    this.fileOut="";
  }

  public Model(String fileName, String fileOut) {
    myGrid = new Grid(fileName);
    myCells = myGrid.getGridCells();
    this.fileOut=fileOut;
  }

  public abstract void updateCells(Cell cell, int x, int y);

  public void modelStep() {
    boolean isUpdate = checkTimeElapsed();
    if ((!isPaused && isUpdate) || isStep) {
      cycles = 0;
      for (int i = 0; i < myGrid.getCellsPerColumn(); i++) {
        for (int j = 0; j < myGrid.getCellsPerRow(); j++) {
          updateCells(myCells.get(i).get(j), i, j);
        }
      }
      for (int i = 0; i < myGrid.getCellsPerColumn(); i++) {
        for (int j = 0; j < myGrid.getCellsPerRow(); j++) {
          this.myCells.get(i).get(j).nextState();
        }
      }
      if(fileOut.length()>0) {
        myGrid.writeToCSV(fileOut);
      }
    }
  }

  private boolean checkTimeElapsed() {
    cycles += 1;
    return cycles % Simulation.FRAMES_PER_MODEL_UPDATE == 0;
  }

  public List<List<Integer>> getNeighbors(int x, int y) {
    int[][] possibleNeighbors = new int[][]{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1},
        {0, -1}, {-1, -1}};
    List<List<Integer>> neighbors = new ArrayList<>();
    for (int[] possibleNeighbor : possibleNeighbors) {
      int currentX = x + possibleNeighbor[0];
      int currentY = y + possibleNeighbor[1];
      if (currentX < 0 || currentY < 0) {
        continue;
      }
      if (currentX >= myGrid.getCellsPerColumn() || currentY >= myGrid.getCellsPerRow()) {
        continue;
      }
      neighbors.add(Arrays.asList(currentX, currentY));
    }
    return neighbors;
  }

  public void switchPause() {
    isPaused = !isPaused;
  }

  public void step() {
    isPaused = true;
    isStep = true;
    modelStep();
    isStep = false;
  }

  public List<List<Cell>> getModelCells() {
    return myCells;
  }
}
