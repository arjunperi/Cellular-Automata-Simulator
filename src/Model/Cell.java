package Model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Cell {

  private List<Integer> possibleStates;
  private int currentState;
  private int futureState;

  public Cell(int state) {
    this.currentState = state;
    this.futureState = currentState;
    this.initializePossibleStates();
  }

  public void initializePossibleStates(){
    this.setPossibleStates(new ArrayList<>(Arrays.asList(0,1)));
  }

  public void setPossibleStates(List<Integer> possibleStates) {
    this.possibleStates = possibleStates;
  }

  public void cycleNextState(){
    int currentStateIndex = this.possibleStates.indexOf(currentState);
    int nextIndex = (currentStateIndex + 1) % possibleStates.size();
    currentState = possibleStates.get(nextIndex);
    futureState = possibleStates.get(nextIndex);
  }

  public void setFutureState(int state) {
    futureState = state;
  }

  public int getCurrentState() {
    return currentState;
  }

  public abstract void updateState(List<Cell> neighbors);

  public abstract int[][] getPossibleNeighbors();

  public void toNextState() {
    currentState = futureState;
  }
}