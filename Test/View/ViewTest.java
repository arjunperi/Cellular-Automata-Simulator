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
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesPulsar");
    testController.initializeSimulation("Test/ConwayStatesPulsar.csv", "GameOfLife",
        "Test/ConwayStatesPulsarOut.csv");
    View testView = testController.getMainView();
    assertEquals("0xffffffff", testView.getFrontEndCellGrid().get(0).get(0).getCellColor());
  }

  @Test
  public void testViewStepBeacon() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesBeacon");
    testController.initializeSimulation("Test/ConwayStatesBeacon.csv", "GameOfLife",
        "Test/ConwayStatesBeaconOut.csv");
    assertEquals("0xffffffff",
        testController.getMainView().getFrontEndCellGrid().get(7).get(7).getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.gameStep();
    assertEquals("0x000000ff",
        testController.getMainView().getFrontEndCellGrid().get(7).get(7).getCellColor());
  }

  @Test
  public void testViewStepBlinker() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesBlinker");
    testController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    View testView = testController.getMainView();
    assertEquals("0x000000ff", testView.getFrontEndCellGrid().get(7).get(8).getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.gameStep();
    assertEquals("0xffffffff", testView.getFrontEndCellGrid().get(7).get(8).getCellColor());
  }

  @Test
  public void testViewStepBlock() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesBlock");
    testController.initializeSimulation("Test/ConwayStatesBlock.csv", "GameOfLife",
        "Test/ConwayStatesBlockOut.csv");
    View testView = testController.getMainView();
    assertEquals("0x000000ff", testView.getFrontEndCellGrid().get(9).get(13).getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.gameStep();
    assertEquals("0x000000ff", testView.getFrontEndCellGrid().get(9).get(13).getCellColor());
  }

  @Test
  public void testViewStepToad() {
    Controller testController = new Controller();
    testController.setupScene();
    testController.startSimulation("GameOfLife","ConwayStatesToad");
    testController.initializeSimulation("Test/ConwayStatesToad.csv", "GameOfLife",
        "Test/ConwayStatesToadOut.csv");
    View testView = testController.getMainView();
    assertEquals("0xffffffff", testView.getFrontEndCellGrid().get(9).get(10).getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.gameStep();
    assertEquals("0x000000ff", testView.getFrontEndCellGrid().get(9).get(10).getCellColor());
  }
}
