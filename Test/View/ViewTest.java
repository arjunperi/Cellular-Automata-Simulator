package View;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Controller.Controller;
import Model.Model;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ViewTest extends DukeApplicationTest {


    @Test
    public void testInitializeFrontendCells() {
        Model testModel = new Model("Test/ConwayStatesPulsar.csv", "GameOfLife");
        View testView = new View();
        testView.initializeFrontEndCells("GameOfLife", testModel.getNumberOfRows(),
                testModel.getNumberOfColumns(), testModel.getGridOfCells());
        assertEquals(Color.WHITE, testView.getFrontEndCellGrid().get(0).get(0).getCellColor());
        assertEquals(0, testView.getFrontEndCellGrid().get(0).get(0).getCellState());
    }

    @Test
    public void testViewStepPulsar() {
        Controller testController = new Controller();
        testController.initializeSimulation("Test/ConwayStatesPulsar.csv", "GameOfLife",
                "Test/ConwayStatesPulsarOut.csv");
        View testView = testController.getMainView();
        Model testModel = testController.getMainModel();
        assertEquals(1, testView.getFrontEndCellGrid().get(0).get(1).getCellState());
        testController.handleKeyInput(KeyCode.S);
        testView.viewStep(testModel.getGridOfCells());
        assertEquals(0, testView.getFrontEndCellGrid().get(0).get(1).getCellState());
    }

    @Test
    public void testViewStepBeacon() {
        Controller testController = new Controller();
        testController.initializeSimulation("Test/ConwayStatesBeacon.csv", "GameOfLife",
                "Test/ConwayStatesBeaconOut.csv");
        assertEquals(0,
                testController.getMainView().getFrontEndCellGrid().get(7).get(7).getCellState());
        testController.handleKeyInput(KeyCode.S);
        testController.getMainView().viewStep(testController.getMainModel().getGridOfCells());
        assertEquals(1,
                testController.getMainView().getFrontEndCellGrid().get(7).get(7).getCellState());
    }

    @Test
    public void testViewStepBlinker() {
        Controller testController = new Controller();
        testController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
                "Test/ConwayStatesBlinkerOut.csv");
        View testView = testController.getMainView();
        Model testModel = testController.getMainModel();
        assertEquals(1, testView.getFrontEndCellGrid().get(7).get(8).getCellState());
        testController.handleKeyInput(KeyCode.S);
        testView.viewStep(testModel.getGridOfCells());
        assertEquals(0, testView.getFrontEndCellGrid().get(7).get(8).getCellState());
    }

    @Test
    public void testViewStepBlock() {
        Controller testController = new Controller();
        testController.initializeSimulation("Test/ConwayStatesBlock.csv", "GameOfLife",
                "Test/ConwayStatesBlockOut.csv");
        View testView = testController.getMainView();
        Model testModel = testController.getMainModel();
        assertEquals(1, testView.getFrontEndCellGrid().get(9).get(13).getCellState());
        testController.handleKeyInput(KeyCode.S);
        testView.viewStep(testModel.getGridOfCells());
        assertEquals(1, testView.getFrontEndCellGrid().get(9).get(13).getCellState());
    }

    @Test
    public void testViewStepToad() {
        Controller testController = new Controller();
        testController.setupScene();
        testController.initializeSimulation("Test/ConwayStatesToad.csv", "GameOfLife",
                "Test/ConwayStatesToadOut.csv");
        View testView = testController.getMainView();
        Model testModel = testController.getMainModel();
        assertEquals(0, testView.getFrontEndCellGrid().get(9).get(10).getCellState());
        testController.handleKeyInput(KeyCode.S);
        testView.viewStep(testModel.getGridOfCells());
        assertEquals(1, testView.getFrontEndCellGrid().get(9).get(10).getCellState());
    }
}
