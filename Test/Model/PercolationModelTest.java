package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class PercolationModelTest extends DukeApplicationTest {
  @Test
  public void testConstructorPercolation() {
    Model testModel = new PercolationModel("PercolationExample.csv", "Percolation");
    assertEquals(2, testModel.getCellState(0,0));
    assertEquals(1, testModel.getCellState(0,4));
  }

  @Test
  public void testStepPercolationExample() {
    Model testModel = new PercolationModel("PercolationExample.csv", "Percolation");
    assertEquals(0, testModel.getCellState(1,4));
    testModel.step();
    assertEquals(1, testModel.getCellState(1,4));
  }

  @Test
  public void testStepPercolation10() {
    Model testModel = new PercolationModel("Percolation10.csv", "Percolation");
    assertEquals(0, testModel.getCellState(1,3));
    testModel.step();
    assertEquals(1, testModel.getCellState(1,3));
  }

  @Test
  public void testStepPercolation10Example2() {
    Model testModel = new PercolationModel("Percolation10Example2.csv", "Percolation");
    assertEquals(0, testModel.getCellState(1,0));
    assertEquals(0, testModel.getCellState(1,2));
    testModel.step();
    assertEquals(1, testModel.getCellState(1,2));
    assertEquals(1, testModel.getCellState(1,0));
  }
}
