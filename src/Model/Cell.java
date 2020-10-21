package Model;

import java.util.List;

/**
 * General ell class to handle changing states between steps.
 *
 * @author Chris Shin
 */

public abstract class Cell {

  protected int currentState;
  protected int futureState;

  public Cell(int state) {
    this.currentState = state;
    this.futureState = currentState;
  }

  /**
   * Cycles state of this cell to the next available state when clicked on in UI.
   */

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