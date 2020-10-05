package Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CellTest {

  @Test
  void testCellConstructor(){
    Cell testCell = new Cell(1);
    assertEquals(1, testCell.getCurrentState());
  }

  @Test
  void testCellFutureState(){
    Cell testCell = new Cell(1);
    assertEquals(1, testCell.getCurrentState());
    testCell.nextState();
    assertEquals(1, testCell.getCurrentState());
    testCell.setFutureState(0);
    assertEquals(1, testCell.getCurrentState());
    testCell.nextState();
    assertEquals(0, testCell.getCurrentState());
  }

}