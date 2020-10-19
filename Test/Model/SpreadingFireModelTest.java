package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class SpreadingFireModelTest extends DukeApplicationTest {
  @Test
  public void testConstructorSpreadingFire() {
    Model testModel = new Model("SpreadingFire20.csv", "SpreadingFire");
    assertEquals(2, testModel.getCellState(8,8));
    assertEquals(1, testModel.getCellState(7,7));
  }

  @Test
  public void testStepSpreadingFireExampleBurnedToEmpty() {
    Model testModel = new Model("SpreadingFire20.csv", "SpreadingFire");
    assertEquals(2, testModel.getCellState(8,8));
    testModel.step();
    assertEquals(0, testModel.getCellState(8,8));
  }

  @Test
  public void testStepSpreadingFireExample10() {
    Model testModel = new Model("SpreadingFire10.csv", "SpreadingFire");
    assertEquals(0, testModel.getCellState(0,0));
    testModel.step();
    assertEquals(0, testModel.getCellState(0,0));
  }

  @Test
  public void testStepSpreadingFireExample100() {
    Model testModel = new Model("SpreadingFire100.csv", "SpreadingFire");
    assertEquals(2, testModel.getCellState(0,0));
    testModel.step();
    assertEquals(0, testModel.getCellState(0,0));
  }
}
