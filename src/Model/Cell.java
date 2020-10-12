package Model;


import java.util.List;

public abstract class Cell {

  protected int currentState;
  protected int futureState;

  public Cell(int state) {
    this.currentState = state;
    this.futureState = currentState;
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

  public abstract void updateState(List<Cell> neighbors);

  public abstract int[][] getPossibleNeighbors();

  public void toNextState() {
    currentState = futureState;
  }
}