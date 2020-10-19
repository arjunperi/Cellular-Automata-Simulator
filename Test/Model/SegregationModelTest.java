package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class SegregationModelTest extends DukeApplicationTest {
  @Test
  public void testConstructorSegregation() {
    Model testModel = new SegregationModel("SegregationExample.csv", "Segregation");
    assertEquals(2, testModel.getCellState(0,0));
    assertEquals(1, testModel.getCellState(0,1));
  }

  @Test
  public void testStepSegregationExample() {
    Model testModel = new SegregationModel("SegregationExample.csv", "Segregation");
    assertEquals(2, testModel.getCellState(0,2));
    testModel.step();
    assertEquals(2, testModel.getCellState(0,2));
  }

  @Test
  public void testStepSegregation10Example2() {
    Model testModel = new SegregationModel("Segregation10Example2.csv", "Segregation");
    assertEquals(1, testModel.getCellState(0,2));
    testModel.step();
    assertEquals(2, testModel.getCellState(0,2));
  }

  @Test
  public void testStepSegregation20Example2() {
    Model testModel = new SegregationModel("Segregation20Example2.csv", "Segregation");
    assertEquals(1, testModel.getCellState(0,8));
    testModel.step();
    assertEquals(2, testModel.getCellState(0,8));
  }
}
