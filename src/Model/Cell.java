package Model;


import java.util.List;

public abstract class Cell {

  private int currentState;
  private int futureState;

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

  public abstract void updateState(List<Cell> neighbors);

  public abstract int[][] getPossibleNeighbors();

  public void toNextState() {
    currentState = futureState;
  }
}