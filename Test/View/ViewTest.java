package View;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Controller.Controller;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ViewTest extends DukeApplicationTest {

  @Test
  public void testInitializeFrontendCells() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesPulsar");
    testController.initializeSimulation("ConwayStatesPulsar.csv", "GameOfLife",
        "ConwayStatesPulsarOut.csv");
    View testView = testController.getMainView();
    assertEquals("0xffffffff", testView.getFrontEndCellGrid().get(0).get(0).getCellColor());
  }

  @Test
  public void testViewStepBeacon() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesBeacon");
    testController.initializeSimulation("ConwayStatesBeacon.csv", "GameOfLife",
        "ConwayStatesBeaconOut.csv");
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
    testController.initializeSimulation("ConwayStatesBlinker.csv", "GameOfLife",
        "ConwayStatesBlinkerOut.csv");
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
    testController.initializeSimulation("ConwayStatesBlock.csv", "GameOfLife",
        "ConwayStatesBlockOut.csv");
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
    testController.initializeSimulation("ConwayStatesToad.csv", "GameOfLife",
        "ConwayStatesToadOut.csv");
    View testView = testController.getMainView();
    assertEquals("0xffffffff", testView.getFrontEndCellGrid().get(9).get(10).getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.gameStep();
    assertEquals("0x000000ff", testView.getFrontEndCellGrid().get(9).get(10).getCellColor());
  }
}
