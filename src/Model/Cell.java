package Model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;

public abstract class Cell {

  protected int currentState;
  protected int futureState;
  private Cell cell;
  protected Properties propertyFile;

  public Cell(int state) {
    this.currentState = state;
    this.futureState = currentState;
  }

  public void cycleNextState(List<Integer> allStates) {
    int currentStateIndex = allStates.indexOf(currentState);
    int nextIndex = (currentStateIndex + 1) % allStates.size();
    currentState = allStates.get(nextIndex);
    futureState = allStates.get(nextIndex);
  }

  public void setFutureState(int state) {
    futureState = state;
  }

  public int getCurrentState() {
    return currentState;
  }

  public int getFutureState() {
    return futureState;
  }

  public void toNextState() {
    currentState = futureState;
  }
}