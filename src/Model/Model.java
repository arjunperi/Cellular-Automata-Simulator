package Model;


import cellsociety.Simulation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.assertj.core.internal.bytebuddy.matcher.StringMatcher;


public class Model {

  private static final int ANIMATION_RATE_CHANGE = 5;

  public double framesPerModelUpdate = 60;
  private final String fileOut;
  protected final Grid gridOfCells;
  protected final Queue<Cell> emptyQueue = new LinkedList<>();
  private boolean isPaused = false;
  private boolean isStep = false;
  private double cycles = 0;
  private List<Integer> allStates;

  public Model(String fileName, String modelType) {
    gridOfCells = new Grid(fileName, modelType, emptyQueue);
    this.fileOut = null;
  }

  public Model(String fileName, String modelType, String fileOut){
    gridOfCells = new Grid(fileName, modelType, emptyQueue);
    this.fileOut = fileOut;
  }

  public void modelStep() {
    if ((!isPaused && checkTimeElapsed()) || isStep) {
      cycles = 0;
      updateCells();
      gridOfCells.toNextState();
      writeToCSV();
    }
  }

  public void updateCells() {
    gridOfCells.updateCells();
  }

  public void writeToCSV() {
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
    for(String stateString:allStatesString) {
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

  public Queue<Cell> getQueue() {
    return emptyQueue;
  }

  public void speedUp(){
    this.framesPerModelUpdate = Math.max(10,framesPerModelUpdate - ANIMATION_RATE_CHANGE);
  }
  public void slowDown(){
    this.framesPerModelUpdate = Math.min(100,framesPerModelUpdate + ANIMATION_RATE_CHANGE);
  }
}
