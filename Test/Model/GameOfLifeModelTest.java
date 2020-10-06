package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class GameOfLifeModelTest {

  @Test
  public void testConstructor() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesPulsar.csv");
    assertEquals(0, testModel.getCellState(0,0));
    assertEquals(1, testModel.getCellState(0,1));
  }

  @Test
  public void testUpdateCells() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesPulsar.csv");
    assertEquals(0, testModel.getCellState(3,6));
    testModel.updateCells(testModel.getCell(3,6), 3, 6);
    assertEquals(0, testModel.getCellState(3,6));
    testModel.getCell(3,6).nextState();
    assertEquals(1, testModel.getCellState(3,6));
  }


  @Test
  public void testGetNeighbors() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesPulsar.csv");
    List<List<Integer>> actualNeighbors = testModel.getNeighbors(1, 0);
    List<List<Integer>> expectedNeighbors = Arrays.asList(
        Arrays.asList(0, 0),
        Arrays.asList(0, 1),
        Arrays.asList(1, 1),
        Arrays.asList(2, 1),
        Arrays.asList(2, 0)
    );
    assertEquals(expectedNeighbors, actualNeighbors);
  }

  @Test
  public void testStepPulsar() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesPulsar.csv");
    assertEquals(1, testModel.getCellState(0,1));
    testModel.step();
    assertEquals(0, testModel.getCellState(0,1));
  }


  @Test
  public void testStepBeacon() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesBeacon.csv");
    assertEquals(0, testModel.getCellState(7,7));
    testModel.step();
    assertEquals(1, testModel.getCellState(7,7));
  }

  @Test
  public void testStepBlinker() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesBlinker.csv");
    assertEquals(1, testModel.getCellState(7,8));
    testModel.step();
    assertEquals(0, testModel.getCellState(7,8));
  }

  @Test
  public void testStepBlock() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesBlock.csv");
    assertEquals(1, testModel.getCellState(9,13));
    testModel.step();
    assertEquals(1, testModel.getCellState(9,13));
  }

  @Test
  public void testStepToad() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesToad.csv");
    assertEquals(0, testModel.getCellState(9,10));
    testModel.step();
    assertEquals(1, testModel.getCellState(9,10));
  }


}