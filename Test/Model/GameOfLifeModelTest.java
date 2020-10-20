package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import Controller.Controller;
import org.junit.jupiter.api.Test;

class GameOfLifeModelTest {

  @Test
  public void testConstructor() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesPulsar.csv", "GameOfLife");
    assertEquals(0, testModel.getCellState(0,0));
    assertEquals(1, testModel.getCellState(4,5));
  }

  @Test
  public void testStepPulsar() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesPulsar.csv", "GameOfLife");
    assertEquals(1, testModel.getCellState(4,5));
    testModel.step();
    assertEquals(0, testModel.getCellState(4,5));
  }

  @Test
  public void testStepBeacon() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesBeacon.csv", "GameOfLife");
    assertEquals(0, testModel.getCellState(7,7));
    testModel.step();
    assertEquals(1, testModel.getCellState(7,7));
  }

  @Test
  public void testStepBlinker() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesBlinker.csv", "GameOfLife");
    assertEquals(1, testModel.getCellState(7,8));
    testModel.step();
    assertEquals(0, testModel.getCellState(7,8));
  }

  @Test
  public void testStepBlock() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesBlock.csv", "GameOfLife");
    assertEquals(1, testModel.getCellState(9,13));
    testModel.step();
    assertEquals(1, testModel.getCellState(9,13));
  }

  @Test
  public void testStepToad() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesToad.csv", "GameOfLife");
    assertEquals(0, testModel.getCellState(9,10));
    testModel.step();
    assertEquals(1, testModel.getCellState(9,10));
  }

  @Test
  public void testInvalidStates() {
    assertThrows(ModelException.class, () -> new GameOfLifeModel("Test/TestInvalidStates.csv", "GameOfLife"));
  }

}