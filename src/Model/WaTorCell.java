package Model;

import java.util.Collections;
import java.util.List;

public class WaTorCell extends Cell{
  public static final int[][] POSSIBLE_NEIGHBORS_SEGREGATION = new int[][]{{-1, 0}, {0, 1},  {1, 0}, {0, -1}};
  private int stepsAlive=1;
  private int stepsAfterEating=1;
  private int stepsAfterEatingFuture=1;
  private int stepsAliveFuture=1;
  public static final int WATER = 0;
  public static final int FISH = 1;
  public static final int SHARK = 2;
  public static final int FISH_BREEDING = 5;
  public static final int SHARK_BREEDING = 15;
  public static final int SHARK_STARVE = 10;

  public WaTorCell(int state) {
    super(state);
  }

  @Override
  public void updateState(List<Cell> neighbors) {
    Collections.shuffle(neighbors);
    if(this.getCurrentState() == FISH && this.getFutureState() == FISH) {
      for(Cell neighbor:neighbors) {
        WaTorCell waTorNeighbor = (WaTorCell)neighbor;
        if(waTorNeighbor.getFutureState()==WATER) {
          this.setFutureState(WATER);
          setFutureStats(waTorNeighbor, this.getCurrentState(), this.stepsAlive+1);
          break;
        }
      }
      for(Cell neighbor:neighbors) {
        WaTorCell waTorNeighbor = (WaTorCell)neighbor;
        if(stepsAlive%FISH_BREEDING==0 && waTorNeighbor.getFutureState()==WATER) {
          setFutureStats(waTorNeighbor, this.getCurrentState(), 1);
          break;
        }
      }
    } else if(this.getCurrentState() == SHARK) {
      if(stepsAfterEating%SHARK_STARVE==0) {
        setFutureSharkStats(this, WATER, 1,1);
        return;
      }
      for(Cell neighbor:neighbors) {
        WaTorCell waTorNeighbor = (WaTorCell)neighbor;
        if(sharkMoveIntoWaterOrEatFish(waTorNeighbor, FISH, 1)) {
          return;
        }
      }
      for(Cell neighbor:neighbors) {
        WaTorCell waTorNeighbor = (WaTorCell)neighbor;
        if(sharkMoveIntoWaterOrEatFish(waTorNeighbor, WATER, this.stepsAfterEating+1)) {
          return;
        }
      }
    }
  }

  private boolean sharkMoveIntoWaterOrEatFish(WaTorCell cell, int prey, int stepsAfterEating) {
    if(cell.getFutureState()==prey) {
      this.setFutureState(WATER);
      setFutureSharkStats(cell, SHARK, this.stepsAlive+1,stepsAfterEating);
      sharkBreed();
      return true;
    }
    return false;
  }

  private void sharkBreed() {
    if(stepsAlive%SHARK_BREEDING==0) {
      setFutureSharkStats(this, SHARK, 1, 1);
    }
  }

  private void setFutureSharkStats(WaTorCell cell, int state, int stepsAlive, int stepsAfterEating) {
    setFutureStats(cell, state, stepsAlive);
    cell.setStepsAfterEatingFuture(stepsAfterEating);
  }

  private void setFutureStats(WaTorCell cell, int state, int stepsAlive) {
    cell.setFutureState(state);
    cell.setStepsAliveFuture(stepsAlive);
  }

  public void setStepsAliveFuture(int stepsAlive) {
    this.stepsAliveFuture=stepsAlive;
  }
  public void setStepsAfterEatingFuture(int stepsAfterEating) {
    this.stepsAfterEatingFuture=stepsAfterEating;
  }

  @Override
  public void toNextState() {
    currentState = futureState;
    stepsAlive=stepsAliveFuture;
    stepsAfterEating=stepsAfterEatingFuture;
  }

  @Override
  public int[][] getPossibleNeighbors() {
    return POSSIBLE_NEIGHBORS_SEGREGATION;
  }
}
