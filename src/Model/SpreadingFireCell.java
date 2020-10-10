package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SpreadingFireCell extends Cell{
  public static final int[][] POSSIBLE_NEIGHBORS_SPREADING_FIRE = new int[][]{{-1, 0}, {0, 1}, {1, 0},
      {0, -1}};
  public static final int ALIVE = 1;
  public static final int EMPTY = 0;
  public static final int BURNING = 2;
  public static final double PROB_CATCH_FIRE = .5;
  public static final Random RANDOM_NUMBER_GEN = new Random();

  public SpreadingFireCell(int state) {
    super(state);
  }

  /**
   * 0 means empty, 1 means filled with water, 2 means wall
   */
  @Override
  public void updateState(List<Cell> neighbors) {
    if(this.getCurrentState()==BURNING) {
      this.setFutureState(EMPTY);
    } else if(this.getCurrentState()==ALIVE) {
      for(Cell currentNeighbor : neighbors) {
        int neighborState = currentNeighbor.getCurrentState();
        if(neighborState==BURNING) {
          double chanceToCatchFire=RANDOM_NUMBER_GEN.nextDouble();
          if(chanceToCatchFire<=PROB_CATCH_FIRE) {
            this.setFutureState(BURNING);
          }
        }
      }
    }
  }

  @Override
  public int[][] getPossibleNeighbors() {
    return POSSIBLE_NEIGHBORS_SPREADING_FIRE;
  }
}
