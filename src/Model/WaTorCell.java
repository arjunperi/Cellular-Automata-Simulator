package Model;

import java.util.List;

public class WaTorCell extends Cell {

  private int stepsAlive = 1;
  private int stepsAfterEating = 1;
  private int stepsAfterEatingFuture = 1;
  private int stepsAliveFuture = 1;
  private int fishBreedingRate;
  private int sharkBreedingRate;
  private int sharkStarve;


  public WaTorCell(int state) {
    super(state);
  }

  protected void moveIfCellIsFish(List<Cell> neighbors) {
    for (Cell neighbor : neighbors) {
      WaTorCell waTorNeighbor = (WaTorCell) neighbor;
      if (waTorNeighbor.getFutureState() == WaTorModel.WATER) {
        this.setFutureState(WaTorModel.WATER);
        setFutureStats(waTorNeighbor, this.getCurrentState(), this.stepsAlive + 1);
        break;
      }
    }
    for (Cell neighbor : neighbors) {
      WaTorCell waTorNeighbor = (WaTorCell) neighbor;
      if (stepsAlive % fishBreedingRate == 0 && waTorNeighbor.getFutureState() == WaTorModel.WATER) {
        setFutureStats(waTorNeighbor, this.getCurrentState(), 1);
        break;
      }
    }
  }

  protected void moveIfCellIsShark(List<Cell> neighbors) {
    if (stepsAfterEating % sharkStarve == 0) {
      setFutureSharkStats(this, WaTorModel.WATER, 1, 1);
      return;
    }
    for (Cell neighbor : neighbors) {
      WaTorCell waTorNeighbor = (WaTorCell) neighbor;
      if (sharkMoveIntoWaterOrEatFish(waTorNeighbor, WaTorModel.FISH, 1)) {
        return;
      }
    }
    for (Cell neighbor : neighbors) {
      WaTorCell waTorNeighbor = (WaTorCell) neighbor;
      if (sharkMoveIntoWaterOrEatFish(waTorNeighbor, WaTorModel.WATER, this.stepsAfterEating + 1)) {
        return;
      }
    }
  }

  private boolean sharkMoveIntoWaterOrEatFish(WaTorCell cell, int prey, int stepsAfterEating) {
    if (cell.getFutureState() == prey) {
      this.setFutureState(WaTorModel.WATER);
      setFutureSharkStats(cell, WaTorModel.SHARK, this.stepsAlive + 1, stepsAfterEating);
      sharkBreed();
      return true;
    }
    return false;
  }

  private void sharkBreed() {
    if (stepsAlive % sharkBreedingRate == 0) {
      setFutureSharkStats(this, WaTorModel.SHARK, 1, 1);
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
    this.fishBreedingRate=fishBreedingRate;
    this.sharkBreedingRate=sharkBreedingRate;
    this.sharkStarve=sharkStarve;
  }

  @Override
  public void toNextState() {
    currentState = futureState;
    stepsAlive = stepsAliveFuture;
    stepsAfterEating = stepsAfterEatingFuture;
  }
}
