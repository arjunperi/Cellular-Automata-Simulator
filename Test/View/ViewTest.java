package View;
import Controller.Controller;
import Model.Model;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewTest {


    @Test
    public void testInitializeFrontendCells(){
        Model testModel = new Model("Test/ConwayStatesPulsar.csv", "GameOfLife");
        View testView = new View(testModel, "GameOfLife");
        assertEquals(Color.WHITE,  testView.getFrontEndCellGrid().get(0).get(0).getCellColor());
        assertEquals(0,  testView.getFrontEndCellGrid().get(0).get(0).getCellState());
    }

    @Test
    public void testViewStepPulsar(){
        Model testModel = new Model("Test/ConwayStatesPulsar.csv", "GameOfLife");
        View testView = new View(testModel, "GameOfLife");
        Controller testController = new Controller(testModel);
        assertEquals(1,  testView.getFrontEndCellGrid().get(0).get(1).getCellState());
        testController.handleKeyInput(KeyCode.S);
        testView.viewStep();
        assertEquals(0,  testView.getFrontEndCellGrid().get(0).get(1).getCellState());
    }

    @Test
    public void testViewStepBeacon(){
        Model testModel = new Model("Test/ConwayStatesBeacon.csv", "GameOfLife");
        View testView = new View(testModel, "GameOfLife");
        Controller testController = new Controller(testModel);
        assertEquals(0,  testView.getFrontEndCellGrid().get(7).get(7).getCellState());
        testController.handleKeyInput(KeyCode.S);
        testView.viewStep();
        assertEquals(1,  testView.getFrontEndCellGrid().get(7).get(7).getCellState());
    }

    @Test
    public void testViewStepBlinker(){
        Model testModel = new Model("Test/ConwayStatesBlinker.csv", "GameOfLife");
        View testView = new View(testModel, "GameOfLife");
        Controller testController = new Controller(testModel);
        assertEquals(1,  testView.getFrontEndCellGrid().get(7).get(8).getCellState());
        testController.handleKeyInput(KeyCode.S);
        testView.viewStep();
        assertEquals(0,  testView.getFrontEndCellGrid().get(7).get(8).getCellState());
    }
    @Test
    public void testViewStepBlock(){
        Model testModel = new Model("Test/ConwayStatesBlock.csv", "GameOfLife");
        View testView = new View(testModel, "GameOfLife");
        Controller testController = new Controller(testModel);
        assertEquals(1,  testView.getFrontEndCellGrid().get(9).get(13).getCellState());
        testController.handleKeyInput(KeyCode.S);
        testView.viewStep();
        assertEquals(1,  testView.getFrontEndCellGrid().get(9).get(13).getCellState());
    }

    @Test
    public void testViewStepToad(){
        Model testModel = new Model("Test/ConwayStatesToad.csv", "GameOfLife");
        View testView = new View(testModel, "GameOfLife");
        Controller testController = new Controller(testModel);
        assertEquals(0,  testView.getFrontEndCellGrid().get(9).get(10).getCellState());
        testController.handleKeyInput(KeyCode.S);
        testView.viewStep();
        assertEquals(1,  testView.getFrontEndCellGrid().get(9).get(10).getCellState());
    }

}
