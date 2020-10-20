package Model;

import java.util.List;

public abstract class Cell {

  protected int currentState;
  protected int futureState;

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