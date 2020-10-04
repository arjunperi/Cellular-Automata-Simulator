package cellSociety;

import cellsociety.Cell;
import cellsociety.Grid;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest extends DukeApplicationTest {

    @Test
    public void testCellDimensions(){
        Grid myGrid = new Grid("ConwayStates1.csv");
        List<List<Cell>> myCells1 = myGrid.getGridCells();
        assertEquals(20,myCells1.get(0).get(0).getWidth());
        myGrid = new Grid("ConwayStates2.csv");
        List<List<Cell>> myCells2 = myGrid.getGridCells();
        assertEquals(13.333333333333334,myCells2.get(19).get(29).getWidth());
    }

}
