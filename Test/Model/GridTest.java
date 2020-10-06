package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class GridTest {

  @Test
  public void testGridReadingFromFile() {
    Grid myGrid = new Grid("Test/ConwayStatesPulsar.csv");
    List<List<Cell>> myCells = myGrid.getGridCells();
    assertEquals(1, myCells.get(9).get(5).getCurrentState());
    assertEquals(0, myCells.get(9).get(4).getCurrentState());
  }

}