package cellSociety;

import cellsociety.Cell;
import cellsociety.Grid;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GridTest extends DukeApplicationTest {

    @Test
    public void testGridReadingFromFile(){
        Grid myGrid = new Grid("ConwayStates1.csv");
        List<List<Cell>> myCells = myGrid.getGridCells();
        assertEquals(1,myCells.get(9).get(5).getCurrentState());
        assertEquals(0,myCells.get(9).get(4).getCurrentState());
    }


}
