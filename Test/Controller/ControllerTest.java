package Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Model.Model;
import View.AbstractFrontEndCell;
import View.View;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ControllerTest extends DukeApplicationTest {

    @Test
    public void testPauseConwayPulsar() {
        Controller testController = new Controller();
        testController.initializeSimulation("Test/ConwayStatesPulsar.csv", "GameOfLife",
                "Test/ConwayStatesPulsarOut.csv");
        testController.handleKeyInput(KeyCode.P);
        AbstractFrontEndCell currentCell;
        int previousCellState = testController.getMainView().getFrontEndCellGrid().get(4).get(5)
                .getCellState();
        Paint previousCellColor = testController.getMainView().getFrontEndCellGrid().get(4).get(5)
                .getCellColor();
        for (int i = 0; i < 120; i++) {
            testController.getMainView().viewStep(testController.getMainModel().getGridOfCells());
            testController.getMainModel().modelStep();
            currentCell = testController.getMainView().getFrontEndCellGrid().get(4).get(5);
            assertEquals(previousCellState, currentCell.getCellState());
            assertEquals(previousCellColor, currentCell.getCellColor());
        }
    }

    @Test
    public void testStepConwayPulsar() {
        Controller testController = new Controller();
        testController.initializeSimulation("Test/ConwayStatesPulsar.csv", "GameOfLife",
                "Test/ConwayStatesPulsarOut.csv");
        View testView = testController.getMainView();
        Model testModel = testController.getMainModel();
        AbstractFrontEndCell currentCell = testView.getFrontEndCellGrid().get(5).get(6);
        testController.handleKeyInput(KeyCode.SPACE);
        assertEquals(0, currentCell.getCellState());
        assertEquals(Color.WHITE, currentCell.getCellColor());
        testController.handleKeyInput(KeyCode.S);
        testView.viewStep(testModel.getGridOfCells());
        assertEquals(1, currentCell.getCellState());
        assertEquals(Color.BLACK, currentCell.getCellColor());
        testController.handleKeyInput(KeyCode.S);
        testView.viewStep(testModel.getGridOfCells());
        assertEquals(1, currentCell.getCellState());
        assertEquals(Color.BLACK, currentCell.getCellColor());
    }

    @Test
    public void testPauseConwayBlinker() {
        Controller testController = new Controller();
        testController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
                "Test/ConwayStatesBlinkerOut.csv");
        View testView = testController.getMainView();
        Model testModel = testController.getMainModel();
        testController.handleKeyInput(KeyCode.P);
        AbstractFrontEndCell currentCell;
        int previousCellState = testView.getFrontEndCellGrid().get(7).get(8).getCellState();
        Paint previousCellColor = testView.getFrontEndCellGrid().get(7).get(8).getCellColor();
        for (int i = 0; i < 120; i++) {
            testView.viewStep(testModel.getGridOfCells());
            testModel.modelStep();
            currentCell = testView.getFrontEndCellGrid().get(7).get(8);
            assertEquals(previousCellState, currentCell.getCellState());
            assertEquals(previousCellColor, currentCell.getCellColor());
        }
    }

    @Test
    public void testStepConwayBlinker() {
        Controller testController = new Controller();
        testController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
                "Test/ConwayStatesBlinkerOut.csv");
        View testView = testController.getMainView();
        Model testModel = testController.getMainModel();
        AbstractFrontEndCell currentCell = testView.getFrontEndCellGrid().get(6).get(9);
        testController.handleKeyInput(KeyCode.SPACE);
        assertEquals(0, currentCell.getCellState());
        assertEquals(Color.WHITE, currentCell.getCellColor());
        testController.handleKeyInput(KeyCode.S);
        testView.viewStep(testModel.getGridOfCells());
        assertEquals(1, currentCell.getCellState());
        assertEquals(Color.BLACK, currentCell.getCellColor());
        testController.handleKeyInput(KeyCode.S);
        testView.viewStep(testModel.getGridOfCells());
        assertEquals(0, currentCell.getCellState());
        assertEquals(Color.WHITE, currentCell.getCellColor());
    }
}
