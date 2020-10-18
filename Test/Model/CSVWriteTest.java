package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Controller.Controller;
import View.View;
import View.FrontEndCell;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class CSVWriteTest extends DukeApplicationTest {

  @Test
  public void testWriteConwayToad() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesToad");
    testController.initializeSimulation("ConwayStatesToad.csv", "GameOfLife", "ConwayStatesToadOut.csv");
    View testView = testController.getMainView();
    FrontEndCell currentCell = testView.getFrontEndCellGrid().get(7).get(10);
    assertEquals("0x000000ff", currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.initializeSimulation("ConwayStatesToadOut.csv", "GameOfLife", "ConwayStatesToadOut.csv");
    View testViewOut = testController.getMainView();
    currentCell = testViewOut.getFrontEndCellGrid().get(7).get(10);
    assertEquals("0xffffffff", currentCell.getCellColor());
  }

  @Test
  public void testWriteConwayPulsar() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesPulsar");
    testController.initializeSimulation("ConwayStatesPulsar.csv", "GameOfLife", "ConwayStatesPulsarOut.csv");
    View testView = testController.getMainView();
    FrontEndCell currentCell = testView.getFrontEndCellGrid().get(4).get(5);
    assertEquals("0x000000ff", currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.initializeSimulation("ConwayStatesPulsarOut.csv", "GameOfLife", "ConwayStatesPulsarOut.csv");
    View testViewOut = testController.getMainView();
    currentCell = testViewOut.getFrontEndCellGrid().get(4).get(5);
    assertEquals("0xffffffff", currentCell.getCellColor());
  }
}
