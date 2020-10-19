package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class GridTest extends DukeApplicationTest {

  @Test
  public void testGridReadingFromFile() {
    Grid myGrid = new Grid("Test/ConwayStatesPulsar.csv", "GameOfLife");
    assertEquals(1, myGrid.getCell(9,5).getCurrentState());
    assertEquals(0, myGrid.getCell(9,4).getCurrentState());
  }

  @Test
  public void testInvalidModelType() {
    assertThrows(ModelException.class, () -> new Grid("Test/TestInvalidModelType.csv", "Test"));
  }

  @Test
  public void testInvalidCSV() {
    assertThrows(ModelException.class, () -> new Grid("Test/TestInvalidDimensions.csv", "GameOfLife"));
  }

  @Test
  public void testInvalidShapeOrNeighborhood() {
    Model myModel = new GameOfLifeModel("Test/TestInvalidShapeNeighbors.csv", "GameOfLife");
    Grid myGrid = new Grid("Test/TestInvalidShapeNeighbors.csv", "GameOfLife");
    myGrid.setPropertyFiles(myModel.propertyFile, myModel.defaultPropertyFile);
    assertThrows(ModelException.class, () -> myGrid.getNeighbors(0,0));
  }

//  @Test
//  public void testInvalidEdgePolicy() {
//    Model myModel = new GameOfLifeModel("Test/TestInvalidEdgePolicy.csv", "GameOfLife");
//    Grid myGrid = new Grid("Test/TestInvalidEdgePolicy.csv", "GameOfLife");
//    myGrid.setPropertyFiles(myModel.propertyFile, myModel.defaultPropertyFile);
//    assertThrows(ModelException.class, () -> myGrid.getNeighbors(0,0));
//  }

  @Test
  public void testCSVInputsOutOfBounds(){
    Grid myGrid = new Grid("Test/TestCSVInputsOutOfBounds.csv", "GameOfLife");
    assertThrows(ModelException.class, () -> myGrid.getCell(1,19));
  }

  @Test
  public void testCSVInvalidStatesFormat(){
    assertThrows(ModelException.class, () -> new Grid("Test/TestCSVInvalidStatesFormat.csv", "GameOfLife"));
  }



}