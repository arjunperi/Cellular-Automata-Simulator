package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class GridTest extends DukeApplicationTest {

  @Test
  public void testGridReadingFromFile() {
    Queue<Cell> emptyQueue = new LinkedList<>();
    Grid myGrid = new Grid("Test/ConwayStatesPulsar.csv", "GameOfLife", emptyQueue);
    assertEquals(1, myGrid.getCell(9, 5).getCurrentState());
    assertEquals(0, myGrid.getCell(9, 4).getCurrentState());
  }

  @Test
  public void testInvalidModelType() {
    Queue<Cell> emptyQueue = new LinkedList<>();
    assertThrows(ModelException.class, () -> new Grid("Test/TestInvalidModelType.csv", "Test", emptyQueue));
  }

  @Test
  public void testInvalidCSV() {
    Queue<Cell> emptyQueue = new LinkedList<>();
    assertThrows(ModelException.class, () -> new Grid("Test/TestInvalidDimensions.csv", "GameOfLife", emptyQueue));
  }

  @Test
  public void testInvalidShapeOrNeighborhood() {
    Queue<Cell> emptyQueue = new LinkedList<>();
    Grid myGrid = new Grid("Test/TestInvalidShapeNeighbors.csv", "GameOfLife", emptyQueue);
    assertThrows(ModelException.class, () -> myGrid.getNeighbors(0,0));
  }

  @Test
  public void testInvalidEdgePolicy() {
    Queue<Cell> emptyQueue = new LinkedList<>();
    Grid myGrid = new Grid("Test/TestInvalidEdgePolicy.csv", "GameOfLife", emptyQueue);
    assertThrows(ModelException.class, () -> myGrid.getNeighbors(0,0));
  }

  @Test
  public void testCSVInputsOutOfBounds(){
    Queue<Cell> emptyQueue = new LinkedList<>();
    Grid myGrid = new Grid("Test/TestCSVInputsOutOfBounds.csv", "GameOfLife", emptyQueue);
    assertThrows(ModelException.class, () -> myGrid.getCell(1,19));
  }

  @Test
  public void testCSVInvalidStatesFormat(){
    Queue<Cell> emptyQueue = new LinkedList<>();
    assertThrows(ModelException.class, () -> new Grid("Test/TestCSVInvalidStatesFormat.csv", "GameOfLife", emptyQueue));
  }



}