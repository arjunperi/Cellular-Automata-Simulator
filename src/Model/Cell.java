package Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

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