package Model;


import Controller.ControllerException;
import cellsociety.Simulation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.sun.jdi.NativeMethodException;
import javafx.scene.control.Alert;
import org.assertj.core.internal.bytebuddy.matcher.StringMatcher;


public class Model {

  private static final int ANIMATION_RATE_CHANGE = 5;
  private static final boolean PAUSED = true;

  public double framesPerModelUpdate = 60;
  protected final Grid gridOfCells;
  protected final Queue<Cell> emptyQueue = new LinkedList<>();
  private boolean isPaused = false;
  private boolean isStep = false;
  private double cycles = 0;
  private List<Integer> allStates;

  public Model(String fileName, String modelType) {
      gridOfCells = new Grid(fileName, modelType, emptyQueue);
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
    try{gridOfCells.updateCells();}
    catch (ModelException e){
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
      throw new ModelException("Invalid States Input");
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

  public void speedUp() {
    this.framesPerModelUpdate = Math.max(10, framesPerModelUpdate - ANIMATION_RATE_CHANGE);
  }

  public void slowDown() {
    this.framesPerModelUpdate = Math.min(100, framesPerModelUpdate + ANIMATION_RATE_CHANGE);
  }
}
