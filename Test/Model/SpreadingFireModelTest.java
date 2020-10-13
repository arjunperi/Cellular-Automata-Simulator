package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class SpreadingFireModelTest extends DukeApplicationTest {
  @Test
  public void testConstructorSpreadingFire() {
    Model testModel = new SpreadingFireModel("SpreadingFire20.csv", "SpreadingFire");
    assertEquals(2, testModel.getCellState(8,8));
    assertEquals(1, testModel.getCellState(7,7));
  }

  @Test
  public void testStepSpreadingFireExampleBurnedToEmpty() {
    Model testModel = new SpreadingFireModel("SpreadingFire20.csv", "SpreadingFire");
    assertEquals(2, testModel.getCellState(8,8));
    testModel.step();
    assertEquals(0, testModel.getCellState(8,8));
  }
}
