package Model;


import cellsociety.Simulation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class Model {

  private final String fileOut;
  private final Grid gridOfCells;
  private boolean isPaused = false;
  private boolean isStep = false;
  private double cycles = 0;

  public Model(String fileName) {
    gridOfCells = new Grid(fileName);
    this.fileOut="";
  }

  public Model(String fileName, String fileOut) {
    gridOfCells = new Grid(fileName);
    this.fileOut=fileOut;
  }

  public abstract void updateCells(Cell cell, int x, int y);

  public void modelStep() {
    boolean isUpdate = checkTimeElapsed();
    if ((!isPaused && isUpdate) || isStep) {
      cycles = 0;
      for (int i = 0; i < gridOfCells.getCellsPerColumn(); i++) {
        for (int j = 0; j < gridOfCells.getCellsPerRow(); j++) {
          gridOfCells.updateSpecificCell(i,j);
          updateCells(gridOfCells.getCell(i,j), i, j);
        }
      }
      for (int i = 0; i < gridOfCells.getCellsPerColumn(); i++) {
        for (int j = 0; j < gridOfCells.getCellsPerRow(); j++) {
          gridOfCells.getCell(i,j).nextState();
        }
      }
      if(fileOut.length()>0) {
        gridOfCells.writeToCSV(fileOut);
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
      if (currentX >= gridOfCells.getCellsPerColumn() || currentY >= gridOfCells.getCellsPerRow()) {
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

  public int getCellState(int row, int column) {
    return gridOfCells.getCell(row, column).getCurrentState();
  }

  public Cell getCell(int row, int column) {
    return gridOfCells.getCell(row, column);
  }

  public int getNumberOfRows() {
    return (int) gridOfCells.getCellsPerColumn();
  }

  public int getNumberOfColumns() {
    return (int) gridOfCells.getCellsPerRow();
  }


}
