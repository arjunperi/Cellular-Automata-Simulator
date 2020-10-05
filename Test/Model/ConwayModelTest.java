package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ConwayModelTest {

  @Test
  public void testConstructor() {
    Model testModel = new ConwayModel("Test/ConwayStates1.csv");
    List<List<Cell>> modelCells = testModel.getModelCells();
    assertEquals(0, modelCells.get(0).get(0).getCurrentState());
    assertEquals(1, modelCells.get(0).get(1).getCurrentState());
  }

  @Test
  public void testUpdateCells() {
    Model testModel = new ConwayModel("Test/ConwayStates1.csv");
    List<List<Cell>> cells = testModel.getModelCells();
    Cell testCell = cells.get(3).get(6);
    assertEquals(0, testCell.getCurrentState());
    testModel.updateCell(cells.get(3).get(6), 3, 6);
    assertEquals(0, testCell.getCurrentState());
    testCell.nextState();
    assertEquals(1, testCell.getCurrentState());
  }

  @Test
  public void testStep() {
    Model testModel = new ConwayModel("Test/ConwayStates1.csv");
    List<List<Cell>> modelCells = testModel.getModelCells();
    assertEquals(1, modelCells.get(0).get(1).getCurrentState());
    testModel.step();
    assertEquals(0, modelCells.get(0).get(1).getCurrentState());
  }

  @Test
  public void testGetNeighbors() {
    Model testModel = new ConwayModel("Test/ConwayStates1.csv");
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


}