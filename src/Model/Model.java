package Model;


import cellsociety.Simulation;


public class Model {

  private final String fileOut;
  protected final Grid gridOfCells;
  private boolean isPaused = false;
  private boolean isStep = false;
  private double cycles = 0;

  public Model(String fileName, String modelType) {
    gridOfCells = new Grid(fileName,modelType);
    this.fileOut = null;
  }

  public Model(String fileName, String modelType, String fileOut) {
    gridOfCells = new Grid(fileName,modelType);
    this.fileOut = fileOut;
  }

  public void modelStep() {
    boolean isUpdate = checkTimeElapsed();
    if ((!isPaused && isUpdate) || isStep) {
      cycles = 0;
      gridOfCells.updateCells();
      gridOfCells.toNextState();
      writeToCSV();
    }
  }

  private void writeToCSV() {
    if (fileOut!=null) {
      gridOfCells.writeToCSV(fileOut);
    }
  }


  private boolean checkTimeElapsed() {
    cycles += 1;
    return cycles % Simulation.FRAMES_PER_MODEL_UPDATE == 0;
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

  public int getNumberOfRows() {
    return (int) gridOfCells.getCellsPerColumn();
  }

  public int getNumberOfColumns() {
    return (int) gridOfCells.getCellsPerRow();
  }
}
