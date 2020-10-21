package Model;

import java.util.List;

/**
 * Cell class for the WaTor model
 *
 * @author Chris Shin
 */

public class WaTorCell extends Cell {

  private int stepsAlive = ONE;
  private int stepsAfterEating = ONE;
  private int stepsAfterEatingFuture = ONE;
  private int stepsAliveFuture = ONE;
  private int fishBreedingRate;
  private int sharkBreedingRate;
  private int sharkStarve;
  private static final int ZERO = 0;
  private static final int ONE = 1;


  public WaTorCell(int state) {
    super(state);
  }

  protected void moveIfCellIsFish(List<Cell> neighbors) {
    for (Cell neighbor : neighbors) {
      WaTorCell waTorNeighbor = (WaTorCell) neighbor;
      if (waTorNeighbor.getFutureState() == WaTorModel.WATER) {
        this.setFutureState(WaTorModel.WATER);
        setFutureStats(waTorNeighbor, this.getCurrentState(), this.stepsAlive + ONE);
        break;
      }
    }
    for (Cell neighbor : neighbors) {
      WaTorCell waTorNeighbor = (WaTorCell) neighbor;
      if (stepsAlive % fishBreedingRate == ZERO
          && waTorNeighbor.getFutureState() == WaTorModel.WATER) {
        setFutureStats(waTorNeighbor, this.getCurrentState(), ONE);
        break;
      }
    }
  }

  protected void moveIfCellIsShark(List<Cell> neighbors) {
    if (stepsAfterEating % sharkStarve == ZERO) {
      setFutureSharkStats(this, WaTorModel.WATER, ONE, ONE);
      return;
    }
    for (Cell neighbor : neighbors) {
      WaTorCell waTorNeighbor = (WaTorCell) neighbor;
      if (sharkMoveIntoWaterOrEatFish(waTorNeighbor, WaTorModel.FISH, ONE)) {
        return;
      }
    }
    for (Cell neighbor : neighbors) {
      WaTorCell waTorNeighbor = (WaTorCell) neighbor;
      if (sharkMoveIntoWaterOrEatFish(waTorNeighbor, WaTorModel.WATER,
          this.stepsAfterEating + ONE)) {
        return;
      }
    }
  }

  private boolean sharkMoveIntoWaterOrEatFish(WaTorCell cell, int prey, int stepsAfterEating) {
    if (cell.getFutureState() == prey) {
      this.setFutureState(WaTorModel.WATER);
      setFutureSharkStats(cell, WaTorModel.SHARK, this.stepsAlive + ONE, stepsAfterEating);
      sharkBreed();
      return true;
    }
    return false;
  }

  private void sharkBreed() {
    if (stepsAlive % sharkBreedingRate == ZERO) {
      setFutureSharkStats(this, WaTorModel.SHARK, ONE, ONE);
    }
  }

  private void setFutureSharkStats(WaTorCell cell, int state, int stepsAlive,
      int stepsAfterEating) {
    setFutureStats(cell, state, stepsAlive);
    cell.setStepsAfterEatingFuture(stepsAfterEating);
  }

  private void setFutureStats(WaTorCell cell, int state, int stepsAlive) {
    cell.setFutureState(state);
    cell.setStepsAliveFuture(stepsAlive);
  }

  public void setStepsAliveFuture(int stepsAlive) {
    this.stepsAliveFuture = stepsAlive;
  }

  public void setStepsAfterEatingFuture(int stepsAfterEating) {
    this.stepsAfterEatingFuture = stepsAfterEating;
  }

  protected void setParameters(int fishBreedingRate, int sharkBreedingRate, int sharkStarve) {
    this.fishBreedingRate = fishBreedingRate;
    this.sharkBreedingRate = sharkBreedingRate;
    this.sharkStarve = sharkStarve;
  }

  /**
   * modified to nextstate method, where now steps alive and steps after eating are now included.
   */

  @Override
  public void toNextState() {
    currentState = futureState;
    stepsAlive = stepsAliveFuture;
    stepsAfterEating = stepsAfterEatingFuture;
  }
}
