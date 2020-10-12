package Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Model.Model;
import View.FrontEndCell;
import View.View;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ControllerTest extends DukeApplicationTest {

  @Test
  public void testPauseConwayPulsar() {
    Controller testController = new Controller();
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

  @Test
  public void testSpeedUpConwayBlinker(){
    Controller testStepController = new Controller();
    testStepController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    Controller testSpeedUpController = new Controller();
    testSpeedUpController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    View testStepView = testStepController.getMainView();
    FrontEndCell currentStepCell = testStepView.getFrontEndCellGrid().get(6).get(9);
    View testSpeedUpView = testSpeedUpController.getMainView();
    FrontEndCell testSpeedUpCell = testSpeedUpView.getFrontEndCellGrid().get(6).get(9);
    for(int i = 0; i < 6; i ++){
      testSpeedUpController.handleKeyInput(KeyCode.RIGHT);
    }
    assertEquals(testSpeedUpCell.getCellColor(), currentStepCell.getCellColor());
    for(int i = 0; i < 60; i ++){
      testStepController.gameStep();
    }
    for(int i = 0; i < 30; i ++){
      testSpeedUpController.gameStep();
    }
    assertEquals(testSpeedUpCell.getCellColor(), currentStepCell.getCellColor());
  }
  @Test
  public void testSlowDownConwayBlinker(){
    Controller testNormalController = new Controller();
    testNormalController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    Controller testSlowDownController = new Controller();
    testSlowDownController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    View testNormalView = testNormalController.getMainView();
    FrontEndCell currentNormalCell = testNormalView.getFrontEndCellGrid().get(6).get(9);
    View testSlowDownView = testSlowDownController.getMainView();
    FrontEndCell testSlowDownCell = testSlowDownView.getFrontEndCellGrid().get(6).get(9);
    for(int i = 0; i < 6; i ++){
      testSlowDownController.handleKeyInput(KeyCode.LEFT);
    }
    assertEquals(testSlowDownCell.getCellColor(), currentNormalCell.getCellColor());
    for(int i = 0; i < 60; i ++){
      testNormalController.gameStep();
    }
    for(int i = 0; i < 120; i ++){
      testSlowDownController.gameStep();
    }
    assertEquals(testSlowDownCell.getCellColor(), currentNormalCell.getCellColor());
  }

  @Test
  public void testChangeCellState() {
    Controller testController = new Controller();
    testController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    FrontEndCell currentNormalCell = testController.getMainView().getFrontEndCellGrid().get(0).get(0);
    FrontEndCell currentNormalCellID;
  }
}
