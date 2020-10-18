package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class GameOfLifeModelTest {

  @Test
  public void testConstructor() {
    Model testModel = new Model("Test/ConwayStatesPulsar.csv", "GameOfLife");
    assertEquals(0, testModel.getCellState(0,0));
    assertEquals(1, testModel.getCellState(4,5));
  }

  @Test
  public void testStepPulsar() {
    Model testModel = new Model("Test/ConwayStatesPulsar.csv", "GameOfLife");
    assertEquals(1, testModel.getCellState(4,5));
    testModel.step();
    assertEquals(0, testModel.getCellState(4,5));
  }

  @Test
  public void testStepBeacon() {
    Model testModel = new Model("Test/ConwayStatesBeacon.csv", "GameOfLife");
    assertEquals(0, testModel.getCellState(7,7));
    testModel.step();
    assertEquals(1, testModel.getCellState(7,7));
  }

  @Test
  public void testStepBlinker() {
    Model testModel = new Model("Test/ConwayStatesBlinker.csv", "GameOfLife");
    assertEquals(1, testModel.getCellState(7,8));
    testModel.step();
    assertEquals(0, testModel.getCellState(7,8));
  }

  @Test
  public void testStepBlock() {
    Model testModel = new Model("Test/ConwayStatesBlock.csv", "GameOfLife");
    assertEquals(1, testModel.getCellState(9,13));
    testModel.step();
    assertEquals(1, testModel.getCellState(9,13));
  }

  @Test
  public void testStepToad() {
    Model testModel = new Model("Test/ConwayStatesToad.csv", "GameOfLife");
    assertEquals(0, testModel.getCellState(9,10));
    testModel.step();
    assertEquals(1, testModel.getCellState(9,10));
  }
}