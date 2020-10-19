package Model;


import Controller.Controller;
import java.util.ArrayList;
import Controller.ControllerException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;


public abstract class Model {

  private static final int ANIMATION_RATE_CHANGE = 5;
  private static final boolean PAUSED = true;
  public static final String DEFAULT = "Default";
  public static final String PROPERTIES = ".properties";
  public static final String INVALID = "Invalid File Name";
  public static final Character SLASH = '/';
  public static final Character DOT = '.';

  private double framesPerModelUpdate = 60;
  public static final int MIN_FRAMES_PER_MODEL_UPDATE = 5;
  public static final int MAX_FRAMES_PER_MODEL_UPDATE = 100;
  protected final Grid gridOfCells;
  protected final Queue<Cell> emptyQueue = new LinkedList<>();
  protected final Properties defaultPropertyFile;
  protected final Properties propertyFile;
  private boolean isPaused = false;
  private boolean isStep = false;
  private double cycles = 0;
  private List<Integer> allStates;

  public Model(String fileName, String modelType) {
    gridOfCells = new Grid(fileName, modelType);
    this.defaultPropertyFile = getPropertyFile(modelType + DEFAULT);
    int lastSlash =fileName.lastIndexOf(SLASH);
    int csvTrim = fileName.lastIndexOf(DOT);
    String trimmedFileName = fileName.substring(lastSlash+1, csvTrim);
    this.propertyFile = getPropertyFile(trimmedFileName);
    gridOfCells.setPropertyFiles(propertyFile,defaultPropertyFile);
  }

  public boolean modelStep() {
    if ((!isPaused && checkTimeElapsed()) || isStep) {
      cycles = 0;
      updateCells();
      gridOfCells.toNextState();
      return true;
    }
    return false;
  }

  public void updateCells() {
    for (int row = 0; row < gridOfCells.getCellsPerColumn(); row++) {
      for (int column = 0; column < gridOfCells.getCellsPerRow(); column++) {
        List<Cell> cellNeighbors = gridOfCells.getNeighbors(row, column);
        updateState(row, column, cellNeighbors);
      }
    }
  }

  protected abstract void updateState(int row, int column, List<Cell> cellNeighbors);


  public void writeToCSV(String fileOut) {
    if (fileOut != null) {
      gridOfCells.writeToCSV(fileOut);
    }
  }

  private boolean checkTimeElapsed() {
    cycles += 1;
    return cycles % this.framesPerModelUpdate == 0;
  }

  public void switchPause() {
    isPaused = !isPaused;
  }

  public void setPaused() {
    isPaused = PAUSED;
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

  public void initializeAllStates(String allStates) {
    this.allStates = new ArrayList<>();
    String[] allStatesString = allStates.split(",");
    for (String stateString : allStatesString) {
      this.allStates.add(Integer.parseInt(stateString));
    }
  }

  public void cycleState(int row, int column) {
    getCell(row, column).cycleNextState(allStates);
  }

  public int getNumberOfRows() {
    return (int) gridOfCells.getCellsPerColumn();
  }

  public int getNumberOfColumns() {
    return (int) gridOfCells.getCellsPerRow();
  }

  public void speedUp() {
    this.framesPerModelUpdate = Math.max(MIN_FRAMES_PER_MODEL_UPDATE, framesPerModelUpdate - ANIMATION_RATE_CHANGE);
  }

  public void slowDown() {
    this.framesPerModelUpdate = Math.min(MAX_FRAMES_PER_MODEL_UPDATE, framesPerModelUpdate + ANIMATION_RATE_CHANGE);
  }

  public void setSimulationSpeed(double speed){
    Double temp = (1-speed)*MAX_FRAMES_PER_MODEL_UPDATE;
    this.framesPerModelUpdate = Math.max(MIN_FRAMES_PER_MODEL_UPDATE, Math.round(temp/10.0) * 10);
  }

  public Properties getPropertyFile(String fileName) {
    Properties propertyFile = new Properties();
    try {
      propertyFile.load(Controller.class.getClassLoader()
          .getResourceAsStream(fileName + PROPERTIES));
    } catch (Exception e) {
      throw new ControllerException(INVALID, e);
    }
    return propertyFile;
  }
}
