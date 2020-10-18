package Model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Cell {

  protected int currentState;
  protected int futureState;

  public Cell(int state, Queue<Cell> emptyQueue) {
    this.currentState = state;
    this.futureState = currentState;
    if(state==0) {
      emptyQueue.add(this);
    }
  }

  public void cycleNextState(List<Integer> allStates){
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

  public abstract void updateState(List<Cell> neighbors);

  public void toNextState() {
    currentState = futureState;
  }
}