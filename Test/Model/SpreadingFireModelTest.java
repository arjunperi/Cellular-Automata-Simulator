package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

  @Test
  public void testStepSpreadingFireExample10() {
    Model testModel = new SpreadingFireModel("SpreadingFire10.csv", "SpreadingFire");
    assertEquals(0, testModel.getCellState(0,0));
    testModel.step();
    assertEquals(0, testModel.getCellState(0,0));
  }

  @Test
  public void testStepSpreadingFireExample100() {
    Model testModel = new SpreadingFireModel("SpreadingFire100.csv", "SpreadingFire");
    assertEquals(2, testModel.getCellState(0,0));
    testModel.step();
    assertEquals(0, testModel.getCellState(0,0));
  }

  @Test
  public void testInvalidCatchFireProbability(){
    assertThrows(ModelException.class, () -> new SpreadingFireModel("Test/TestInvalidProbCatch.csv", "SpreadingFire"));
  }
}
