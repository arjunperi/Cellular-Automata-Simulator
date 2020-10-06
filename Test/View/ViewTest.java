package View;
import Model.GameOfLifeModel;
import Model.Model;

import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.*;

public class ViewTest {


    @Test
    public void testInitializeFrontendCells(){
        Model testModel = new GameOfLifeModel("Test/ConwayStatesPulsar.csv");
        View testView = new View(testModel);
        //Why is this black assertion true?
        assertEquals(Color.BLACK,  testView.getFrontEndCellGrid().get(0).get(0).getFill());
        assertEquals(0,  testView.getFrontEndCellGrid().get(0).get(0).getCellState());
    }

    @Test
    public void testViewStepPulsar(){
        Model testModel = new GameOfLifeModel("Test/ConwayStatesPulsar.csv");
        View testView = new View(testModel);
        assertEquals(1,  testView.getFrontEndCellGrid().get(0).get(1).getCellState());
        testView.viewStep();
        assertEquals(0,  testView.getFrontEndCellGrid().get(0).get(1).getCellState());
    }

    @Test
    public void testViewStepBeacon(){
        Model testModel = new GameOfLifeModel("Test/ConwayStatesBeacon.csv");
        View testView = new View(testModel);
        assertEquals(0,  testView.getFrontEndCellGrid().get(7).get(7).getCellState());
        testView.viewStep();
        assertEquals(1,  testView.getFrontEndCellGrid().get(7).get(7).getCellState());
    }

    @Test
    public void testViewStepBlinker(){
        Model testModel = new GameOfLifeModel("Test/ConwayStatesBlinker.csv");
        View testView = new View(testModel);
        assertEquals(1,  testView.getFrontEndCellGrid().get(7).get(8).getCellState());
        testView.viewStep();
        assertEquals(0,  testView.getFrontEndCellGrid().get(7).get(8).getCellState());
    }
    @Test
    public void testViewStepBlock(){
        Model testModel = new GameOfLifeModel("Test/ConwayStatesBlock.csv");
        View testView = new View(testModel);
        assertEquals(1,  testView.getFrontEndCellGrid().get(9).get(13).getCellState());
        testView.viewStep();
        assertEquals(1,  testView.getFrontEndCellGrid().get(9).get(13).getCellState());
    }

    @Test
    public void testViewStepToad(){
        Model testModel = new GameOfLifeModel("Test/ConwayStatesToad.csv");
        View testView = new View(testModel);
        assertEquals(0,  testView.getFrontEndCellGrid().get(9).get(10).getCellState());
        testView.viewStep();
        assertEquals(1,  testView.getFrontEndCellGrid().get(9).get(10).getCellState());
    }

}
