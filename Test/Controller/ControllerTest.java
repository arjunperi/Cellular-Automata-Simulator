package Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import View.View;
import View.AbstractFrontendCell;
import Model.GameOfLifeModel;
import Model.Model;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.junit.jupiter.api.Test;

public class ControllerTest {

  @Test
  public void testPauseConwayPulsar() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesPulsar.csv");
    View testView = new View(testModel);
    Controller testController = new Controller(testModel);
    testController.handleKeyInput(KeyCode.SPACE);
    AbstractFrontendCell currentCell;
    int previousCellState = testView.getFrontEndCellGrid().get(4).get(5).getCellState();
    Paint previousCellColor = testView.getFrontEndCellGrid().get(4).get(5).getCellColor();
    for (int i = 0; i < 120; i++) {
      testView.viewStep();
      testModel.modelStep();
      currentCell = testView.getFrontEndCellGrid().get(4).get(5);
      assertEquals(previousCellState, currentCell.getCellState());
      assertEquals(previousCellColor, currentCell.getCellColor());
    }
  }

  @Test
  public void testStepConwayPulsar() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesPulsar.csv");
    View testView = new View(testModel);
    Controller testController = new Controller(testModel);
    AbstractFrontendCell currentCell = testView.getFrontEndCellGrid().get(5).get(6);
    testController.handleKeyInput(KeyCode.SPACE);
    assertEquals(0, currentCell.getCellState());
    assertEquals(Color.WHITE, currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testView.viewStep();
    assertEquals(1, currentCell.getCellState());
    assertEquals(Color.BLACK, currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testView.viewStep();
    assertEquals(1, currentCell.getCellState());
    assertEquals(Color.BLACK, currentCell.getCellColor());
  }

  @Test
  public void testPauseConwayBlinker() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesBlinker.csv");
    View testView = new View(testModel);
    Controller testController = new Controller(testModel);
    testController.handleKeyInput(KeyCode.SPACE);
    AbstractFrontendCell currentCell;
    int previousCellState = testView.getFrontEndCellGrid().get(7).get(8).getCellState();
    Paint previousCellColor = testView.getFrontEndCellGrid().get(7).get(8).getCellColor();
    for (int i = 0; i < 120; i++) {
      testView.viewStep();
      testModel.modelStep();
      currentCell = testView.getFrontEndCellGrid().get(7).get(8);
      assertEquals(previousCellState, currentCell.getCellState());
      assertEquals(previousCellColor, currentCell.getCellColor());
    }
  }

  @Test
  public void testStepConwayBlinker() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesBlinker.csv");
    View testView = new View(testModel);
    Controller testController = new Controller(testModel);
    AbstractFrontendCell currentCell = testView.getFrontEndCellGrid().get(6).get(9);
    testController.handleKeyInput(KeyCode.SPACE);
    assertEquals(0, currentCell.getCellState());
    assertEquals(Color.WHITE, currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testView.viewStep();
    assertEquals(1, currentCell.getCellState());
    assertEquals(Color.BLACK, currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testView.viewStep();
    assertEquals(0, currentCell.getCellState());
    assertEquals(Color.WHITE, currentCell.getCellColor());
  }

  @Test
  public void testViewStepBlinker() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesBlinker.csv");
    View testView = new View(testModel);
    Controller testController = new Controller(testModel);
    assertEquals(1, testView.getFrontEndCellGrid().get(7).get(8).getCellState());
    testController.handleKeyInput(KeyCode.S);
    testView.viewStep();
    assertEquals(0, testView.getFrontEndCellGrid().get(7).get(8).getCellState());
  }

  @Test
  public void testViewStepBlock() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesBlock.csv");
    View testView = new View(testModel);
    Controller testController = new Controller(testModel);
    assertEquals(1, testView.getFrontEndCellGrid().get(9).get(13).getCellState());
    testController.handleKeyInput(KeyCode.S);
    testView.viewStep();
    assertEquals(1, testView.getFrontEndCellGrid().get(9).get(13).getCellState());
  }

  @Test
  public void testViewStepToad() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesToad.csv");
    View testView = new View(testModel);
    Controller testController = new Controller(testModel);
    assertEquals(0, testView.getFrontEndCellGrid().get(9).get(10).getCellState());
    testController.handleKeyInput(KeyCode.S);
    testView.viewStep();
    assertEquals(1, testView.getFrontEndCellGrid().get(9).get(10).getCellState());
  }
}
