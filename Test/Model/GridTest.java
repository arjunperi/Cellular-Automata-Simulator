package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class GridTest {

  @Test
  public void testGridReadingFromFile() {
    Grid myGrid = new Grid("Test/ConwayStatesPulsar.csv");
    assertEquals(1, myGrid.getCell(9,5).getCurrentState());
    assertEquals(0, myGrid.getCell(9,4).getCurrentState());
  }

}