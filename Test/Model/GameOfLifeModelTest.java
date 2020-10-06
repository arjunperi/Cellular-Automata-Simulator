package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class GameOfLifeModelTest {

  @Test
  public void testConstructor() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesPulsar.csv");
    List<List<Cell>> modelCells = testModel.getModelCells();
    assertEquals(0, modelCells.get(0).get(0).getCurrentState());
    assertEquals(1, modelCells.get(0).get(1).getCurrentState());
  }

  @Test
  public void testUpdateCells() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesPulsar.csv");
    List<List<Cell>> cells = testModel.getModelCells();
    Cell testCell = cells.get(3).get(6);
    assertEquals(0, testCell.getCurrentState());
    testModel.updateCells(cells.get(3).get(6), 3, 6);
    assertEquals(0, testCell.getCurrentState());
    testCell.nextState();
    assertEquals(1, testCell.getCurrentState());
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
    List<List<Cell>> modelCells = testModel.getModelCells();
    assertEquals(1, modelCells.get(0).get(1).getCurrentState());
    testModel.step();
    assertEquals(0, modelCells.get(0).get(1).getCurrentState());
  }


  @Test
  public void testStepBeacon() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesBeacon.csv");
    List<List<Cell>> modelCells = testModel.getModelCells();
    assertEquals(0, modelCells.get(7).get(7).getCurrentState());
    testModel.step();
    assertEquals(1, modelCells.get(7).get(7).getCurrentState());
  }

  @Test
  public void testStepBlinker() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesBlinker.csv");
    List<List<Cell>> modelCells = testModel.getModelCells();
    assertEquals(1, modelCells.get(7).get(8).getCurrentState());
    testModel.step();
    assertEquals(0, modelCells.get(7).get(8).getCurrentState());
  }

  @Test
  public void testStepBlock() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesBlock.csv");
    List<List<Cell>> modelCells = testModel.getModelCells();
    assertEquals(1, modelCells.get(9).get(13).getCurrentState());
    testModel.step();
    assertEquals(1, modelCells.get(9).get(13).getCurrentState());
  }

  @Test
  public void testStepToad() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesToad.csv");
    List<List<Cell>> modelCells = testModel.getModelCells();
    assertEquals(0, modelCells.get(9).get(10).getCurrentState());
    testModel.step();
    assertEquals(1, modelCells.get(9).get(10).getCurrentState());
  }


}