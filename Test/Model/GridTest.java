package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
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
}