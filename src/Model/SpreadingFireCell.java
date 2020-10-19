package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class SpreadingFireCell extends Cell {

  public static final int ALIVE = 1;
  public static final int EMPTY = 0;
  public static final int BURNING = 2;
  public static final double PROB_CATCH_FIRE = .5;
  public static final Random RANDOM_NUMBER_GEN = new Random();

  public SpreadingFireCell(int state, Queue<Cell> emptyQueue) {
    super(state, emptyQueue);
  }

  /**
   * 0 means empty, 1 means filled with water, 2 means wall
   */
  @Override
  public void updateState(List<Cell> neighbors) {
    if (this.getCurrentState() == BURNING) {
      this.setFutureState(EMPTY);
    } else if (this.getCurrentState() == ALIVE) {
      for (Cell currentNeighbor : neighbors) {
        int neighborState = currentNeighbor.getCurrentState();
        if (neighborState == BURNING) {
          double chanceToCatchFire = RANDOM_NUMBER_GEN.nextDouble();
          if (chanceToCatchFire <= PROB_CATCH_FIRE) {
            this.setFutureState(BURNING);
          }
        }
      }
    }
  }
}
