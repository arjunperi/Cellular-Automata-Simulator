package Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Model.Model;
import View.FrontEndCell;
import View.View;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ControllerTest extends DukeApplicationTest {

  private Button buttonTest;

  @Test
  public void testPauseConwayPulsar() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesPulsar");
    testController.initializeSimulation("Test/ConwayStatesPulsar.csv", "GameOfLife",
        "Test/ConwayStatesPulsarOut.csv");
    testController.handleKeyInput(KeyCode.P);
    FrontEndCell currentCell;
    String previousCellColor = testController.getMainView().getFrontEndCellGrid().get(4).get(5)
        .getCellColor();
    for (int i = 0; i < 120; i++) {
      testController.gameStep();
      currentCell = testController.getMainView().getFrontEndCellGrid().get(4).get(5);
      assertEquals(previousCellColor, currentCell.getCellColor());
    }
  }

  @Test
  public void testStepConwayPulsar() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesPulsar");
    testController.initializeSimulation("Test/ConwayStatesPulsar.csv", "GameOfLife",
        "Test/ConwayStatesPulsarOut.csv");
    View testView = testController.getMainView();
    Model testModel = testController.getMainModel();
    FrontEndCell currentCell = testView.getFrontEndCellGrid().get(5).get(6);
    testController.handleKeyInput(KeyCode.SPACE);
    assertEquals("0xffffffff", currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.gameStep();
    assertEquals("0x000000ff", currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.gameStep();
    assertEquals("0x000000ff", currentCell.getCellColor());
  }

  @Test
  public void testPauseConwayBlinker() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesBlinker");
    testController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    View testView = testController.getMainView();
    testController.handleKeyInput(KeyCode.P);
    FrontEndCell currentCell;
    String previousCellColor = testView.getFrontEndCellGrid().get(7).get(8).getCellColor();
    for (int i = 0; i < 120; i++) {
      testController.gameStep();
      currentCell = testView.getFrontEndCellGrid().get(7).get(8);
      assertEquals(previousCellColor, currentCell.getCellColor());
    }
  }

  @Test
  public void testStepConwayBlinker() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesBlinker");
    testController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    View testView = testController.getMainView();
    Model testModel = testController.getMainModel();
    FrontEndCell currentCell = testView.getFrontEndCellGrid().get(6).get(9);
    testController.handleKeyInput(KeyCode.SPACE);
    assertEquals("0xffffffff", currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.gameStep();
    assertEquals("0x000000ff", currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.gameStep();
    assertEquals("0xffffffff", currentCell.getCellColor());
  }
}
