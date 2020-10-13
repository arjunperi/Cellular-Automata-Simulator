package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class RPSModelTest extends DukeApplicationTest {
  @Test
  public void testConstructorRPS() {
    Model testModel = new RPSModel("RPSExample.csv", "RPS");
    assertEquals(3, testModel.getCellState(0,0));
    assertEquals(1, testModel.getCellState(0,1));
  }

  @Test
  public void testStepRPSExample() {
    Model testModel = new RPSModel("RPSExample.csv", "RPS");
    assertEquals(1, testModel.getCellState(1,3));
    testModel.step();
    assertEquals(1, testModel.getCellState(1,3));
  }

  @Test
  public void testStepRPS50() {
    Model testModel = new RPSModel("RPS50.csv", "RPS");
    assertEquals(1, testModel.getCellState(0,3));
    testModel.step();
    assertEquals(1, testModel.getCellState(0,3));
  }
//
//  @Test
//  public void testStepRPS100() {
//    Model testModel = new RPSModel("RPS100.csv", "RPS");
//    assertEquals(3, testModel.getCellState(1,2));
//    testModel.step();
//    assertEquals(3, testModel.getCellState(1,2));
//  }
}
