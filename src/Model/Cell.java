package Model;


public class Cell {

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

  public void nextState() {
    currentState = futureState;
  }
}