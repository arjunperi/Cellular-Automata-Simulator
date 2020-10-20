package Model;


import Controller.Controller;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;

import javafx.scene.control.Alert;


public abstract class Model {

  private static final int ANIMATION_RATE_CHANGE = 5;
  private static final boolean PAUSED = true;
  public static final String DEFAULT = "Default";
  public static final String PROPERTIES = ".properties";
  public static final String INVALID = "Invalid File Name";
  public static final String STATES_ERROR = "Invalid States Input";
  public static final String INITIALIZATION_TYPE = "Initialization_Type";
  public static final Character SLASH = '/';
  public static final Character DOT = '.';
  private static final String STATES = "States";

  private double framesPerModelUpdate = 60;
  public static final int MIN_FRAMES_PER_MODEL_UPDATE = 5;
  public static final double TEN = 10;
  public static final int MAX_FRAMES_PER_MODEL_UPDATE = 100;
  protected final Grid gridOfCells;
  protected final Queue<Cell> emptyQueue = new LinkedList<>();
  protected final Properties defaultPropertyFile;
  protected final Properties propertyFile;
  private boolean isPaused = false;
  private boolean isStep = false;
  private double cycles = 0;
  protected List<Integer> allStates;

  public Model(String fileName, String modelType) {
    this.defaultPropertyFile = getPropertyFile(modelType + DEFAULT);
    int lastSlash =fileName.lastIndexOf(SLASH);
    int csvTrim = fileName.lastIndexOf(DOT);
    String trimmedFileName = fileName.substring(lastSlash+1, csvTrim);
    this.propertyFile = getPropertyFile(trimmedFileName);
    String defaultStates = defaultPropertyFile.getProperty(STATES);
    initializeAllStates((String) propertyFile.getOrDefault(STATES, defaultStates));
    String initializationTypeDefault = defaultPropertyFile.getProperty(INITIALIZATION_TYPE);
    String initializationType = (String)propertyFile.getOrDefault(INITIALIZATION_TYPE, initializationTypeDefault);
    gridOfCells = new Grid(fileName, modelType);
    gridOfCells.setPropertyFiles(propertyFile,defaultPropertyFile);
    gridOfCells.initializeWithType(initializationType, allStates);
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
    try {
      for (int row = 0; row < gridOfCells.getCellsPerColumn(); row++) {
        for (int column = 0; column < gridOfCells.getCellsPerRow(); column++) {
          List<Cell> cellNeighbors = gridOfCells.getNeighbors(row, column);
          updateState(row, column, cellNeighbors);
        }
      }
    } catch (ModelException e){
      switchPause();
      showError(e.getMessage());
    }
  }

  public void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Controller Error");
    alert.setContentText(message);
    alert.show();
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

  public void initializeAllStates(String allStates) throws ModelException {
    try{
      this.allStates = new ArrayList<>();
      String[] allStatesString = allStates.split(",");
      for (String stateString : allStatesString) {
        this.allStates.add(Integer.parseInt(stateString));
      }
    }
    catch (NumberFormatException e){
      throw new ModelException(STATES_ERROR);
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
    double temp = (1-speed)*MAX_FRAMES_PER_MODEL_UPDATE;
    this.framesPerModelUpdate = Math.max(MIN_FRAMES_PER_MODEL_UPDATE, Math.round(temp/TEN) * TEN);
  }

  public Properties getPropertyFile(String fileName) {
    Properties propertyFile = new Properties();
    try {
      propertyFile.load(Controller.class.getClassLoader()
          .getResourceAsStream(fileName + PROPERTIES));
    } catch (Exception e) {
      throw new ModelException(INVALID);
    }
    return propertyFile;
  }
}
