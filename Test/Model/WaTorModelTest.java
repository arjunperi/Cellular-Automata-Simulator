package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class WaTorModelTest extends DukeApplicationTest {
  @Test
  public void testConstructorWaTor() {
    Model testModel = new WaTorModel("WaTorExample.csv", "WaTor");
    assertEquals(0, testModel.getCellState(0,0));
    assertEquals(1, testModel.getCellState(0,1));
  }

  @Test
  public void testStepWaTorExample() {
    Model testModel = new WaTorModel("WaTorExample.csv", "WaTor");
    assertEquals(2, testModel.getCellState(5,1));
    testModel.step();
    assertEquals(0, testModel.getCellState(5,1));
  }
}
